package com.softserve.edu.delivery.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "driver")
    private List<Car> cars = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks = new ArrayList<>();

    /* 10 is 1 star, 40 is 4 stars and so on */
    @Column(name = "rate")
    private Integer rate;

    @Column(name = "photo_url")
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role userRole;

    @Column(name = "passport")
    private String passport;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "blocked")
    private Boolean blocked;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public List<Car> getCars() {
        return cars;
    }

    public User setCars(List<Car> cars) {
        this.cars = cars;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public User setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public User setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        return this;
    }

    public Integer getRate() {
        return rate;
    }

    public User setRate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public User setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public Role getUserRole() {
        return userRole;
    }

    public User setUserRole(Role userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getPassport() {
        return passport;
    }

    public User setPassport(String passport) {
        this.passport = passport;
        return this;
    }

    public Boolean getApproved() {
        return approved;
    }

    public User setApproved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public User setBlocked(Boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}