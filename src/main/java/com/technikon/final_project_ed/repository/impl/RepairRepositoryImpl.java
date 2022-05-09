package com.technikon.final_project_ed.repository.impl;

import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.repository.RepairRepository;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import lombok.NoArgsConstructor;

/**
 *
 * @author Kostas Vamvakousis
 */
@NoArgsConstructor
@Default
public class RepairRepositoryImpl extends CRUDRepositoryImpl<Repair> implements RepairRepository, Serializable {

    @PersistenceContext(unitName = "TechnikonPU")
    private EntityManager em;

    @Override
    public Class<Repair> getClassType() {
        return Repair.class;
    }

    @Override
    public String getClassName() {
        return Repair.class.getName();
    }

    @Override
    public void copyValues(Repair tTarget, Repair tSource) {
        tTarget.setCost(tSource.getCost());
        tTarget.setDetailedDescription(tSource.getDetailedDescription());
        tTarget.setProperty(tSource.getProperty());
        tTarget.setRepairDate(tSource.getRepairDate());
        tTarget.setShortDescription(tSource.getShortDescription());
        tTarget.setStatusOfRepair(tSource.getStatusOfRepair());
        tTarget.setTypeOfRepair(tSource.getTypeOfRepair());
    }

    @Override
    public List<Repair> findByDate(Date date) {
        Query query = em.createQuery("SELECT s FROM Repair s WHERE s.repairDate = ?1", Repair.class);
        return query.setParameter(1, date, TemporalType.DATE).getResultList();
    }

    @Override
    public List<Repair> findByRangeOfDates(Date startDate, Date endDate) {
        Query query = em.createQuery("SELECT r FROM Repair r WHERE r.repairDate BETWEEN :startDate AND :endDate", Repair.class);
        query.setParameter("startDate", startDate, TemporalType.DATE);
        query.setParameter("endDate", endDate, TemporalType.DATE);
        return query.getResultList();
    }

    /**
     * findByOwner returns a list with all the existing owners in the database
     * that correspond to the given repair and vat
     *
     * @param ownersVat
     * @param repair
     * @return list
     */
    @Override
    public List<Repair> findByOwner(Repair repair, long ownersVat) {
        Query query = em.createQuery("SELECT s FROM Repair s WHERE s.owner = ?1", Repair.class);
        return query.setParameter(1, ownersVat).getResultList();
    }
}
