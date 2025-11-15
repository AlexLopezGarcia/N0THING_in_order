package org.n0thing.models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String email;
    private String passwordHash;
    private String nombre;
    private LocalDateTime fechaRegistro;

    //Contructor Vacio
    public User() {}

    //Contructor completo
    public User(int id, String email, String passwordHash, String nombre, LocalDateTime fechaRegistro) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPasswordHash() {return passwordHash;}

    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public LocalDateTime getFechaRegistro() {return fechaRegistro;}

    public void setFechaRegistro(LocalDateTime fechaRegistro) {this.fechaRegistro = fechaRegistro;}
}
