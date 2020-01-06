package com.tubespbp.DAO;

public class accountDAO {
    String username;
    String role;
    String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public accountDAO(String username, String role, String email) {
        this.username = username;
        this.role = role;
        this.email = email;
    }
}
