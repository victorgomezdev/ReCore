package CatsPrograming.ReCore.model;

import java.time.LocalDate;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String dni;
    private LocalDate joinDate;
    private LocalDate lastLogin;
    private Address address;

    public User(Integer id, String name, String email, String password, String dni, LocalDate joinDate, LocalDate lastLogin, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
        this.address = address;
    }

    public User(String name, String email, String password, String dni, LocalDate joinDate, LocalDate lastLogin, Address address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
