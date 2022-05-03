package com.technikon.final_project_ed.dto;

import com.technikon.final_project_ed.model.Owner;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Kostas
 */
@Data
@NoArgsConstructor
public class OwnerDto {

    private Long ownerId;
    private Long vat;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    public OwnerDto(Owner owner) {
        this.ownerId = owner.getOwnerId();
        this.vat = owner.getVat();
        this.name = owner.getName();
        this.surname = owner.getSurname();
        this.address = owner.getAddress();
        this.phoneNumber = owner.getPhoneNumber();
        this.email = owner.getEmail();
        this.username = owner.getUsername();
        this.password = owner.getPassword();
    }

    public Owner createOwner() {
        Owner owner = new Owner.Builder()
                .setId(ownerId)
                .setVat(vat)
                .setName(name)
                .setSurname(surname)
                .setAddress(address)
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .setUsername(username)
                .setPassword(password)
                .build();
        return owner;
    }

}
