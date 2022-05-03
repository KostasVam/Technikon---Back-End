package com.technikon.final_project_ed.service.impl;

import com.technikon.final_project_ed.dto.RepairDto;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.enumeration.StatusOfRepair;
import com.technikon.final_project_ed.model.enumeration.TypeOfRepair;
import com.technikon.final_project_ed.service.RepairService;
import com.technikon.final_project_ed.repository.PropertyRepository;
import com.technikon.final_project_ed.repository.RepairRepository;
import com.technikon.final_project_ed.repository.OwnerRepository;
import com.technikon.final_project_ed.util.DateFormatterUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import javax.enterprise.inject.Default;

/**
 *
 * @author Kostas
 */
@Slf4j
@NoArgsConstructor
@Default
public class RepairServiceImpl implements RepairService, Serializable {

    @Inject
    private RepairRepository repairRepo;
    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private OwnerRepository ownerRepo;

    /**
     * isRepairValid checks if the given owner's vat and property id already
     * exist in the database.If both weren't found it return true otherwise it
     * returns false
     *
     * @param repair
     * @return boolean
     */
    @Override
    public boolean isRepairValid(Repair repair) {
        boolean ownerValid = isOwnerValid(repair.getOwner().getVat());
        boolean propertyOwned = isPropertyValid(repair.getProperty(), repair.getOwner());
        return ownerValid && propertyOwned;

    }

    /**
     * isOwnerValid checks if the given owner's vat and email already exist in
     * the database.If both weren't found it return true otherwise it returns
     * false
     *
     * @param vat
     * @return boolean
     */
    @Override
    public boolean isOwnerValid(long vat) {
        return ownerRepo.findByVat(vat).isPresent();
    }

    /**
     * isPropertyValid checks if the given property and owner already exist in
     * the database.If both weren't found it return true otherwise it returns
     * false
     *
     * @param owner
     * @param property
     * @return boolean
     */
    @Override
    public boolean isPropertyValid(Property property, Owner owner) {
        for (Property propertyOwner : owner.getProperties()) {
            if (propertyOwner.getId() == propertyOwner.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * create creates a repair in the database.if it already exists in throws an
     * exception
     *
     * @param repair
     * @return
     */
    @Override
    public RepairDto create(Repair repair) {
        try {
            if (isRepairValid(repair)) {
                repairRepo.save(repair);
                return new RepairDto(repair);
            } else {
                log.info("Repair with id: {} is invalid", repair.getRepairId());
                return getDummyRepairDto();
            }
        } catch (NullPointerException ex) {
            log.info("Owner or property is null");
            return getDummyRepairDto();
        }
    }

    /**
     * create iterates the given list of repairs and and calls the create method
     * for each repair
     *
     * @param repairList
     * @return
     */
    @Override
    public List<RepairDto> create(List<Repair> repairList) {
        List<RepairDto> repairsDtoList = new ArrayList<>();
        for (Repair repair : repairList) {
            repairsDtoList.add(create(repair));
        }
        return repairsDtoList;
    }

    /**
     * searchById checks whether or not a certain repair id is in the database
     *
     * @param id
     * @return repair
     */
    @Override
    public RepairDto searchById(long id) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        if (repairFound.isPresent()) {
            return new RepairDto(repairFound.get());
        }
        log.info("Repair with id: {} is invalid", repairFound.get().getRepairId());
        return getDummyRepairDto();
    }

    /**
     * getAll calls the readAll method to get all repairs from the database
     *
     * @return List
     */
    @Override
    public List<RepairDto> getAll() {
        List<Repair> allRepairList = repairRepo.findAll();
        if (allRepairList.isEmpty()) {
            log.info("The database has no repairs.");
        }
        List<RepairDto> repairDtoList = new ArrayList<>();
        for (Repair repair : allRepairList) {
            repairDtoList.add(new RepairDto(repair));
        }
        return repairDtoList;
    }

    /**
     * delete deletes a repaid given that it exists in the database
     *
     * @param id
     */
    @Override
    public void delete(long id) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        if (repairFound.isPresent()) {
            repairFound.get().getOwner().getRepairs().remove(repairFound.get());
            repairFound.get().getProperty().getRepairList().remove(repairFound.get());
            repairRepo.delete(repairFound.get().getRepairId());
        } else {
            log.info("Repair doesn't exist.");
        }
    }

    /**
     * delete calls the getAll method to retrieve all the repairs in the
     * database.
     *
     */
    @Override
    public void deleteAll() {
        try {
            List<Repair> repairlist = repairRepo.findAll();
            if (!repairlist.isEmpty()) {
                repairlist
                        .stream()
                        .forEach((Repair r)
                                -> {
                            r.getOwner().getRepairs().remove(r);
                            r.getProperty().getRepairList().remove(r);
                            delete(r.getRepairId());
                        });
            } else {
                log.info("Database hasn't any repairs.");
            }
        } catch (NullPointerException e) {
            log.info("Database hasn't any repairs.");
        }
    }

    /**
     * updateDate updates the date of a specific repair in the database if it
     * exists
     *
     * @param id
     * @param repairDate
     * @return
     */
    @Override
    public RepairDto updateDate(long id, Date repairDate) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        if (repairFound.isPresent() && repairDate != null) {
            repairFound.get().setRepairDate(repairDate);
            repairRepo.save(repairFound.get());
            return new RepairDto(repairFound.get());
        } else {
            log.info("Update of repair with id {} failed. Invalid id or date.", repairFound.get().getRepairId());
            return getDummyRepairDto();
        }
    }

