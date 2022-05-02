package com.technikon.final_project_ed.repository;

import com.technikon.final_project_ed.model.Repair;
import java.util.Date;
import java.util.List;

/**
 * RepairRepository is an inteface that extends the generic class CRUDRepository
 * for the model class Owner and declares any additional methods that must be
 * implemented from an owner repository implementation.
 *
 * @author Kostas Vamvakousis
 */
public interface RepairRepository extends CRUDRepository<Repair> {

    List<Repair> findByDate(Date date);

    List<Repair> findByRangeOfDates(Date startDate, Date endDate);

    List<Repair> findByOwner(Repair repair, long ownersVat);
}
