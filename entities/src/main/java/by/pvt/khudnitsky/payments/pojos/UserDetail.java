package by.pvt.khudnitsky.payments.pojos;

import javax.persistence.*;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 11:56
 */
@Entity
public class UserDetail extends AbstractEntity{
    private static final long serialVersionUID = 6L;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "F_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "F_STREET")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "F_ZIPCODE"))
    })
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    private Address address;

    @OneToOne
    @PrimaryKeyJoinColumn
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    private User user;

    public UserDetail() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetail)) return false;
        if (!super.equals(o)) return false;

        UserDetail that = (UserDetail) o;

        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "address=" + address +
                '}';
    }
}
