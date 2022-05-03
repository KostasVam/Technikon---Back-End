package com.technikon.final_project_ed.service.impl;

import com.technikon.final_project_ed.dto.OwnerDto;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.repository.OwnerRepository;
import com.technikon.final_project_ed.repository.PropertyRepository;
import com.technikon.final_project_ed.service.OwnerService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OwnerServiceImpl class implements all the methods declared in the
 * OwnerService interface
 *
 * @author Kostas Vamvakousis
 */
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    @Inject
    private OwnerRepository ownerRepo;
    @Inject
    private PropertyRepository propertyRepo;

    /**
     * create checks if the given owner already exists in the database.If that
     * owner is found then the create method informs the user that the given
     * owner already exists, otherwise it calls the save method from the
     * ownerRepo to add the given owner to the database.
     *
     * @param vat
     * @param email
     * @return
     */
    @Override
    public OwnerDto create(long vat, String email) {
        try {
            if (isOwnerValid(vat, email)) {
                Owner owner = new Owner.Builder().setVat(vat).setEmail(email).build();
                ownerRepo.save(owner);
                return new OwnerDto(owner);
            } else {
                log.info("Owner with vat: {} is invalid", vat);
                return getDummyOwnerDto();
            }
        } catch (NullPointerException e) {
            log.info("Invalid null owner");
            return getDummyOwnerDto();
        }
    }

    /**
     * isOwnerValid checks if the given owner's vat and email already exist in
     * the database.If both weren't found it return true otherwise it returns
     * false
     *
     * @param vat
     * @param email
     * @return boolean
     */
    @Override
    public boolean isOwnerValid(long vat, String email) {
        return isVatUnique(vat) && isEmailUnique(email);
    }

    /**
     * isVatUnique calls ownerRepo's read method with the given vat. If another
     * owner exists in the database with the same vat isVatUnique returns false,
     * otherwise it returns true
     *
     * @param vat
     * @return boolean
     */
    @Override
    public boolean isVatUnique(long vat) {
        boolean uniqueVat = !ownerRepo.findByVat(vat).isPresent();
        if (uniqueVat) {
            log.info("Given vat is unique.");
            return uniqueVat;
        } else {
            log.info("Not unique vat");
            return uniqueVat;
        }
    }

    /**
     * isEmailUnique calls ownerRepo's findByEmail method with the given
     * email.If another owner exists in the database with the same email or the
     * given email is null isEmailUnique returns false, otherwise it returns
     * true
     *
     * @param email
     * @return boolean
     */
    @Override
    public boolean isEmailUnique(String email) {
        boolean uniqueEmail = !ownerRepo.findByEmail(email).isPresent() && email != null;
        if (uniqueEmail) {
            log.info("Given email is unique.");
            return uniqueEmail;
        } else {
            log.info("Not unique email");
            return uniqueEmail;
        }
    }

    /**
     * create iterates the given list of owners and for each owner it calls the
     * create method with the iterated owner as a parameter.Then the called
     * create method will add the given owner if it doesn't already exist in the
     * database
     *
     * @param vatlist
     * @param emailList
     * @return
     */
    @Override
    public List<OwnerDto> create(List<Long> vatlist, List<String> emailList) {
        List<OwnerDto> ownersDtoList = new ArrayList<>();
        for (int i = 0; i <= vatlist.size(); i++) {
            ownersDtoList.add(create(vatlist.get(i), emailList.get(i)));
        }
        return ownersDtoList;
    }

    /**
     * searchById checks if the given id corresponds to an existing Owner in the
     * database.if that Owner is found then the searchById returns the Owner
     * instance otherwise it informs the user that the Owner wasn't found and
     * returns null
     *
     * @param id
     * @return Owner
     */
    @Override
    public OwnerDto searchById(long id) {
        Optional<Owner> ownerFound = ownerRepo.findById(id);
        if (ownerFound.isPresent()) {
            return new OwnerDto(ownerFound.get());
        }
        log.info("Owner with id {} wasn't found", id);
        return getDummyOwnerDto();
    }

    /**
     * searchByEmail checks if the given email corresponds to an existing Owner
     * in the database.if that Owner is found then the searchByEmail returns the
     * Owner instance otherwise it informs the user that the Owner wasn't found
     * and returns null. The returning list from the ownerRepo's findByEmail
     * method can contain maximum one owner, because the email attribute is
     * unique.
     *
     * @param email
     * @return Owner
     */
    @Override
    public OwnerDto searchByEmail(String email) {
        Optional<Owner> ownerFound = ownerRepo.findByEmail(email);
        if (ownerFound.isPresent()) {
            return new OwnerDto(ownerFound.get());
        }
        Owner owner = new Owner();
        owner.setOwnerId(-1L);
        return new OwnerDto(owner);
    }

    @Override
    public OwnerDto searchByVat(long vat) {
        create(vat, "random");
        Optional<Owner> ownerFound = ownerRepo.findByVat(vat);
        if (ownerFound.isPresent()) {
            return new OwnerDto(ownerFound.get());
        }
        log.info("Owner with vat {} wasn't found.", vat);
        return getDummyOwnerDto();
    }

    /**
     * getAll calls the ownerRepo's method readAll to retrieve all the owners in
     * the database and it returns a list with all the owners. If the database
     * is empty getAll informs the user and returns null
     *
     * @return List
     */
    @Override
    public List<OwnerDto> getAll() {
        List<Owner> allOwnresList = ownerRepo.findAll();
        if (allOwnresList.isEmpty()) {
            log.info("The database has no owners.");
        }
        List<OwnerDto> ownersDtoList = new ArrayList<>();
        for (Owner owner : allOwnresList) {
            ownersDtoList.add(new OwnerDto(owner));
        }
        return ownersDtoList;
    }

    /**
     * printAllOwners calls the getAll method to retrieve all the owners stored
     * in database.If the database contains any owners then printAllOwners
     * prints all their instances.
     *
     */
    @Override
    public void printAllOwners() {
        List<Owner> ownerList = ownerRepo.findAll();
        for (Owner owner : ownerList) {
            System.out.println(owner);
        }
    }

    /**
     * updateAddress checks if the given owner corresponds to an existing owner
     * in the database.if that owner is found then the updateAddress sets it's
     * address with the given string
     *
     * @param id
     * @param vat
     * @return
     */
    @Override
    public OwnerDto updateVat(long id, long vat) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && isVatUnique(vat)) {
                ownerFound.get().setVat(vat);
                ownerRepo.update(id, ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid owner id or vat.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * updateAddress checks if the given owner corresponds to an existing owner
     * in the database.if that owner is found then the updateAddress sets it's
     * address with the given string
     *
     * @param id
     * @param newName
     * @return
     */
    @Override
    public OwnerDto updateName(long id, String newName) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && newName != null) {
                ownerFound.get().setName(newName);
                ownerRepo.update(id, ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid owner id or name.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * updateAddress checks if the given owner corresponds to an existing owner
     * in the database.if that owner is found then the updateAddress sets it's
     * address with the given string
     *
     * @param id
     * @param newSurname
     * @return
     */
    @Override
    public OwnerDto updateSurname(long id, String newSurname) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && newSurname != null) {
                ownerFound.get().setSurname(newSurname);
                ownerRepo.update(id, ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid owner id or surname.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * updateAddress checks if the given owner corresponds to an existing owner
     * in the database.if that owner is found then the updateAddress sets it's
     * address with the given string
     *
     * @param id
     * @param address
     * @return
     */
    @Override
    public OwnerDto updateAddress(long id, String address) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && address != null) {
                ownerFound.get().setAddress(address);
                ownerRepo.update(ownerFound.get().getOwnerId(), ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid address or owner.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * updateEmail checks if the given owner corresponds to an existing owner in
     * the database.if that owner is found and the given email doesn't belong to
     * another owner then the updateEmail sets it's email with the given string
     *
     * @param id
     * @param phoneNumber
     * @return
     */
    @Override
    public OwnerDto updatePhoneNumber(long id, String phoneNumber) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && phoneNumber != null) {
                ownerFound.get().setPhoneNumber(phoneNumber);
                ownerRepo.update(id, ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid email or owner.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * updateEmail checks if the given owner corresponds to an existing owner in
     * the database.if that owner is found and the given email doesn't belong to
     * another owner then the updateEmail sets it's email with the given string
     *
     * @param id
     * @param email
     * @return
     */
    @Override
    public OwnerDto updateEmail(long id, String email) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && isEmailUnique(email)) {
                ownerFound.get().setEmail(email);
                ownerRepo.update(ownerFound.get().getOwnerId(), ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid email or owner.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * updatePassword checks if the given owner corresponds to an existing owner
     * in the database.if that owner is found then the updatePassword sets it's
     * password with the given string
     *
     * @param id
     * @param username
     * @return
     */
    @Override
    public OwnerDto updateUsername(long id, String username) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && username != null) {
                ownerFound.get().setUsername(username);
                ownerRepo.update(id, ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid username or owner.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * updatePassword checks if the given owner corresponds to an existing owner
     * in the database.if that owner is found then the updatePassword sets it's
     * password with the given string
     *
     * @param id
     * @param password
     * @return
     */
    @Override
    public OwnerDto updatePassword(long id, String password) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(id);
            if (ownerFound.isPresent() && password != null) {
                ownerFound.get().setPassword(password);
                ownerRepo.update(ownerFound.get().getOwnerId(), ownerFound.get());
                return new OwnerDto(ownerFound.get());
            } else {
                log.info("Invalid password or owner.");
                return getDummyOwnerDto();
            }
        } catch (NullPointerException ex) {
            log.info("Invalid null owner.");
            return getDummyOwnerDto();
        }
    }

    /**
     * delete checks if the given owner corresponds to an existing owner in the
     * database.if that owner is found then the method calls ownerRepo's methos
     * delete to remove the given owner
     *
     * @param id
     */
    @Override
    public void delete(long id) {
        Optional<Owner> ownerFound = ownerRepo.findById(id);
        if (ownerFound.isPresent()) {
            List<Property> propertyList = propertyRepo.findByVat(ownerFound.get());
            if (!propertyList.isEmpty()) {
                propertyList.stream()
                        .forEach((Property p)
                                -> propertyRepo.delete(p.getId())
                        );
            }
            ownerRepo.delete(id);

        } else {
            log.info("Owner doesn't exist.");
        }
    }

    /**
     * delete calls the getAll method to retrieve all the owners registerd in
     * the database. If there are any owners in the database then it calls
     * ownerRepo's deleteALL method with the found owners as parameter to be
     * deleted.
     *
     */
    @Override
    public void deleteAll() {
        try {
            List<Owner> allOwnresList = ownerRepo.findAll();
            if (!allOwnresList.isEmpty()) {
//            repairRepo.deleteAll(repairRepo.readAll());
//            propertyRepo.deleteAll(propertyRepo.readAll());
                allOwnresList.stream()
                        .forEach(o -> ownerRepo.delete(o.getOwnerId()
                ));
            } else {
                log.info("Database hasn't any owners.");
            }
        } catch (NullPointerException e) {
            log.info("Database hasn't any owners.");
        }
    }

    @Override
    public OwnerDto getDummyOwnerDto() {
        return new OwnerDto(new Owner.Builder().setId(-1L).build());
    }
}
