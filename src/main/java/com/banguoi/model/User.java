package com.banguoi.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String address;
    private int age;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(targetEntity = Product.class)
    private List<Product> products;

    public User() {
    }

    public User(String name, String email, String password, String address, int age, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean index(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String name = user.getName();
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        if (name.length() < 5) {
            errors.rejectValue("name", "name.length");
        }

        String email = user.getEmail();
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        if (!email.matches("(^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$)")) {
            errors.rejectValue("email", "email.matches");
        }

        String password = user.getPassword();
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
        if (password.length() < 6) {
            errors.rejectValue("password", "password.length");
        }

        if (!password.matches("(^[A-Za-z0-9]*[0-9|A-Z]+[A-Za-z0-9]*$)")) {
            errors.rejectValue("password", "password.matches");
        }

        ValidationUtils.rejectIfEmpty(errors, "address", "address.empty");

        String phoneNumber = user.getPhoneNumber();
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "phoneNumber.empty");
        if (phoneNumber.length() > 11 || phoneNumber.length() < 10) {
            errors.rejectValue("phoneNumber", "phone.length");
        }

        if (!phoneNumber.startsWith("0")) {
            errors.rejectValue("phoneNumber", "phoneStartsWith");
        }

        if (!phoneNumber.matches("(^$|[0-9]*$)")) {
            errors.rejectValue("phoneNumber", "phone.matches");
        }

        int age = user.getAge();
        if (age < 18) {
            errors.rejectValue("age", "age.min");
        }
    }
}
