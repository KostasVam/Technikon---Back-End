package com.technikon.final_project_ed.dto;

import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.model.enumeration.StatusOfRepair;
import com.technikon.final_project_ed.model.enumeration.TypeOfRepair;
import com.technikon.final_project_ed.util.DateFormatterUtil;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Kostas
 */
@Data
@NoArgsConstructor
public class RepairDto {

    private long repairId;
    private String repairDate;
    private String shortDescription;
    private BigDecimal cost;
    private String detailedDescription;
    private TypeOfRepair typeOfRepair;
    private StatusOfRepair statusOfRepair;

    public RepairDto(Repair repair) {
        this.repairId = repair.getRepairId();
        this.repairDate = new DateFormatterUtil().getFormattedDate(repair.getRepairDate());
        this.shortDescription = repair.getShortDescription();
        this.cost = repair.getCost();
        this.detailedDescription = repair.getDetailedDescription();
        this.typeOfRepair = repair.getTypeOfRepair();
        this.statusOfRepair = repair.getStatusOfRepair();
    }

    public Repair createRepair() {
        Repair repair = new Repair.Builder()
                .setRepairId(repairId)
                .setRepairDate(new DateFormatterUtil().getParsedDate(repairDate))
                .setShortDescription(shortDescription)
                .setCost(cost)
                .setDetailedDescription(detailedDescription)
                .setTypeOfRepair(typeOfRepair)
                .setStatusOfRepair(statusOfRepair)
                .build();
        return repair;
    }
}
