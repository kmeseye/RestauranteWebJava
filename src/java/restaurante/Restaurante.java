/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author tomas.fermoso
 */
public class Restaurante {

    private int num_tableros;
    private List<Mesa> mesas;
    private List<Tablero> tableros;
    private List<Camarero> camareros;
    private List<Plato> carta;
    private final float PRECIO_MENU = (float) 20.00;

    public List<Tablero> getTableros() {
        return tableros;
    }

    public Restaurante(int num_tableros) {
        this.num_tableros = num_tableros;
        tableros = webservice.obtenerTableros(num_tableros);
        mesas = new ArrayList<>();
        camareros = new ArrayList<>();
        carta = new ArrayList<>();

    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public List<Plato> getCarta() {
        return carta;
    }

    public void infoRestaurante() {
        System.out.println("El restaurante tiene: " + mesas.size() + " mesas");
        System.out.println("El restaurante tiene: " + tableros.size() + " tableros");
        for (Mesa mesa : this.mesas) {
            mesa.infoMesa();
        }
    }

    public void asignarMesa(int comensales) {

        int num_tableros = comensales / Tablero.personas;
        if (comensales % Tablero.personas > 0) {
            num_tableros++;
        }
        if (num_tableros <= tableros.size()) {
            List<Tablero> tab = new ArrayList<>();
            for (int i = 0; i < num_tableros; i++) {
                tab.add(tableros.remove(0));
            }
            String nombre = tab.get(0).getNombre();
            Camarero cam = this.getCamarero();
            cam.incrementarServicio();
            Mesa m = new Mesa(nombre, tab, cam);
            m.setComensales(comensales);
            mesas.add(m);

        } else {
            System.out.println("No hay sitio disponible");
        }
    }

    public void leerCarta(String fichero) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        FileReader fr;
        fr = new FileReader(fichero);
        JsonElement datos = parser.parse(fr);
        //Obtenemos images
//        JsonObject jobject = datos.getAsJsonObject();
//        JsonArray platos = jobject.getAsJsonArray("platos");       
        JsonArray platos = datos.getAsJsonArray();
        for (JsonElement p : platos) {
            String tipo = p.getAsJsonObject().get("tipo").getAsString();
            String nombre = p.getAsJsonObject().get("nombre").getAsString();
            int id = Integer.parseInt(p.getAsJsonObject().get("id").toString());
            float precio = Float.parseFloat(p.getAsJsonObject().get("precio").toString());
            this.carta.add(new Plato(tipo, nombre, id, precio));
        }
    }

    public void contratarCamareros(String fichero) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        FileReader fr;
        fr = new FileReader(fichero);
        JsonElement datos = parser.parse(fr);
        //Obtenemos images
//        JsonObject jobject = datos.getAsJsonObject();
//        JsonArray platos = jobject.getAsJsonArray("platos");       
        JsonArray platos = datos.getAsJsonArray();
        for (JsonElement p : platos) {
            String nombre = p.getAsJsonObject().get("nombre").getAsString();
            int id = Integer.parseInt(p.getAsJsonObject().get("id").toString());
            this.camareros.add(new Camarero(nombre, id));
        }
    }

    private Camarero getCamarero() {
        Camarero camareroDisponible = this.camareros.get(0);
        for (Camarero cam : this.camareros) {
            if (camareroDisponible.getServicios() > cam.getServicios()) {
                camareroDisponible = cam;
            }
        }
        return camareroDisponible;
    }

    public void llevarPlato(String mesaSel, Plato plato) {
        for (Mesa mesa : mesas) {
            if (mesa.getNombre().equals(mesaSel)) {
                mesa.añadirPlato(plato);
                break;
            }
        }
    }

    public Plato obtenerPlato(String nombrePlato) {
        for (Plato p : carta) {
            if (p.getNombre().equals(nombrePlato)) {
                return p;
            }
        }
        return null;
    }

    public Plato obtenerPlato(int idPlato) {
        for (Plato p : carta) {
            if (p.getId() == idPlato) {
                return p;
            }
        }
        return null;
    }

    public Mesa buscarMesa(String nombreMesa) {
        for (Mesa mesa : mesas) {
            if (mesa.getNombre().equals(nombreMesa)) {
                return mesa;
            }
        }
        return null;
    }

    public void cerrarMesa(String nombreMesa) {
        Mesa mesa = this.buscarMesa(nombreMesa);
        if (mesa != null) {
            System.out.println("El total es:" + mesa.calcularPrecio(this.PRECIO_MENU));

            for (Tablero t : mesa.getTableros()) {
                this.tableros.add(t);
            }
            this.mesas.remove(mesa);
            
        }
    }

    public String[] obtenerTicket(String nombreMesa) {
        Mesa mesa = this.buscarMesa(nombreMesa);
        if (mesa != null) {
//            System.out.println("El total es:" + mesa.calcularPrecio(this.PRECIO_MENU));
            String[] resultado=mesa.calcularTicket(PRECIO_MENU);
            for (Tablero t : mesa.getTableros()) {
                this.tableros.add(t);
            }
            this.mesas.remove(mesa);
            return resultado;
        }
        return null;
    }

    public List<Plato> platos(String tipo) {
        List<Plato> primeros = new ArrayList<>();
        List<Plato> segundos = new ArrayList<>();
        List<Plato> bebidas = new ArrayList<>();
        List<Plato> postres = new ArrayList<>();

        for (Plato p : this.carta) {
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
        switch (tipo) {
            case "primeros":
                return primeros;
            case "segundos":
                return segundos;
            case "bebidas":
                return bebidas;
            case "postres":
                return postres;
            default:
                return null;

        }
    }

}
