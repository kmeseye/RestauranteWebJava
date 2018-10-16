/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

/**
 *
 * @author tomas.fermoso
 */
public class Camarero {
    private String nombre;
    private int id;
    private int servicios;

    public Camarero(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        this.servicios=0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServicios() {
        return servicios;
    }

    public void setServicios(int servicios) {
        this.servicios = servicios;
    }
    
    public void incrementarServicio(){
        this.servicios++;
    }
    
}
