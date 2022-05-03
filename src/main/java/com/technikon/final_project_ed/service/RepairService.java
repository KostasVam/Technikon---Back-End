package com.technikon.final_project_ed.service;

import com.technikon.final_project_ed.dto.RepairDto;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.model.enumeration.TypeOfRepair;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * RepairService is an interface that extends the generic class CRUDService for
 * the model class Repair and declares additional method for the implementation
 * of repair service
 *
 * @author marina
 */
public interface RepairService {

    RepairDto create(Repair repair);

    List<RepairDto> create(List<Repair> list);

    RepairDto searchById(long id);

    List<RepairDto> getAll();

    void delete(long id);

    void deleteAll();

    RepairDto updateDate(long id, Date repairDate);

    RepairDto updateShortDescription(long id, String shortDescription);

    RepairDto updateTypeOfRepair(long id, TypeOfRepair typeOfRepair);

    RepairDto updateDetailedDescription(long id, String detailedDescription);

    RepairDto updateCost(long id, BigDecimal cost);

    RepairDto updatePropertiesId(long id, long propertyId);

    boolean isPropertyValid(Property property, Owner owner);

    boolean isOwnerValid(long vat);

    boolean isRepairValid(Repair repair);

    List<RepairDto> findRepairsByDate(Date date);

    List<RepairDto> findRepairsByDateRange(Date startDate, Date endDate);

    RepairDto getDummyRepairDto();

}
