package com.technikon.final_project_ed.service.impl;

import com.technikon.final_project_ed.dto.PropertyDto;
import com.technikon.final_project_ed.dto.RepairDto;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.repository.OwnerRepository;
import com.technikon.final_project_ed.repository.PropertyRepository;
import com.technikon.final_project_ed.repository.RepairRepository;
import com.technikon.final_project_ed.service.PropertyService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Kostas Vamvakousis
 */
@Slf4j
@NoArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private OwnerRepository ownerRepo;
    @Inject
    private RepairRepository repairRepo;

    private boolean isPropertyValid(long propertyId) {
        boolean uniqueId = (propertyRepo.findByPropertyId(propertyId).isEmpty());
        if (!uniqueId) {
            log.info("Not unique property id");
        }
        return uniqueId;
    }

    private PropertyDto getDummyPropertyDto() {
        return new PropertyDto(new Property.Builder().setId(-1L).build());
    }

    /**
     * create: checks if the given property already exists in the database.it
     * calls the save method from the propertyRepository to add the given
     * property to the database.
     *
     * @param propertyDto
     * @return
     */
    @Override
    public PropertyDto create(PropertyDto propertyDto) {
        try {
            if (ownerRepo == null) {
                log.info("null owner repo");
            }
            if (propertyRepo == null) {
                log.info("null propertyRepo");
            }
            if (propertyDto == null) {
                log.info("null propertyDto");
            }
            if (isPropertyValid(propertyDto.getPropertyID())) { //&& ownerRepo.findByVat(propertyDto.getOwnerVat()) != null
                return new PropertyDto(propertyRepo.save(propertyDto.createProperty()).get());
            } else {
                log.info("Property with id: {} is invalid", propertyDto.getPropertyID());
                return getDummyPropertyDto();
            }
        } catch (NullPointerException e) {
            log.info("Invalid null property");
            e.printStackTrace();
            return getDummyPropertyDto();
        }
    }

    /**
     * create: iterates the given list of property and for each owner it calls
     * the create method with the iterated property as a parameter.
     *
     * @param propertyDtoList
     * @return
     */
    @Override
    public List<PropertyDto> create(List<PropertyDto> propertyDtoList) {
        List<PropertyDto> propertyCreatedList = new ArrayList<>();
        for (PropertyDto propertyDto : propertyDtoList) {
            propertyCreatedList.add(create(propertyDto));
        }
        return propertyCreatedList;
    }

    /**
     * searchById: checks if the given id corresponds to an existing property in
     * the database
     *
     * @param id
     * @return Property
     */
    @Override
    public PropertyDto searchById(long id) {
        Optional<Property> propertyFound = propertyRepo.findById(id);
        if (propertyFound.isPresent()) {
            return new PropertyDto(propertyFound.get());
        }
        log.info("Property with id {} wasn't found", id);
        return getDummyPropertyDto();
    }

    /**
     * searchByPropertyId: checks if the given id corresponds to an existing
     * property in the database
     *
     * @param propertyId
     * @return
     */
    @Override
    public PropertyDto searchByPropertyId(long propertyId) {
        Optional<Property> propertyFound = propertyRepo.findByPropertyId(propertyId);
        if (propertyFound.isPresent()) {
            return new PropertyDto(propertyFound.get());
        }
        log.info("Property with property id {} wasn't found", propertyId);
        return getDummyPropertyDto();
    }

    /**
     * searchByVatNumber: checks if the given vatNumber corresponds to an
     * existing property in the database
     *
     * @param vat
     * @return
     */
    @Override
    public List<PropertyDto> searchByVatNumber(long vat) {
        List<PropertyDto> propertyDtoList = new ArrayList<>();
        Optional<Owner> ownerFound = ownerRepo.findByVat(vat);
        if (ownerFound.isPresent()) {
            List<Property> propertyList = propertyRepo.findByVat(ownerFound.get());
            if (!propertyList.isEmpty()) {
                for (Property property : propertyList) {
                    propertyDtoList.add(new PropertyDto(property));
                }
            } else {
                log.info("Owner with vat: {} has no properties.", vat);
            }
        } else {
            log.info("The owner with vat: {} doesn't exist", vat);
        }
        return propertyDtoList;
    }

    /**
     * getAll: calls the ownerRepo's method readAll to retrieve all the
     * properties in the database
     *
     * @return
     */
    @Override
    public List<PropertyDto> getAll() {
        List<PropertyDto> propertyDtoList = new ArrayList<>();
        List<Property> allPropertyList = propertyRepo.findAll();
        if (allPropertyList.isEmpty()) {
            log.info("Database hasn't any properties.");
        }
        for (Property property : allPropertyList) {
            propertyDtoList.add(new PropertyDto(property));
        }
        return propertyDtoList;
    }

    @Override
    public PropertyDto update(PropertyDto propertyDto) {
        try {
            Optional<Property> propertyFound = propertyRepo.findById(propertyDto.getId());
            if (propertyFound.isPresent()) {
                log.info("Property was found");
                if (propertyDto.getPropertyID() != null && isPropertyValid(propertyDto.getPropertyID())) {
                    propertyFound.get().setPropertyID(propertyDto.getPropertyID());
                    log.info("PropertyId was updated");
                }
                if (propertyDto.getAddress() != null) {
                    propertyFound.get().setAddress(propertyDto.getAddress());
                    log.info("Address was updated");
                }
                if (propertyDto.getYearOfConstruction() != null
                        && propertyDto.getYearOfConstruction() <= Calendar.getInstance().get(Calendar.YEAR)) {
                    propertyFound.get().setYearOfConstruction(propertyDto.getYearOfConstruction());
                    log.info("Year Of Construction was updated");
                }
                if (propertyDto.getTypeOfProperty() != null) {
                    propertyFound.get().setTypeOfProperty(propertyDto.getTypeOfProperty());
                    log.info("Type Of Property was updated");
                }
//                if (propertyDto.getOwnerVat() != null) {
//                    Optional<Owner> ownerFound = ownerRepo.findByVat(propertyDto.getOwnerVat());
//                    if (ownerFound.isPresent()) {
//                        propertyFound.get().setOwnerVat(propertyDto.getOwnerVat());
//                        propertyFound.get().setOwner(ownerFound.get());
//                    } else {
//                        log.info("Owner's vat wasn't found");
//                    }
//                    log.info("Owner was updated");
//                }
                return new PropertyDto(propertyRepo.update(propertyFound.get().getId(), propertyFound.get()).get());
            } else {
                log.info("Property wasn't found");
                return getDummyPropertyDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null property.");
            return getDummyPropertyDto();
        }
    }

    /**
     * delete: checks if the given property corresponds to an existing property
     * in the database.If that property is found then delete property
     *
     * @param propertyId
     */
    @Override
    public void delete(long propertyId) {
        Optional<Property> propertyFound = propertyRepo.findById(propertyId);
        if (propertyFound.isPresent()) {
            List<Repair> repairList = propertyRepo.findRepairByProperty(propertyFound.get());
            repairList.stream().forEach(r -> repairRepo.delete(r.getRepairId()));
            propertyRepo.delete(propertyFound.get().getId());

        } else {
            log.info("Owner doesn't exist.");
        }

    }

    /**
     * delete: calls the getAll method to retrieve all the properties registered
     * in the database. And delete all the properties with all repairs
     *
     */
    @Override
    public void deleteAll() {
        try {
            List<Property> propertyList = propertyRepo.findAll();
            if (!propertyList.isEmpty()) {
                List<Repair> repairlist = repairRepo.findAll();
                if (!repairlist.isEmpty()) {
                    repairlist.stream().forEach(r -> repairRepo.delete(r.getRepairId()));
                }
                propertyList.stream().forEach(p -> delete(p.getId()));
            } else {
                log.info("Database hasn't any properties.");
            }
        } catch (NullPointerException e) {
            log.info("Database hasn't any properties.");
        }
    }

    @Override
    public List<RepairDto> findRepairsOfProperty(long propertyId) {
        List<RepairDto> repairDtosList = new ArrayList<>();
        try {
            Optional<Property> propertyFound = propertyRepo.findById(propertyId);
            if (propertyFound.isPresent()) {
                List<Repair> repairList = propertyRepo.findRepairByProperty(propertyFound.get());
                for (Repair repair : repairList) {
                    repairDtosList.add(new RepairDto(repair));
                }
            } else {
                log.info("Invalid property.");
            }
        } catch (NullPointerException e) {
            log.info("Invalid null property.");
        }
        return repairDtosList;
    }

}
