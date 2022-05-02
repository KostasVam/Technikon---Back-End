package com.technikon.final_project_ed.dto;

import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.Repair;
import com.technikon.final_project_ed.model.enumeration.TypeOfProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Kostas
 */
@Data
@NoArgsConstructor
public class PropertyDto {

    private long id;
    private Long propertyID;
    private String address;
    private Integer yearOfConstruction;
    private Owner owner;
    private TypeOfProperty typeOfProperty;
    private List<Repair> repairList;
    private Long ownerVat;

    public PropertyDto(Property property) {
        this.id = property.getId();
        this.propertyID = property.getPropertyID();
        this.address = property.getAddress();
        this.yearOfConstruction = property.getYearOfConstruction();
        this.owner = property.getOwner();
        this.typeOfProperty = property.getTypeOfProperty();
        this.repairList = property.getRepairList();
        this.ownerVat = property.getOwnerVat();
    }

    public Property createProperty() {
        Property property = new Property.Builder()
                .setId(id)
                .setPropertyId(propertyID)
                .setAddress(address)
                .setYearOfConstruction(yearOfConstruction)
                .setOwner(owner)
                .setTypeOfProperty(typeOfProperty)
                .setRepairList(repairList)
                .setOwnerVat(ownerVat)
                .build();
        return property;
    }

}
