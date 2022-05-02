package com.technikon.final_project_ed.model;

import com.technikon.final_project_ed.model.enumeration.TypeOfRepair;
import com.technikon.final_project_ed.model.enumeration.StatusOfRepair;
import java.util.Date;
import javax.persistence.*;
import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model of repair
 *
 * @author marina
 */
@Data
@NoArgsConstructor
@Entity
public class Repair implements Serializable {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long repairId;
    @Column(name = "repairDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date repairDate;
    @Column(name = "shortDescription", nullable = false)
    private String shortDescription;
    @Column(name = "cost", nullable = false)
    private BigDecimal cost;
    @Column(name = "detailedDescription", nullable = false)
    private String detailedDescription;
    @Column(name = "Type_Of_Repair", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TypeOfRepair typeOfRepair;
    @Column(name = "Status_Of_Repair", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StatusOfRepair statusOfRepair;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "property", referencedColumnName = "id")
    private Property property;
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id")//ownerId????
    private Owner owner;

    public Repair(Date repairDate, String shortDescription, BigDecimal cost, String detailedDescription, Property propertiesId, Owner owner, TypeOfRepair typeOfRepair, StatusOfRepair statusOfRepair) {
        this.repairDate = repairDate;
        this.shortDescription = shortDescription;
        this.cost = cost;
        this.detailedDescription = detailedDescription;
        this.property = propertiesId;
        this.owner = owner;
        this.typeOfRepair = typeOfRepair;
        this.statusOfRepair = statusOfRepair;
    }

    private Repair(Builder builder) {
        this.repairId = builder.repairId;
        this.repairDate = builder.repairDate;
        this.statusOfRepair = builder.statusOfRepair;
        this.typeOfRepair = builder.typeOfRepair;
        this.owner = builder.owner;
        this.property = builder.propertiesId;
        this.detailedDescription = builder.detailedDescription;
        this.cost = builder.cost;
        this.shortDescription = builder.shortDescription;
    }

    public static class Builder {

        private long repairId;
        private Date repairDate;
        private String shortDescription;
        private BigDecimal cost;
        private String detailedDescription;
        private Property propertiesId;
        private Owner owner;
        private TypeOfRepair typeOfRepair;
        private StatusOfRepair statusOfRepair;

        public Builder() {
        }

        public Builder setStatusOfRepair(StatusOfRepair statusOfRepair) {
            this.statusOfRepair = statusOfRepair;
            return this;
        }

        public Builder setTypeOfRepair(TypeOfRepair typeOfRepair) {
            this.typeOfRepair = typeOfRepair;
            return this;
        }

        public Builder setRepairId(long repairId) {
            this.repairId = repairId;
            return this;
        }

        public Builder setRepairDate(Date repairDate) {
            this.repairDate = repairDate;
            return this;
        }

        public Builder setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder setCost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public Builder setDetailedDescription(String detailedDescription) {
            this.detailedDescription = detailedDescription;
            return this;
        }

        public Builder setProperty(Property property) {
            this.propertiesId = property;
            return this;
        }

        public Builder setOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Repair build() {
            return new Repair(this);
        }
    }

}
