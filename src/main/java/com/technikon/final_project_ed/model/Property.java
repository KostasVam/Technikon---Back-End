package com.technikon.final_project_ed.model;

import com.technikon.final_project_ed.model.enumeration.TypeOfProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class of Property
 *
 * @author Kostas Vamvakousis
 */
@Data
@NoArgsConstructor
@Entity
public class Property implements Serializable {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "propertyID", nullable = false, length = 20, unique = true)
    private Long propertyID;
    @Column(name = "Address", nullable = false, length = 45)
    private String address;
    @Column(name = "Year_Of_Construction", nullable = false, length = 4)
    private Integer yearOfConstruction;
    @Column(name = "Type_Of_Property")
    @Enumerated(value = EnumType.STRING)
    private TypeOfProperty typeOfProperty;
    @Column(name = "owner ")
    private Long ownerVat;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "OwnerId", referencedColumnName = "id")
    private Owner owner;
    @OneToMany(mappedBy = "property", targetEntity = Repair.class)//, fetch = FetchType.LAZY , cascade = CascadeType.ALL
    private List<Repair> repairList;

    public Property(Long propertyID, String address, Integer yearOfConstruction, Owner owner, TypeOfProperty typeOfProperty, List<Repair> repairList) {
        this.propertyID = propertyID;
        this.address = address;
        this.yearOfConstruction = yearOfConstruction;
        this.owner = owner;
        this.typeOfProperty = typeOfProperty;
        this.repairList = repairList;
        this.ownerVat = owner.getVat();
    }

    private Property(Builder builder) {
        this.id = builder.id;
        this.propertyID = builder.propertyID;
        this.address = builder.address;
        this.yearOfConstruction = builder.yearOfConstruction;
        this.owner = builder.owner;
        this.typeOfProperty = builder.typeOfProperty;
        this.repairList = builder.repairList;
        this.ownerVat = builder.owner.getVat();
    }

    public static class Builder {

        private Long id;
        private long propertyID;
        private String address;
        private int yearOfConstruction;
        private Owner owner;
        private TypeOfProperty typeOfProperty;
        private List<Repair> repairList;
        private long ownerVat;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setPropertyId(long propertyID) {
            this.propertyID = propertyID;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setYearOfConstruction(int yearOfConstruction) {
            this.yearOfConstruction = yearOfConstruction;
            return this;
        }

        public Builder setOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Builder setTypeOfProperty(TypeOfProperty typeOfProperty) {
            this.typeOfProperty = typeOfProperty;
            return this;
        }

        public Builder setRepairList(List<Repair> repairList) {
            this.repairList = repairList;
            return this;
        }

        public Builder setOwnerVat(long ownerVat) {
            this.ownerVat = ownerVat;
            return this;
        }

        public Property build() {
            return new Property(this);
        }
    }
}
