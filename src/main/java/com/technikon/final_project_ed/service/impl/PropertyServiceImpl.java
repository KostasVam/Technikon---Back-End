package com.technikon.final_project_ed.service.impl;

import com.technikon.final_project_ed.dto.PropertyDto;
import com.technikon.final_project_ed.dto.RepairDto;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.model.enumeration.TypeOfProperty;
import com.technikon.final_project_ed.repository.OwnerRepository;
import com.technikon.final_project_ed.repository.PropertyRepository;
import com.technikon.final_project_ed.repository.RepairRepository;
import com.technikon.final_project_ed.service.PropertyService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Kostas Vamvakousis
 */
@Slf4j
@NoArgsConstructor
@Default
public class PropertyServiceImpl implements PropertyService, Serializable {

    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private OwnerRepository ownerRepo;
    @Inject
    private RepairRepository repairRepo;

    public boolean isPropertyValid(Property property) throws IllegalArgumentException {
        boolean uniqueId = (propertyRepo.findByPropertyId(property.getPropertyID()).isEmpty());
        if (uniqueId) {
            return uniqueId;
        } else {
            throw new IllegalArgumentException("Not unique property id");
        }
    }

    /**
     * create: checks if the given property already exists in the database.it
     * calls the save method from the propertyRepository to add the given
     * property to the database.
     *
     * @param property
     * @return
     */
    @Override
    public PropertyDto create(Property property) {
        try {
            if (isPropertyValid(property) && ownerRepo.findByVat(property.getOwnerVat()) != null) {
                propertyRepo.save(property);
                return new PropertyDto(property);
            } else {
                log.info("Property with id: {} is invalid", property.getId());
                Property propertyReturned = new Property();
                propertyReturned.setId(-1L);
                return new PropertyDto(propertyReturned);
            }
        } catch (NullPointerException e) {
            log.info("Invalid null property");
            Property propertyReturned = new Property();
            propertyReturned.setId(-1L);
            return new PropertyDto(propertyReturned);
        }
    }

