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
public class Tablero {
    public static int personas=2; 
    private String nombre;
    private String bandera;

    public Tablero(String nombre, String bandera) {
        this.nombre = nombre;
        this.bandera = bandera;
    }

    public String getNombre() {
        return nombre;
    }

    public String getBandera() {
        return bandera;
    }
    
}
