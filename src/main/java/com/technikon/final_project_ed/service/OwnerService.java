package com.technikon.final_project_ed.service;

import com.technikon.final_project_ed.dto.OwnerDto;
import com.technikon.final_project_ed.model.Owner;
import java.util.List;

/**
 * OwnerService is an inteface that extends the generic class CRUDService for
 * the model class Owner and declares any additional methods that must be
 * implemented from an owner service implementation.
 *
 * @author Kostas Vamvakousis
 */
public interface OwnerService {

    OwnerDto create(Owner owner);

    List<OwnerDto> create(List<Owner> list);

    OwnerDto searchById(long id);

    List<OwnerDto> getAll();

    void delete(long id);

    void deleteAll();

    boolean isOwnerValid(Owner owner);

    boolean isVatUnique(long vat);

    boolean isEmailUnique(String email);

    OwnerDto searchByVat(long vat);

    OwnerDto searchByEmail(String email);

    void printAllOwners();

    OwnerDto updateAddress(long id, String address);

    OwnerDto updateEmail(long id, String email);

    OwnerDto updatePassword(long id, String password);

}