    /**
     * create: iterates the given list of property and for each owner it calls
     * the create method with the iterated property as a parameter.
     *
     * @param propertyList
     * @return
     */
    @Override
    public List<PropertyDto> create(List<Property> propertyList) {
        List<PropertyDto> propertyDtoList = new ArrayList<>();
        for (Property property : propertyList) {
            propertyDtoList.add(create(property));
        }
        return propertyDtoList;
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
        Property propertyReturned = new Property();
        propertyReturned.setId(-1L);
        return new PropertyDto(propertyReturned);
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
        Property propertyReturned = new Property();
        propertyReturned.setId(-1L);
        return new PropertyDto(propertyReturned);
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
     * printAllOwners: calls the getAll method to retrieve all the properties
     * stored in database.
     */
    @Override
    public void printAllProperties() {
        List<Property> propertyList = propertyRepo.findAll();
        if (!propertyList.isEmpty()) {
            for (Property property : propertyList) {
                System.out.println(property);
            }
        } else {
            log.info("There are no properties");
        }
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

    /**
     * updateId: checks if the given property corresponds to an existing
     * property in the database.If that property is found then the updateId sets
     * newId with the given Long
     *
     *
     * @param id
     * @param newΙd
     * @return
     */
    @Override
    public PropertyDto updatePropertyId(long id, Long newΙd) {
        try {
            Optional<Property> propertyFound = propertyRepo.findByPropertyId(id);
            Optional<Property> newIdFound = propertyRepo.findByPropertyId(newΙd);
            if (propertyFound.isPresent() && newIdFound.isEmpty() && newΙd != null) {
                propertyFound.get().setPropertyID(newΙd);
                propertyRepo.update(id, propertyFound.get());
                return new PropertyDto(propertyFound.get());
            } else {
                log.info("Invalid id or property.");
                Property propertyReturned = new Property();
                propertyReturned.setId(-1L);
                return new PropertyDto(propertyReturned);
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null property.");
            Property propertyReturned = new Property();
            propertyReturned.setId(-1L);
            return new PropertyDto(propertyReturned);
        }
    }

    /**
     * updateOwner: checks if the given owner corresponds to an existing owner
     * in the database.If that owner is found then the updateOwner sets newOwner
     * with the given Owner
     *
     * @param propertyId
     * @param ownerId
     * @return
     */
    @Override
    public PropertyDto updateOwner(long propertyId, long ownerId) {
        try {
            Optional<Property> propertyFound = propertyRepo.findById(propertyId);
            Optional<Owner> ownerFound = ownerRepo.findById(ownerId);
            if (ownerFound.isPresent() && propertyFound.isPresent()) {
                propertyFound.get().setOwner(ownerFound.get());
                propertyRepo.update(propertyId, propertyFound.get());
                return new PropertyDto(propertyFound.get());
            } else {
                log.info("Invalid property or owner.");
                Property propertyReturned = new Property();
                propertyReturned.setId(-1L);
                return new PropertyDto(propertyReturned);
            }
        } catch (NullPointerException e) {
            log.info("Invalid null property or owner.");
            Property propertyReturned = new Property();
            propertyReturned.setId(-1L);
            return new PropertyDto(propertyReturned);
        }

    }

    /**
     * updateAddress: checks if the given property corresponds to an existing
     * property in the database.If that property is found then the updateAddress
     * sets newAddress with the given String
     *
     * @param propertyId
     * @param newAddress
     * @return
     */
    @Override
    public PropertyDto updateAddress(long propertyId, String newAddress) {
        try {
            Optional<Property> propertyFound = propertyRepo.findById(propertyId);
            if (propertyFound.isPresent() && newAddress != null) {
                propertyFound.get().setAddress(newAddress);
                propertyRepo.update(propertyId, propertyFound.get());
                return new PropertyDto(propertyFound.get());
            } else {
                log.info("Invalid property or address.");
                Property propertyReturned = new Property();
                propertyReturned.setId(-1L);
                return new PropertyDto(propertyReturned);
            }
        } catch (NullPointerException e) {
            log.info("Invalid null property.");
            Property propertyReturned = new Property();
            propertyReturned.setId(-1L);
            return new PropertyDto(propertyReturned);
        }
    }

    /**
     * updateYearOfConstruction: checks if the given property corresponds to an
     * existing property in the database.If that property is found then the
     * updateYearOfConstruction sets newYearOfConstruction with the given String
     *
     * @param propertyId
     * @param newYearOfConstruction
     * @return
     */
    @Override
    public PropertyDto updateYearOfConstruction(long propertyId, Integer newYearOfConstruction) {
        try {
            Optional<Property> propertyFound = propertyRepo.findById(propertyId);
            if (propertyFound.isPresent() && newYearOfConstruction <= Calendar.getInstance().get(Calendar.YEAR)) {
                propertyFound.get().setYearOfConstruction(newYearOfConstruction);
                propertyRepo.update(propertyId, propertyFound.get());
                return new PropertyDto(propertyFound.get());
            } else {
                log.info("Invalid property or year of construction.");
                Property propertyReturned = new Property();
                propertyReturned.setId(-1L);
                return new PropertyDto(propertyReturned);
            }
        } catch (NullPointerException e) {
            log.info("Invalid null property.");
            Property propertyReturned = new Property();
            propertyReturned.setId(-1L);
            return new PropertyDto(propertyReturned);
        }
    }

    /**
     * updateTypeOfProperty: checks if the given property corresponds to an
     * existing property in the database.If that property is found then the
     * updateTypeOfProperty sets newType with the given TypeOfProperty
     *
     * @param propertyId
     * @param newType
     * @return
     */
    @Override
    public PropertyDto updateTypeOfProperty(long propertyId, TypeOfProperty newType) {
        try {
            Optional<Property> propertyFound = propertyRepo.findById(propertyId);
            if (propertyFound.isPresent() && newType != null) {
                propertyFound.get().setTypeOfProperty(newType);
                propertyRepo.update(propertyId, propertyFound.get());
                return new PropertyDto(propertyFound.get());
            } else {
                log.info("Invalid property or type of property.");
                Property propertyReturned = new Property();
                propertyReturned.setId(-1L);
                return new PropertyDto(propertyReturned);
            }
        } catch (NullPointerException e) {
            log.info("Invalid null property.");
            Property propertyReturned = new Property();
            propertyReturned.setId(-1L);
            return new PropertyDto(propertyReturned);
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

}
