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

    OwnerDto create(long vat, String email);

    List<OwnerDto> create(List<Long> vatlist, List<String> emailList);

    OwnerDto searchById(long id);

    List<OwnerDto> getAll();

    void delete(long id);

    void deleteAll();

    boolean isOwnerValid(long vat, String email);

    boolean isVatUnique(long vat);

    boolean isEmailUnique(String email);

    OwnerDto searchByVat(long vat);

    OwnerDto searchByEmail(String email);

    void printAllOwners();

    OwnerDto updateVat(long id, long vat);

    OwnerDto updateName(long id, String newName);

    OwnerDto updateSurname(long id, String newSurname);

    OwnerDto updateAddress(long id, String address);

    OwnerDto updatePhoneNumber(long id, String phoneNumber);

    OwnerDto updateEmail(long id, String email);

    OwnerDto updateUsername(long id, String username);

    OwnerDto updatePassword(long id, String password);

    OwnerDto getDummyOwnerDto();

}
