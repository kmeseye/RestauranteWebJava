/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author tomas.fermoso
 */
public class Mesa {

    private String nombre;
    private List<Tablero> tableros;
    private Camarero camarero;
    private List<Plato> pedidos;
    private int comensales;

    public Mesa(String nombre, List<Tablero> tableros, Camarero camarero) {
        this.nombre = nombre;
        this.tableros = tableros;
        this.camarero = camarero;
        pedidos = new ArrayList<>();
    }

    public List<Plato> getPedidos() {
        return pedidos;
    }

    public void añadirPlato(Plato plato) {
        this.pedidos.add(plato);
    }

    public int getComensales() {
        return comensales;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public void infoMesa() {
        System.out.println("Mesa: " + nombre + ", atendida por: " + camarero.getNombre());
        System.out.println("Platos pedidos:");
        for (Plato p : pedidos) {
            System.out.println(p.getTipo() + ":" + p.getNombre());
        }

    }

    public float calcularPrecio(float precio_menu) {
        List<Plato> primeros = new ArrayList<>();
        List<Plato> segundos = new ArrayList<>();
        List<Plato> bebidas = new ArrayList<>();
        List<Plato> postres = new ArrayList<>();

        float total = 0;
        for (Plato p : pedidos) {
            switch (p.getTipo()) {
                case "primero":
                    primeros.add(p);
                    break;
                case "segundo":
                    segundos.add(p);
                    break;
                case "bebida":
                    bebidas.add(p);
                    break;
                case "postre":
                    postres.add(p);
                    break;
            }
        }
        Collections.sort(primeros, new PrecioComparator());
        Collections.sort(segundos, new PrecioComparator());
        Collections.sort(bebidas, new PrecioComparator());
        Collections.sort(postres, new PrecioComparator());

        float menu = 0;
        Plato[] menuPlato = {null, null, null, null};
        System.out.println("Ticket de la mesa");
        int cont=1;
        while (primeros.size() > 0 || segundos.size() > 0 || bebidas.size() > 0 || postres.size() > 0) {
            System.out.println("Menu"+cont++);           
            if (!primeros.isEmpty()) {
                menuPlato[0] = primeros.get(0);
            }
            if (!segundos.isEmpty()) {
                menuPlato[1] = segundos.get(0);
            }
            if (!bebidas.isEmpty()) {
                menuPlato[2] = bebidas.get(0);
            }
            if (!postres.isEmpty()) {
                menuPlato[3] = postres.get(0);
            }
            for (Plato plato : menuPlato) {
                if (plato != null) {
                    System.out.println(plato.getTipo() + ":............." + plato.getNombre());
                    menu += plato.getPrecio();
                    switch (plato.getTipo()) {
                        case "primero":
                            primeros.remove(plato);
                            break;
                        case "segundo":
                            segundos.remove(plato);
                            break;
                        case "bebida":
                            bebidas.remove(plato);
                            break;
                        case "postre":
                            postres.remove(plato);
                            break;
                    }
                }
            }
            if (menu > precio_menu) {
                total += precio_menu;
            } else {
                total += menu;
            }
//            System.out.println("Precio 'menu': " + menu);
            menu = 0;
            for (int i = 0; i < menuPlato.length; i++) {
                menuPlato[i]=null;
            }                         
        }
        return (total+this.comensales);
    }
    
    public String[] calcularTicket(float precio_menu) {
        String ticket="";
        String[] respuesta=new String[2];
        List<Plato> primeros = new ArrayList<>();
        List<Plato> segundos = new ArrayList<>();
        List<Plato> bebidas = new ArrayList<>();
        List<Plato> postres = new ArrayList<>();
        
        float total = 0;
        for (Plato p : pedidos) {
            switch (p.getTipo()) {
                case "primero":
                    primeros.add(p);
                    break;
                case "segundo":
                    segundos.add(p);
                    break;
                case "bebida":
                    bebidas.add(p);
                    break;
                case "postre":
                    postres.add(p);
                    break;
            }
        }
        Collections.sort(primeros, new PrecioComparator());
        Collections.sort(segundos, new PrecioComparator());
        Collections.sort(bebidas, new PrecioComparator());
        Collections.sort(postres, new PrecioComparator());

        float menu = 0;
        Plato[] menuPlato = {null, null, null, null};
        System.out.println("Ticket de la mesa");
        ticket+="Ticket de la mesa<br>";
        int cont=0;
        while (primeros.size() > 0 || segundos.size() > 0 || bebidas.size() > 0 || postres.size() > 0) {
            System.out.println("Menu"+cont++);
            ticket+="Menu "+cont +"<br>";
            if (!primeros.isEmpty()) {
                menuPlato[0] = primeros.get(0);
            }
            if (!segundos.isEmpty()) {
                menuPlato[1] = segundos.get(0);
            }
            if (!bebidas.isEmpty()) {
                menuPlato[2] = bebidas.get(0);
            }
            if (!postres.isEmpty()) {
                menuPlato[3] = postres.get(0);
            }
            for (Plato plato : menuPlato) {
                if (plato != null) {
                    System.out.println(plato.getTipo() + ":............." + plato.getNombre());
                    ticket+=plato.getTipo() + ":............." + plato.getNombre()+"<br>";
                    menu += plato.getPrecio();
                    switch (plato.getTipo()) {
                        case "primero":
                            primeros.remove(plato);
                            break;
                        case "segundo":
                            segundos.remove(plato);
                            break;
                        case "bebida":
                            bebidas.remove(plato);
                            break;
                        case "postre":
                            postres.remove(plato);
                            break;
                    }
                }
            }
            if (menu > precio_menu) {
                total += precio_menu;
            } else {
                total += menu;
            }
//            System.out.println("Precio 'menu': " + menu);
            ticket+="Precio menu: " + menu+"<br>";
            menu = 0;
            for (int i = 0; i < menuPlato.length; i++) {
                menuPlato[i]=null;
            }                         
        }
        respuesta[0]=ticket;
        respuesta[1]=String.format("%.2f", total);
        return respuesta;
    }
    
    class PrecioComparator implements Comparator<Plato> {

        @Override
        public int compare(Plato a, Plato b) {
            return a.getPrecio() < b.getPrecio() ? 1 : a.getPrecio() == b.getPrecio() ? 0 : -1;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Tablero> getTableros() {
        return tableros;
    }

}
