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

    RepairDto create(RepairDto repairDto);

    List<RepairDto> create(List<RepairDto> repairDtoList);

    RepairDto searchById(long id);

    List<RepairDto> getAll();

    void delete(long id);

    void deleteAll();

    RepairDto update(RepairDto repairDto);

    RepairDto updatePropertiesId(long id, long propertyId);

    List<RepairDto> findRepairsByDate(Date date);

    List<RepairDto> findRepairsByDateRange(Date startDate, Date endDate);

}
