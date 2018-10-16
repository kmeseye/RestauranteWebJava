/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomas.fermoso
 */
public class webservice {

    public static List<Tablero> obtenerTableros(int numeroTableros) {

        URL url;
        List<Tablero> listaTableros=new ArrayList<>();
        try {
            // Creando un objeto URL
            url = new URL("https://restcountries.eu/rest/v2/region/europe");

            // Realizando la petición GET
            URLConnection con = url.openConnection();

            // Leyendo el resultado
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String linea;
            linea = in.readLine();
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(linea);
       
            JsonArray paises = datos.getAsJsonArray();            
            System.out.println(paises.size());
            for (int i = 0; i < numeroTableros; i++) {
                JsonElement pais=paises.get(i);
                String nombre=pais.getAsJsonObject().get("name").getAsString();
                nombre=nombre.replace("Ã…","A");                
                String bandera=pais.getAsJsonObject().get("flag").getAsString();
                listaTableros.add(new Tablero(nombre,bandera));
            }            
        } catch (IOException ex) {
            Logger.getLogger(webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTableros;
    }
}
