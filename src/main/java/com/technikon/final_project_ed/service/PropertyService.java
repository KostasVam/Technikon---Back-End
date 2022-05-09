package com.technikon.final_project_ed.service;

import com.technikon.final_project_ed.dto.OwnerDto;
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

    OwnerDto searchOwner(long id);

    List<PropertyDto> getAll();

    PropertyDto update(PropertyDto propertyDto);

    public PropertyDto updateOwner(long id, String vat);

    void delete(long id);

    void deleteAll();

    List<PropertyDto> searchByVatNumber(String vat);

    PropertyDto searchByPropertyId(String id);

    List<RepairDto> findRepairsOfProperty(long id);

}
