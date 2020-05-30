package com.ccb.ha.fw.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admin_role_menus")
public class AdminRoleMenu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    Long rid;
    Long mid;

    public AdminRoleMenu() {
    }

    public AdminRoleMenu(Long rid, Long mid) {
        this.rid = rid;
        this.mid = mid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "AdminRoleMenu{" +
                "id=" + id +
                ", rid=" + rid +
                ", mid=" + mid +
                '}';
    }
}