    /**
     * updateShortDescription updates the short description of a specific repair
     * in the database if it exists
     *
     * @param id
     * @param shortDescription
     * @return
     */
    @Override
    public RepairDto updateShortDescription(long id, String shortDescription) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        if (repairFound.isPresent() && shortDescription != null) {
            repairFound.get().setShortDescription(shortDescription);
            repairRepo.save(repairFound.get());
            return new RepairDto(repairFound.get());
        } else {
            log.info("Update of repair with id {} failed. Invalid id or short description.", repairFound.get().getRepairId());
            return getDummyRepairDto();
        }
    }

    /**
     * updateTypeOfRepair updates the type of a specific repair in the database
     * if it exists
     *
     * @param id
     * @param typeOfRepair
     * @return
     */
    @Override
    public RepairDto updateTypeOfRepair(long id, TypeOfRepair typeOfRepair) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        if (repairFound.isPresent() && typeOfRepair != null) {
            repairFound.get().setTypeOfRepair(typeOfRepair);
            repairRepo.save(repairFound.get());
            return new RepairDto(repairFound.get());
        } else {
            log.info("Update of repair with id {} failed. Invalid id or type of repair.", repairFound.get().getRepairId());
            return getDummyRepairDto();
        }
    }

    /**
     * updateDetailedDescription updates detailed short description of a
     * specific repair in the database if it exists
     *
     * @param id
     * @param detailedDescription
     * @return
     */
    @Override
    public RepairDto updateDetailedDescription(long id, String detailedDescription) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        if (repairFound.isPresent() && detailedDescription != null) {
            repairFound.get().setDetailedDescription(detailedDescription);
            repairRepo.save(repairFound.get());
            return new RepairDto(repairFound.get());
        } else {
            log.info("Update of repair with id {} failed. Invalid id or detailed description.", repairFound.get().getRepairId());
            return getDummyRepairDto();
        }
    }

    /**
     * updateCost updates the cost of a specific repair in the database if it
     * exists
     *
     * @param id
     * @param cost
     * @return
     */
    @Override
    public RepairDto updateCost(long id, BigDecimal cost) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        if (repairFound.isPresent() && cost != null) {
            repairFound.get().setCost(cost);
            repairRepo.save(repairFound.get());
            return new RepairDto(repairFound.get());
        } else {
            log.info("Update of repair with id {} failed. Invalid id or cost.", repairFound.get().getRepairId());
            return getDummyRepairDto();
        }
    }

    /**
     * updateCost updates the property id matched to a specific repair in the
     * database
     *
     * @param id
     * @param propertyId
     * @return
     */
    @Override
    public RepairDto updatePropertiesId(long id, long propertyId) {
        Optional<Repair> repairFound = repairRepo.findById(id);
        Optional<Property> propertyFound = propertyRepo.findById(propertyId);
        if (repairFound.isPresent() && propertyFound.isPresent()) {
            if (isPropertyValid(repairFound.get().getProperty(), propertyFound.get().getOwner())) {
                repairFound.get().setProperty(propertyFound.get());
                repairRepo.save(repairFound.get());
                return new RepairDto(repairFound.get());
            } else {
                log.info("Update of repair with id {} failed. New property id doesn't belong to same owner.", repairFound.get().getRepairId());
                return getDummyRepairDto();
            }
        } else {
            log.info("Update of repair with id {} failed. Invalid repair or property id.");
            return getDummyRepairDto();
        }
    }

    /**
     * findRepairsByDate takes a specific date and searches for repairs
     * scheduled on that date
     *
     * @param date
     * @return repair
     */
    @Override
    public List<RepairDto> findRepairsByDate(Date date) {
        DateFormatterUtil dfu = new DateFormatterUtil();
        String dateString = dfu.getFormattedDate(date);
        List<RepairDto> repairsDtoList = new ArrayList<>();
        try {
            if (dfu.isDateValid(dateString)) {
                List<Repair> repairsFound = repairRepo.findByDate(date);
                if (!repairsFound.isEmpty()) {
                    for (Repair repair : repairsFound) {
                        repairsDtoList.add(create(repair));
                    }
                } else {
                    log.info("No repairs exist at the given date.");
                }
            } else {
                log.info("Invalid dates");
            }
        } catch (ParseException ex) {
            log.info("Invalid dates");
        }
        return repairsDtoList;
    }

    /**
     * findRepairsByDateRange takes a range of dates and searches for repairs
     * scheduled between these dates
     *
     * @param startDate
     * @param endDate
     * @return repair
     */
    @Override
    public List<RepairDto> findRepairsByDateRange(Date startDate, Date endDate) throws IllegalArgumentException {
        DateFormatterUtil dfu = new DateFormatterUtil();
        String startDateString = dfu.getFormattedDate(startDate);
        String endDateString = dfu.getFormattedDate(endDate);
        List<RepairDto> repairsDtoList = new ArrayList<>();
        try {
            if (dfu.isDateValid(startDateString) && dfu.isDateValid(endDateString)) {
                List<Repair> repairsFound = repairRepo.findByRangeOfDates(startDate, endDate);
                if (!repairsFound.isEmpty()) {
                    for (Repair repair : repairsFound) {
                        repairsDtoList.add(create(repair));
                    }
                } else {
                    log.info("No repairs exist in the given dates' range.");
                }
            } else {
                log.info("Invalid dates");
            }
        } catch (ParseException ex) {
            log.info("Invalid dates");
        }
        return repairsDtoList;
    }

    public RepairDto getDummyRepairDto() {
        return new RepairDto(new Repair.Builder().setRepairId(-1L).build());
    }
}
