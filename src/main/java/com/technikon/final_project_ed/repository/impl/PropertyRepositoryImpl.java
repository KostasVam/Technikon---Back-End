package com.technikon.final_project_ed.repository.impl;

import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.repository.PropertyRepository;
import java.io.Serializable;
import java.util.List;
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
public class PropertyRepositoryImpl extends CRUDRepositoryImpl<Property> implements PropertyRepository, Serializable {

    @PersistenceContext(unitName = "TechnikonPU")
    private EntityManager em;

    @Override
    public Class<Property> getClassType() {
        return Property.class;
    }

    @Override
    public String getClassName() {
        return Property.class.getName();
    }

    @Override
    public void copyValues(Property tSource, Property tTarget) {
        tTarget.setAddress(tSource.getAddress());
        tTarget.setOwner(tSource.getOwner());
        tTarget.setOwnerVat(tSource.getOwnerVat());
        tTarget.setPropertyID(tSource.getPropertyID());
        tTarget.setTypeOfProperty(tSource.getTypeOfProperty());
        tTarget.setYearOfConstruction(tSource.getYearOfConstruction());
        tTarget.setRepairList(tSource.getRepairList());
    }

    /**
     * findByPropertyId: returns a list with all the existing Properties in the
     * database that correspond to the given id
     *
     * @param propertyId
     * @return
     */
    @Override
    public Optional<Property> findByPropertyId(long propertyId) {
        try {
            Query query = em.createQuery("SELECT s FROM Property s WHERE s.propertyID = ?1", Property.class);
            Property property = (Property) query.setParameter(1, propertyId).getSingleResult();
            return property != null ? Optional.of(property) : Optional.empty();
        } catch (Exception e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * findByVat: returns a list with all the existing Properties in the
     * database that correspond to the given owner
     *
     * @param owner
     * @return
     */
    @Override
    public List<Property> findByVat(Owner owner) {
        Query query = em.createQuery("SELECT s FROM Property s WHERE s.owner = ?1", Property.class);
        query.setParameter(1, owner);
        return query.getResultList();
    }

    /**
     * findRepairByProperty: returns a list with all the existing repairs in the
     * database that correspond to the given property
     *
     * @param property
     * @return List
     */
    @Override
    public List<Repair> findRepairByProperty(Property property) {
        Query query = em.createQuery("SELECT s FROM Repair s WHERE s.property = ?1", Repair.class);
        query.setParameter(1, property);
        return query.getResultList();
    }
}
