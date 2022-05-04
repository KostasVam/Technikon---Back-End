package com.technikon.final_project_ed.service;

import com.technikon.final_project_ed.dto.OwnerDto;
import java.util.List;

/**
 * OwnerService is an inteface that extends the generic class CRUDService for
 * the model class Owner and declares any additional methods that must be
 * implemented from an owner service implementation.
 *
 * @author Kostas Vamvakousis
 */
public interface OwnerService {

    OwnerDto create(OwnerDto ownerDto);

    List<OwnerDto> create(List<OwnerDto> ownerDtoList);

    OwnerDto searchById(long id);

    List<OwnerDto> getAll();

    void delete(long id);

    void deleteAll();

    OwnerDto searchByVat(long vat);

    OwnerDto searchByEmail(String email);

    OwnerDto update(OwnerDto ownerDto);

}
