package com.technikon.final_project_ed.usecases;

import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.repository.OwnerRepository;
import com.technikon.final_project_ed.service.OwnerService;
import com.technikon.final_project_ed.service.PropertyService;
import com.technikon.final_project_ed.service.RepairService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Kostas
 */
@Slf4j
@NoArgsConstructor
public class OwnerUseCase {

    @Inject
    private OwnerService ownerService;
    @Inject
    private OwnerRepository ownerRepo;
    @Inject
    private PropertyService propertyService;
    @Inject
    private RepairService repairService;

    /**
     * getOwnerDummyList creates and returns a list with several "dummy" owners
     *
     * @return dummyList
     */
    public List<Owner> getOwnerDummyList() {
        List<Owner> dummyList = new ArrayList<>();
        dummyList.add(new Owner(154526311L, "Maria", "Iordanou", "Athens", "+306901234567", "miordanou@mail.com",
                "miordanou", "pass_miordanou", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526312L, "Dimitrios", "Dimitriou", "Athens", "+306902234567", "ddimitriou@mail.com",
                "ddimitriou", "pass_ddimitriou", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526313L, "Ioannis", "Ioannou", "Athens", "+306903234567", "iioannou@mail.com",
                "iioannou", "pass_iioannou", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526314L, "Antonio", "Molinari", "Milan", "+906904234567", "amolinari@mail.com",
                "amolinari", "pass_amolinari", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526315L, "Frederico", "Rossi", "Milan", "+906905234567", "frossi@mail.com",
                "frossi", "pass_frossi", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526316L, "Mario", "Conti", "Rome", "+906906234567", "mconti@mail.com",
                "mconti", "pass_mconti", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526317L, "Nathan", "Martin", "Lyon", "+706907234567", "nmartin@mail.com",
                "nmartin", "pass_nmartin", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526318L, "Enzo", "Collin", "Lyon", "+706908234567", "ecollin@mail.com",
                "ecollin", "pass_ecollin", new ArrayList<>(), new ArrayList<>()));
        dummyList.add(new Owner(154526319L, "Frederic", "Michel", "Athens", "+fmichel", "fmichel@mail.com",
                "fmichel", "pass_fmichel", new ArrayList<>(), new ArrayList<>()));
        return dummyList;
    }

    /**
     * getDummyOwner creates and return an owner with unique fields
     *
     * @return dummyOwner
     */
    public Owner getDummyOwner() {
        return new Owner(123456789L, "Kostas", "Anagnostou", "Athens", "+306987456321", "kanagnostou@mail.com",
                "kanagnostou", "pass_kanagnostou", new ArrayList<>(), new ArrayList<>());
    }

    /**
     * getDummyOwnerWithNonUniqueVat creates and return an owner with non unique
     * vat
     *
     * @return dummyOwner
     */
    public Owner getDummyOwnerWithNonUniqueVat() {
        return new Owner(123456789L, "Kostas", "Ioannou", "Athens", "+3069987456321", "kioannou@mail.com",
                "kioannou", "pass_kioannou", new ArrayList<>(), new ArrayList<>());
    }

    /**
     * getDummyOwnerWithNonUniqueEmail creates and return an owner with non
     * unique email
     *
     * @return dummyOwner
     */
    public Owner getDummyOwnerWithNonUniqueEmail() {
        return new Owner(987654321L, "Panos", "Papas", "Athens", "+3069987456321", "kanagnostou@mail.com",
                "ppapas", "pass_ppapas", new ArrayList<>(), new ArrayList<>());
    }

