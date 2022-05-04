package com.technikon.final_project_ed.service.impl;

import com.technikon.final_project_ed.dto.OwnerDto;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.repository.OwnerRepository;
import com.technikon.final_project_ed.repository.PropertyRepository;
import com.technikon.final_project_ed.service.OwnerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
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
     * @param ownerDto
     * @return
     */
    @Override
    public OwnerDto create(OwnerDto ownerDto) {
        try {
            if (isOwnerValid(ownerDto.getVat(), ownerDto.getEmail())) {
                return new OwnerDto(ownerRepo.save(ownerDto.createOwner()).get());
            } else {
                log.info("Owner with vat: {} is invalid", ownerDto.getVat());
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
    private boolean isOwnerValid(long vat, String email) {
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
    private boolean isVatUnique(long vat) {
        boolean uniqueVat = !ownerRepo.findByVat(vat).isPresent();
        if (!uniqueVat) {
            log.info("Not unique vat");
        }
        return uniqueVat;
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
    private boolean isEmailUnique(String email) {
        boolean uniqueEmail = !ownerRepo.findByEmail(email).isPresent() && email != null;
        if (uniqueEmail) {
            log.info("Not unique email");
        }
        return uniqueEmail;
    }

    /**
     * create iterates the given list of owners and for each owner it calls the
     * create method with the iterated owner as a parameter.Then the called
     * create method will add the given owner if it doesn't already exist in the
     * database
     *
     * @param ownerDtoList
     * @return
     */
    @Override
    public List<OwnerDto> create(List<OwnerDto> ownerDtoList) {
        List<OwnerDto> ownersCreatedList = new ArrayList<>();
        for (OwnerDto ownerDto : ownerDtoList) {
            ownersCreatedList.add(create(ownerDto));
        }
        return ownersCreatedList;
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

    @Override
    public OwnerDto update(OwnerDto ownerDto) {
        try {
            Optional<Owner> ownerFound = ownerRepo.findById(ownerDto.getOwnerId());
            if (ownerFound.isPresent()) {
                log.info("owner was found");
                if (ownerDto.getVat() != null && isVatUnique(ownerDto.getVat())) {
                    ownerFound.get().setVat(ownerDto.getVat());
                    log.info("vat updated");
                }
                if (ownerDto.getEmail() != null && isEmailUnique(ownerDto.getEmail())) {
                    ownerFound.get().setEmail(ownerDto.getEmail());
                    log.info("email updated");
                }
                if (ownerDto.getName() != null) {
                    ownerFound.get().setName(ownerDto.getName());
                    log.info("name updated");
                }
                if (ownerDto.getSurname() != null) {
                    ownerFound.get().setSurname(ownerDto.getSurname());
                    log.info("surname updated");
                }
                if (ownerDto.getAddress() != null) {
                    ownerFound.get().setAddress(ownerDto.getAddress());
                    log.info("address updated");
                }
                if (ownerDto.getPhoneNumber() != null) {
                    ownerFound.get().setPhoneNumber(ownerDto.getPhoneNumber());
                    log.info("phone number updated");
                }
                if (ownerDto.getUsername() != null) {
                    ownerFound.get().setUsername(ownerDto.getUsername());
                    log.info("username updated");
                }
                if (ownerDto.getPassword() != null) {
                    ownerFound.get().setPassword(ownerDto.getPassword());
                    log.info("password updated");
                }
                return new OwnerDto(ownerRepo.update(ownerFound.get().getOwnerId(), ownerFound.get()).get());
            } else {
                log.info("Owner wasn't found.");
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

    private OwnerDto getDummyOwnerDto() {
        return new OwnerDto(new Owner.Builder().setId(-1L).build());
    }
}
