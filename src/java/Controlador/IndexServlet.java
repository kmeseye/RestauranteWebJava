/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import restaurante.Mesa;
import restaurante.Plato;
import restaurante.Restaurante;

/**
 *
 * @author tomas.fermoso
 */
public class IndexServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Restaurante restaurante = (Restaurante) request.getSession().getAttribute("restaurante");
        if (restaurante == null) {
            restaurante = new Restaurante(20);
            restaurante.leerCarta("C:\\Users\\tomas.fermoso\\Documents\\NetBeansProjects\\Restaurante\\src\\restaurante\\platos.json");
            restaurante.contratarCamareros("C:\\Users\\tomas.fermoso\\Documents\\NetBeansProjects\\Restaurante\\src\\restaurante\\camareros.json");
            request.getSession().setAttribute("restaurante", restaurante);
        }
        request.setAttribute("tableros", restaurante.getTableros());
        request.setAttribute("carta", restaurante.getCarta());
        String comensales = request.getParameter("comensales");
        if (comensales != null) {
            restaurante.asignarMesa(Integer.parseInt(comensales));
            response.sendRedirect("/RestauranteWeb");
        } else if (request.getParameter("idMesa") != null) { 
            String nombre=request.getParameter("idMesa");
            String[] infoTicket=restaurante.obtenerTicket(nombre);
            request.setAttribute("ticket", infoTicket);
            request.setAttribute("mesas", restaurante.getMesas());
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
        } else {
//            restaurante.infoRestaurante();
            request.setAttribute("mesas", restaurante.getMesas());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreMesa = request.getParameter("mesa");

        String plato = request.getParameter("plato");
        Restaurante restaurante = (Restaurante) request.getSession().getAttribute("restaurante");

        restaurante.llevarPlato(nombreMesa, restaurante.obtenerPlato(plato));
        Mesa mesa = restaurante.buscarMesa(nombreMesa);
        PrintWriter out = response.getWriter();
        String respuesta = "";
        if (mesa != null) {
            respuesta += "<div>Numero de comensales: " + mesa.getComensales() + "</div><ul>";
            for (Plato p : mesa.getPedidos()) {
                respuesta += "<li>" + p.getNombre() + "</li>";
            }
            respuesta += "</ul>";
        }
        out.write(respuesta);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
