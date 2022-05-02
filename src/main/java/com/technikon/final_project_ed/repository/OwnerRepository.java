package com.technikon.final_project_ed.repository;

import com.technikon.final_project_ed.model.Owner;
import java.util.Optional;

/**
 * OwnerRepository is an inteface that extends the generic class CRUDRepository
 * for the model class Owner and declares any additional methods that must be
 * implemented from an owner repository implementation.
 *
 * @author Kostas Vamvakousis
 */
public interface OwnerRepository extends CRUDRepository<Owner> {

    Optional<Owner> findByEmail(String email);

    Optional<Owner> findByVat(long vat);
}
