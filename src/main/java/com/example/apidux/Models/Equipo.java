package com.example.apidux.Models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="equipos")
public class Equipo implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String liga;
    private String pais;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
