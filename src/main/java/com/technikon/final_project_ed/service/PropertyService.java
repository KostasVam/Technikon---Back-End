package com.technikon.final_project_ed.service;

import com.technikon.final_project_ed.dto.PropertyDto;
import com.technikon.final_project_ed.dto.RepairDto;
import com.technikon.final_project_ed.model.enumeration.TypeOfProperty;

import java.util.List;

/**
 * PropertyService is an interface that extends the generic class CRUDService
 * for the model class Property and declares any additional methods that must be
 * implemented from an PropertyServiceImplementation.
 *
 * @author Kostas Vamvakousis
 */
public interface PropertyService {

    PropertyDto create(PropertyDto propertyDto);

    List<PropertyDto> create(List<PropertyDto> propertyDtoList);

    PropertyDto searchById(long id);

    List<PropertyDto> getAll();

    PropertyDto update(PropertyDto propertyDto);

    void delete(long propertyId);

    void deleteAll();

    List<PropertyDto> searchByVatNumber(long vat);

    PropertyDto searchByPropertyId(long id);

    List<RepairDto> findRepairsOfProperty(long propertyId);

}
