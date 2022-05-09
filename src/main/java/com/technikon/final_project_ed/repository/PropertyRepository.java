package com.technikon.final_project_ed.repository;

import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.Repair;
import java.util.List;
import java.util.Optional;

/**
 * PropertyRepository is an inteface that extends the generic class
 * CRUDRepository for the model class Owner and declares any additional methods
 * that must be implemented from an owner repository implementation.
 *
 * @author Kostas Vamvakousis
 */
public interface PropertyRepository extends CRUDRepository<Property> {

    List<Property> findByVat(Owner owner);

    List<Repair> findRepairByProperty(Property property);

    Optional<Property> findByPropertyId(String id);
}
