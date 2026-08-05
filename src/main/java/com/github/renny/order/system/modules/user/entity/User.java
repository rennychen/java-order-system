package com.github.renny.order.system.modules.user.entity;

import com.github.renny.order.system.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,updatable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false,length = 20)
    private String userName;
    @Version
    @Column(nullable = false)
    private Long version = 0L;

    protected User(){}

    public User(String email,String password,String userName){
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public Long getId(){ return id; }
    public String getEmail(){ return email; }
    public String getUserName(){ return userName; }
    public String getPassword(){ return password; }
    public Long getVersion(){ return version; }

    public void changePassword(String password){ this.password = password; }
    public void changeUserName(String userName){ this.userName = userName; }
}
