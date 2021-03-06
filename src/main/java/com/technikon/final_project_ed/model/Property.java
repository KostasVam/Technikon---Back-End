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
    private String propertyID;
    @Column(name = "Address", nullable = false, length = 45)
    private String address;
    @Column(name = "Year_Of_Construction", nullable = false, length = 4)
    private String yearOfConstruction;
    @Column(name = "Type_Of_Property")
    @Enumerated(value = EnumType.STRING)
    private TypeOfProperty typeOfProperty;

    @ManyToOne(cascade = CascadeType.MERGE)//(fetch = FetchType.LAZY)
    @JoinColumn(name = "OwnerId", referencedColumnName = "id")
    private Owner owner;
    @OneToMany(mappedBy = "property", targetEntity = Repair.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})//, fetch = FetchType.LAZY , cascade = CascadeType.ALL
    private List<Repair> repairList;

    public Property(String propertyID, String address, String yearOfConstruction, Owner owner, TypeOfProperty typeOfProperty, List<Repair> repairList) {
        this.propertyID = propertyID;
        this.address = address;
        this.yearOfConstruction = yearOfConstruction;
        this.owner = owner;
        this.typeOfProperty = typeOfProperty;
        this.repairList = repairList;
    }

    private Property(Builder builder) {
        this.id = builder.id;
        this.propertyID = builder.propertyID;
        this.address = builder.address;
        this.yearOfConstruction = builder.yearOfConstruction;
        this.owner = builder.owner;
        this.typeOfProperty = builder.typeOfProperty;
        this.repairList = builder.repairList;
    }

    public static class Builder {

        private Long id;
        private String propertyID;
        private String address;
        private String yearOfConstruction;
        private Owner owner;
        private TypeOfProperty typeOfProperty;
        private List<Repair> repairList;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setPropertyId(String propertyID) {
            this.propertyID = propertyID;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setYearOfConstruction(String yearOfConstruction) {
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

        public Property build() {
            return new Property(this);
        }
    }
}
