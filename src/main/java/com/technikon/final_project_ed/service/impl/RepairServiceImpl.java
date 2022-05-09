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
//    private boolean isRepairValid(Repair repair) {
//        boolean ownerValid = isOwnerValid(repair.getOwner().getVat());
//        boolean propertyOwned = isPropertyValid(repair.getProperty(), repair.getOwner());
//        return ownerValid && propertyOwned;
//
//    }
    /**
     * isOwnerValid checks if the given owner's vat and email already exist in
     * the database.If both weren't found it return true otherwise it returns
     * false
     *
     * @param vat
     * @return boolean
     */
    private boolean isOwnerValid(String vat) {
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
    private boolean isPropertyValid(Property property, Owner owner) {
        for (Property propertyOwner : owner.getProperties()) {
            if (propertyOwner.getId()
                    == property.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * create creates a repair in the database.if it already exists in throws an
     * exception
     *
     * @param repairDto
     * @return
     */
    @Override
    public RepairDto create(RepairDto repairDto) {
        try {
//            if (isRepairValid(repair)) {
//                repairRepo.save(repair);
//                return new RepairDto(repair);
//            } else {
//                log.info("Repair with id: {} is invalid", repair.getRepairId());
//                return getDummyRepairDto();
//            }
            return new RepairDto(repairRepo.save(repairDto.createRepair()).get());
        } catch (NullPointerException ex) {
            log.info("Invalid null repair");
            return getDummyRepairDto();
        }
    }

    /**
     * create iterates the given list of repairs and and calls the create method
     * for each repair
     *
     * @param RepairDtoList
     * @return
     */
    @Override
    public List<RepairDto> create(List<RepairDto> RepairDtoList) {
        List<RepairDto> repairsCreatedList = new ArrayList<>();
        for (RepairDto repairDto : RepairDtoList) {
            repairsCreatedList.add(create(repairDto));
        }
        return repairsCreatedList;
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
        log.info("Repair with id: {} is invalid", id);
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
            if (repairFound.get().getProperty() != null) {
                repairFound.get().getProperty().getRepairList().remove(repairFound.get());
            }
            repairRepo.delete(id);
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
                                -> delete(r.getRepairId())
                        );
            } else {
                log.info("Database hasn't any repairs.");
            }
        } catch (NullPointerException e) {
            log.info("Database hasn't any repairs.");
        }
    }

    @Override
    public RepairDto update(RepairDto repairDto) {
        try {
            Optional<Repair> repairFound = repairRepo.findById(repairDto.getRepairId());
            if (repairFound.isPresent()) {
                log.info("Repair was found");
                if (repairDto.getRepairDate() != null) {
                    repairFound.get().setRepairDate(new DateFormatterUtil().getParsedDate(repairDto.getRepairDate()));
                }
                if (repairDto.getShortDescription() != null) {
                    repairFound.get().setShortDescription(repairDto.getShortDescription());
                }
                if (repairDto.getCost() != null) {
                    repairFound.get().setCost(repairDto.getCost());
                }
                if (repairDto.getRepairDate() != null) {
                    repairFound.get().setDetailedDescription(repairDto.getDetailedDescription());
                }
                if (repairDto.getRepairDate() != null) {
                    repairFound.get().setTypeOfRepair(repairDto.getTypeOfRepair());
                }
                if (repairDto.getRepairDate() != null) {
                    repairFound.get().setStatusOfRepair(repairDto.getStatusOfRepair());
                }
                return new RepairDto(repairRepo.update(repairFound.get().getRepairId(), repairFound.get()).get());
            } else {
                log.info("Repair wasn't found.");
                return getDummyRepairDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null repair.");
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
        try {
            Optional<Repair> repairFound = repairRepo.findById(id);
            Optional<Property> propertyFound = propertyRepo.findById(propertyId);
            if (repairFound.isPresent() && propertyFound.isPresent()) {
                repairFound.get().setProperty(propertyFound.get());
                repairRepo.update(id, repairFound.get());
                return new RepairDto(repairFound.get());
            } else {
                log.info("Update of repair with id {} failed. Invalid repair or property id.");
                return getDummyRepairDto();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.toString());
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
                        repairsDtoList.add(create(new RepairDto(repair)));
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
    public List<RepairDto> findRepairsByDateRange(Date startDate, Date endDate) {
        DateFormatterUtil dfu = new DateFormatterUtil();
        String startDateString = dfu.getFormattedDate(startDate);
        String endDateString = dfu.getFormattedDate(endDate);
        List<RepairDto> repairsDtoList = new ArrayList<>();
        try {
            if (dfu.isDateValid(startDateString) && dfu.isDateValid(endDateString)) {
                List<Repair> repairsFound = repairRepo.findByRangeOfDates(startDate, endDate);
                if (!repairsFound.isEmpty()) {
                    for (Repair repair : repairsFound) {
                        repairsDtoList.add(create(new RepairDto(repair)));
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

    private RepairDto getDummyRepairDto() {
        return new RepairDto(new Repair.Builder().setRepairId(-1L).build());
    }
}
