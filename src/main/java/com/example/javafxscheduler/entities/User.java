package com.example.javafxscheduler.entities;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Entity
@Table(name = "users", schema = "uebung07", catalog = "")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "role")
    private String role;

    public User() {
    }

    public User (String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "User {" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public void save() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");
            String name = this.getName();
            String email = this.getEmail();
            String password = this.getPassword();
            String role = this.getRole();

            String sql = "INSERT INTO users (name, email, password, role) VALUES ('" +
                    name +
                    "', '" + email +
                    "', '" + password +
                    "', '" + role + "');";

            PreparedStatement statement = con.prepareStatement(sql);

            try {
                statement.executeUpdate();
                System.out.println("Insert successful");
            } catch (Exception e) {
                System.out.println("Insert failed");
            }

        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }
    }

    public void delete() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.remove(this);
        session.getTransaction().commit();
        session.close();
    }
}
