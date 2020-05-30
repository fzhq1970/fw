package com.ccb.ha.fw.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "admin_users")
public class AdminUser extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    private String name;

    @Size(max = 16)
    private String username;

    @NaturalId
    @Size(max = 40)
    @Email
    private String email;

    @Size(max = 100)
    @JsonIgnore(true)
    private String password;

    @Size(max = 32)
    @JsonIgnore(true)
    private String salt;

    private Boolean enabled;

    @Size(max = 16)
    private String phone;

    @Transient
    private List<AdminRole> roles;

    public AdminUser() {

    }

    public AdminUser(String name,
                     String username,
                     String email,
                     String password,
                     String salt,
                     String phone,
                     Boolean enabled,
                     List<AdminRole> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.enabled = enabled;
        this.phone = phone;
        this.roles = roles;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<AdminRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdminRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", enabled=" + enabled +
                ", phone='" + phone + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
