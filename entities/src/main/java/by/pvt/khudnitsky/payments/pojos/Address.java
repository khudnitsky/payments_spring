package by.pvt.khudnitsky.payments.pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Component, that stored in storage like columns
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 12:03
 */
@Embeddable
public class Address implements Serializable {
    private static final long serialVersionUID = 3L;

    @Column(name = "F_CITY", length = 50)
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    private String city;

    @Column(name = "F_STREET", length = 50)
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    private String street;

    @Column(name = "F_ZIP_CODE", length = 15)
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    private String zipCode;

    public Address() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return zipCode != null ? zipCode.equals(address.zipCode) : address.zipCode == null;

    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
