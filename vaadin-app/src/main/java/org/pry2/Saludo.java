package org.pry2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Saludo {


    /*Atributos de la clase usuario:*/
    //@JsonProperty("id")
    //private int id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("dosis")
    private int dosis;

    public Saludo(){

    }

    public Saludo(String nombre, int dosis) {
        this.nombre = nombre;
        this.dosis = dosis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    @Override
    public String toString() {
        return "Saludo{" +
                "nombre='" + nombre + '\'' +
                ", dosis=" + dosis +
                '}';
    }

}
