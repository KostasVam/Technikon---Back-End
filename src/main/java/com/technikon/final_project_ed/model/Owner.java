package com.technikon.final_project_ed.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class Owner
 *
 * @author Kostas Vamvakousis
 */
@Data
@NoArgsConstructor
@Entity
public class Owner implements Serializable {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;
    @Column(name = "VAT", nullable = false, unique = true, length = 20)
    private String vat;
    @Column(name = "name", length = 45)
    private String name;
    @Column(name = "surname", length = 45)
    private String surname;
    @Column(name = "address", length = 120)
    private String address;
    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;
    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;
    @Column(name = "username", length = 45)
    private String username;
    @Column(name = "password", length = 45)
    private String password;

    @OneToMany(mappedBy = "owner", targetEntity = Property.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})//, fetch = FetchType.EAGER, cascade = CascadeType.ALL
    private List<Property> properties;

    public Owner(String vat, String name, String surname, String address, String phoneNumber, String email, String username, String password, List<Property> properties, List<Repair> repairs) {
        this.vat = vat;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.properties = properties;
    }

    private Owner(Builder builder) {
        this.ownerId = builder.ownerId;
        this.vat = builder.vat;
        this.name = builder.name;
        this.surname = builder.surname;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.properties = builder.properties;
    }

    public static class Builder {

        private Long ownerId;
        private String vat;
        private String name;
        private String surname;
        private String address;
        private String phoneNumber;
        private String email;
        private String username;
        private String password;
        private List<Property> properties;

        public Builder() {
        }

        public Builder setId(Long id) {
            this.ownerId = id;
            return this;
        }

        public Builder setVat(String vat) {
            this.vat = vat;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setProperties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Owner build() {
            return new Owner(this);
        }
    }

}
