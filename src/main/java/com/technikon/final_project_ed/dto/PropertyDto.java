package com.technikon.final_project_ed.dto;

import com.technikon.final_project_ed.model.Property;
import com.technikon.final_project_ed.model.enumeration.TypeOfProperty;
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
    private TypeOfProperty typeOfProperty;
    private Long ownerVat;

    public PropertyDto(Property property) {
        this.id = property.getId();
        this.propertyID = property.getPropertyID();
        this.address = property.getAddress();
        this.yearOfConstruction = property.getYearOfConstruction();
        this.typeOfProperty = property.getTypeOfProperty();
        this.ownerVat = property.getOwnerVat();
    }

    public Property createProperty() {
        Property property = new Property.Builder()
                .setId(id)
                .setPropertyId(propertyID)
                .setAddress(address)
                .setYearOfConstruction(yearOfConstruction)
                .setTypeOfProperty(typeOfProperty)
                .setOwnerVat(ownerVat)
                .build();
        return property;
    }

}
