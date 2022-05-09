package com.technikon.final_project_ed.repository.impl;

import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.repository.OwnerRepository;
import java.util.Optional;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Kostas Vamvakousis
 */
@Slf4j
@NoArgsConstructor
@Default
public class OwnerRepositoryImpl extends CRUDRepositoryImpl<Owner> implements OwnerRepository {

    @PersistenceContext(unitName = "TechnikonPU")
    private EntityManager em;

    @Override
    public Class<Owner> getClassType() {
        return Owner.class;
    }

    @Override
    public String getClassName() {
        return Owner.class.getName();
    }

    @Override
    public void copyValues(Owner tTarget, Owner tSource) {
        tTarget.setName(tSource.getName());
        tTarget.setAddress(tSource.getAddress());
        tTarget.setEmail(tSource.getEmail());
        tTarget.setOwnerId(tSource.getOwnerId());
        tTarget.setPassword(tSource.getPassword());
        tTarget.setPhoneNumber(tSource.getPhoneNumber());
        tTarget.setProperties(tSource.getProperties());
        tTarget.setSurname(tSource.getSurname());
        tTarget.setUsername(tSource.getUsername());
        tTarget.setVat(tSource.getVat());
    }

    @Override
    public Optional<Owner> findByVat(String vat) {
        try {
            Query query = em.createQuery("SELECT s FROM Owner s WHERE s.vat = ?1", Owner.class);
            Owner owner = (Owner) query.setParameter(1, vat).getSingleResult();
            return owner != null ? Optional.of(owner) : Optional.empty();
        } catch (Exception e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * findByEmail returns a list with all the existing owners in the database
     * that correspond to the given email
     *
     * @param email
     * @return List
     */
    @Override
    public Optional<Owner> findByEmail(String email) {
        try {
            Query query = em.createQuery("SELECT s FROM Owner s WHERE s.email = ?1", Owner.class);
            Owner owner = (Owner) query.setParameter(1, email).getSingleResult();
            return owner != null ? Optional.of(owner) : Optional.empty();
        } catch (Exception e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }
}
