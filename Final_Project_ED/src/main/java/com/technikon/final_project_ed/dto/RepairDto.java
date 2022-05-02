package com.technikon.final_project_ed.dto;

import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.model.enumeration.StatusOfRepair;
import com.technikon.final_project_ed.model.enumeration.TypeOfRepair;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Kostas
 */
public class RepairDto {

    private long repairId;
    private Date repairDate;
    private String shortDescription;
    private BigDecimal cost;
    private String detailedDescription;
    private Property property;
    private Owner owner;
    private TypeOfRepair typeOfRepair;
    private StatusOfRepair statusOfRepair;

    public RepairDto(Repair repair) {
        this.repairId = repair.getRepairId();
        this.repairDate = repair.getRepairDate();
        this.shortDescription = repair.getShortDescription();
        this.cost = repair.getCost();
        this.detailedDescription = repair.getDetailedDescription();
        this.property = repair.getProperty();
        this.owner = repair.getOwner();
        this.typeOfRepair = repair.getTypeOfRepair();
        this.statusOfRepair = repair.getStatusOfRepair();
    }

    public Repair createRepair() {
        Repair repair = new Repair.Builder()
                .setRepairId(repairId)
                .setRepairDate(repairDate)
                .setShortDescription(shortDescription)
                .setCost(cost)
                .setDetailedDescription(detailedDescription)
                .setProperty(property)
                .setOwner(owner)
                .setTypeOfRepair(typeOfRepair)
                .setStatusOfRepair(statusOfRepair)
                .build();
        return repair;
    }
}