    /**
     * createOwnersUseCases implements various calls to create owners using the
     * owner's implemented service methods
     *
     */
    @PostConstruct
    public void createOwnersUseCases() {
        log.info("========== Creating Owners Use Cases ==========");
//        //We create the owners from the dummy list
//        try {
//            log.info("Creating owners from dummy list...");
////            ownerService.create(getOwnerDummyList());
//            for (Owner owner : getOwnerDummyList()) {
//                ownerRepo.save(owner);
//            }
//
//        } catch (Exception ex) {
//            log.error(ex.toString());
//        }
//        //We create a new random owner
//        try {
//            log.info("Creating dummy owner...");
////            ownerService.create(getDummyOwner());
//            ownerRepo.save(getDummyOwner());
//        } catch (Exception ex) {
//            log.error(ex.toString());
//        }
//        //We create a new owner with non unique vat
//        try {
//            log.info("Creating dummy owner with non unique vat...");
//            ownerRepo.save(getDummyOwnerWithNonUniqueVat());
//        } catch (Exception ex) {
//            log.error(ex.toString());
//        }
//
//        //We create a new owner with non unique email
//        try {
//            log.info("Creating dummy owner with non unique email...");
//            ownerRepo.save(getDummyOwnerWithNonUniqueEmail());
//        } catch (Exception ex) {
//            log.error(ex.toString());
//        }
//        //We create several owners with no arguments
//        try {
//            log.info("Creating owners with null fields...");
//            ownerRepo.save(new Owner());
//            ownerRepo.save(new Owner());
//            ownerRepo.save(new Owner());
//        } catch (Exception ex) {
//            log.error(ex.toString());
//        }
//        //We create owner with property using builder
//        try {
//            log.info("Creating owner with builder...");
//            Owner buildedOwner = new Owner.Builder()
//                    .setAddress("random address")
//                    .setEmail("randonEmail@mail.com")
//                    .setVat(111111L)
//                    .setName("Mister")
//                    .setSurname("Random")
//                    .setUsername("mrandom")
//                    .setPassword("random-pass")
//                    .setPhoneNumber("+000000000")
//                    .build();
//            buildedOwner.setProperties(List.of(new Property(664455L, "Nowhere 77", 1999, buildedOwner, TypeOfProperty.MAISONETTE, null)));
//            ownerRepo.save(buildedOwner);
//        } catch (Exception ex) {
//            log.error(ex.toString());
//        }
        try {
            if (ownerService == null) {
                log.info("ownerservice is null");
            } else {
                ownerService.create(465L, "firstemail");
            }

            Owner owner = new Owner.Builder().setVat(465L).setEmail("firstemail").build();
            if (ownerRepo != null) {
                log.info("owner repo not null");
                ownerRepo.save(owner);
            }
            if (ownerRepo == null) {
                log.info("owner repo is null");
            }

        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        log.info("========== End of Create's Use Cases ==========");
    }

    /**
     * searchOwnersUseCases makes various calls to search an owner using the
     * owner's implemented service methods
     *
     */
    public void searchOwnersUseCases() {
        log.info("========== Searching Owners Use Cases ==========");
        //We search for an existing owner with his vat
        try {
            log.info("Searching owner with vat: 123456789");
            System.out.println(ownerService.searchByVat(123456789L));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        //We search for a non existing owner with his vat
        try {
            log.info("Searching owner with vat: 123");
            System.out.println(ownerService.searchByVat(123L));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        //We search for an existing owner with his email
        try {
            log.info("Searching owner with email: kanagnostou@mail.com");
            System.out.println(ownerService.searchByEmail("kanagnostou@mail.com"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        //We search for a non existing owner with his email
        try {
            log.info("Searching owner with email: not_a_valid_email@mail.com");
            System.out.println(ownerService.searchByEmail("not_a_valid_email@mail.com"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        //We search for an owner with null email
        try {
            log.info("Searching owner with null email:");
            System.out.println(ownerService.searchByEmail(null));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.info("========== End of Search's Use Cases ==========");
    }

    /**
     * updateOwnersUseCases makes various calls to update an owner's fields
     * using the owner's implemented service methods
     *
     */
//    public void updateOwnersUseCases() {
//        log.info("========== Updating Owners Use Cases ==========");
//        Owner existingOwner = ownerService.searchByVat(123456789L);
//        Owner nonExistingOwner = new Owner(9999L, "John", "Doe", "Nada", "+04566544456", "doe@mail.com", "jdoe", "pass_jdoe", null, null);
//        //We update an existing owner's address & password
//        try {
//            log.info("Updating existing owner's address & password...");
//            ownerService.updateAddress(existingOwner, "New address");
//            ownerService.updatePassword(existingOwner, "password");
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update an existing owner's address & password with null
//        try {
//            log.info("Updating existing owner's address & password with null...");
//            ownerService.updateAddress(existingOwner, null);
//            ownerService.updatePassword(existingOwner, null);
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update an non existing owner's address & password
//        try {
//            log.info("Updating non existing owner's address & password...");
//            ownerService.updateAddress(nonExistingOwner, "New address");
//            ownerService.updatePassword(nonExistingOwner, "password");
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update an non existing owner's address & password with null
//        try {
//            log.info("Updating non existing owner's address & password with null...");
//            ownerService.updateAddress(nonExistingOwner, null);
//            ownerService.updatePassword(nonExistingOwner, null);
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update an null owner's address & password
//        try {
//            log.info("Updating null owner's address & password with null...");
//            ownerService.updateAddress(null, null);
//            ownerService.updatePassword(null, null);
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update an existing owner's email with a valid string
//        try {
//            log.info("Updating owner's email with a valid string...");
//            ownerService.updateEmail(existingOwner, "valid_email@mail.com");
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update an existing owner's email with a not valid string
//        try {
//            log.info("Updating owner's email with a not valid string...");
//            ownerService.updateEmail(existingOwner, "miordanou@mail.com");
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update an existing owner's email with null
//        try {
//            log.info("Updating owner's email with null...");
//            ownerService.updateEmail(existingOwner, null);
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update a null owner's email with a valid string
//        try {
//            log.info("Updating null owner's email with a valid string...");
//            ownerService.updateEmail(null, "totally_valid_email@mail.com");
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update a non existing owner's email with a valid string
//        try {
//            log.info("Updating non existing owner's email with a valid string...");
//            ownerService.updateEmail(nonExistingOwner, "valid_email_2@mail.com");
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update a non existing owner's email with a not valid string
//        try {
//            log.info("Updating non existing owner's email with a not valid string...");
//            ownerService.updateEmail(nonExistingOwner, "valid_email@mail.com");
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We update a null owner's email with null
//        try {
//            log.info("Updating non existing owner's email with null...");
//            ownerService.updateEmail(null, null);
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        log.info("========== End of Update's Use Cases ==========");
//    }
//
//    /**
//     * deleteOwnersUseCases makes various calls to delete an owner's using the
//     * owner's implemented service methods
//     *
//     */
//    public void deleteOwnersUseCases() {
//        log.info("========== Deleting Owners Use Cases ==========");
//        Owner nonExistingOwner = new Owner(9999L, "John", "Doe", "Nada", "+04566544456", "doe@mail.com", "jdoe", "pass_jdoe", null, null);
//        //We delete an existing owner
//        try {
//            log.info("Deleting existing owner...");
//            ownerService.delete(ownerService.searchById(1L));
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We delete a non existing owner
//        try {
//            log.info("Deleting non existing owner...");
//            ownerService.delete(nonExistingOwner);
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We delete all owners
//        try {
//            log.info("Deleting all owners...");
//            ownerService.deleteAll();
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        //We delete all owners from an empty database
//        try {
//            log.info("Deleting all owners from an empty database...");
//            ownerService.deleteAll();
//        } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
//        }
//        log.info("========== End of Delete's Use Cases ==========");
//    }
    /**
     * printAllOwners searches and prints all the users stored in database
     */
    public void printAllOwners() {
        System.out.println("\n---------- All the users stored in database ----------");
        try {
            ownerService.printAllOwners();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("------------------------------------------------------\n");
    }
}
