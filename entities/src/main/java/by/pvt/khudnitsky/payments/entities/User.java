/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Describes the entity <b>User</b>
 * @author khudnitsky
 * @version 1.0
 */

@Entity
//@SQLDelete(sql = "UPDATE T_USER F_STATUS SET F_STATUS = 'deleted' WHERE F_ID = ?" )  // TODO to do deleting
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@OnDelete(action = OnDeleteAction.CASCADE)
public class User extends AbstractEntity {
    private static final long serialVersionUID = 2L;

    @Column(nullable = false, length = 15)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    private String firstName;

    @Column(nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    private String lastName;

    @Column(unique = true, nullable = false, length = 25)
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    private String login;

    @Column(nullable = false, length = 50)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    private String password;       // TODO Шифрование

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    public UserDetail getUserDetail() {
        return userDetail;
    }
    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
    private UserDetail userDetail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    private Set<Account> accounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Operation> getOperations() {
        return operations;
    }
    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
    private Set<Operation> operations;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "T_USER_ACCESS_LEVEL",
            joinColumns = @JoinColumn(name = "F_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "F_ACCESS_LEVEL_ID"))
    public Set<AccessLevel> getAccessLevels() {
        return accessLevels;
    }
    public void setAccessLevels(Set<AccessLevel> accessLevels) {
        this.accessLevels = accessLevels;
    }
    private Set<AccessLevel> accessLevels;

    public User() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return userDetail != null ? userDetail.equals(user.userDetail) : user.userDetail == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userDetail != null ? userDetail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userDetail=" + userDetail +
                '}';
    }

    public void addAccessLevel(AccessLevel accessLevel){
        if(accessLevels == null){
            accessLevels = new HashSet<>();
        }
        accessLevels.add(accessLevel);
    }

    public void addAccount(Account account){
        if(accounts == null){
            accounts = new HashSet<>();
        }
        accounts.add(account);
    }

    public void addOperation(Operation operation){
        if(operations == null){
            operations = new HashSet<>();
        }
        operations.add(operation);
    }
}
