package com.technikon.final_project_ed.service;

import com.technikon.final_project_ed.dto.PropertyDto;
import com.technikon.final_project_ed.dto.RepairDto;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.enumeration.TypeOfProperty;

import java.util.List;

/**
 * PropertyService is an interface that extends the generic class CRUDService
 * for the model class Property and declares any additional methods that must be
 * implemented from an PropertyServiceImplementation.
 *
 * @author Nikolaos Mpifsas
 */
public interface PropertyService {

    PropertyDto create(Property property);

    List<PropertyDto> create(List<Property> list);

    PropertyDto searchById(long id);

    List<PropertyDto> getAll();

    void delete(long propertyId);

    void deleteAll();

    PropertyDto updateAddress(long propertyId, String address);

    PropertyDto updateYearOfConstruction(long propertyId, Integer newYearOfConstruction);

    PropertyDto updateTypeOfProperty(long propertyId, TypeOfProperty newType);

    public void printAllProperties();

    public PropertyDto updatePropertyId(long propertyId, Long newΙd);

    public PropertyDto updateOwner(long propertyId, long ownerId);

    List<PropertyDto> searchByVatNumber(long vat);

    public PropertyDto searchByPropertyId(long id);

    public List<RepairDto> findRepairsOfProperty(long propertyId);

}
