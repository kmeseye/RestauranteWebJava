<%-- 
    Document   : index
    Created on : 24-ago-2018, 9:35:06
    Author     : tomas.fermoso
--%>

<%@page import="restaurante.Plato"%>
<%@page import="restaurante.Mesa"%>
<%@page import="java.util.List"%>
<%@page import="restaurante.Tablero"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Restaurante</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"/>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
        <script src="${pageContext.request.contextPath}/js/index.js"></script>
    </head>
    <body>
        <div class="container">
            <h1>Restaurante Web</h1>            
            <div class="row">
                <div class="col-md-4 col-sm-12">
                    <h2>Tableros disponibles</h2>
                    <div class="row" style="border:1px solid black">
                        <%
                            String html = "";
                            List<Tablero> tableros = (List<Tablero>) request.getAttribute("tableros");
                            for (Tablero tab : tableros) {
                                html += "<div class='col-md-4'>"
                                        + "<img class='bandera' src='" + tab.getBandera() + "'>"
                                        + "</div>";
                            }
                        %>
                        <%=html%>
                    </div>
                </div>
                <div class="col-md-4 col-sm-12">
                    <h2>Solicitar Mesa</h2>
                    <form action="" class="form" method="get">
                        <input class="form-control" placeholder="Número de comensales" type="number" name="comensales">
                        <br>
                        <input class="btn btn-success" type="submit" value="Solicitar mesa">
                    </form>
                </div>
                <div class="col-md-4 col-sm-12">
                    <h2>Mesas</h2>
                    <%
                        String mesas = "";
                        List<Mesa> listaMesas = (List<Mesa>) request.getAttribute("mesas");
                        if (listaMesas != null) {
                            for (Mesa m : listaMesas) {
                                String mesa = "";
                                for (Tablero tab : m.getTableros()) {
                                    if (m.getTableros().indexOf(tab) == 0) {
                                        mesa += "<div >"
                                                + "<img class='bandera' src='" + tab.getBandera() + "'>"
                                                + "</div>";
                                    } else {
                                        mesa += "<div class='bandera' >"
                                                + "</div>";
                                    }
                                }

                                mesas += "<div class='me mesa' id='" + m.getNombre() + "'>"
                                        + mesa
                                        + "</div>";
                            }
                        }
                    %>
                    <%=mesas%>
                </div>
            </div>
            <hr>

            <div class="row">
                <div class="col-md-8">      
                    <h3>Mesa</h3>
                    <div class="row">
                        <div class="col-md-6" id="mesaSel">
                            <div id="mesaId"></div>
                            <div id="infoMesa"></div>
                            <form action="/RestauranteWeb">
                                <input type="hidden" name="idMesa" id="id_mesa">
                                <input id="btnPagar" class="btn btn-success" type="submit" value="Pagar">
                            </form>
                        </div>
                        <div class="col-md-6" id="infoTicket">
                            <%
                                String[] infoTicket=(String[]) request.getAttribute("ticket");
                                String htmlTicket="";
                                
                                if(infoTicket!=null){                                    
                                    htmlTicket+="<h4>Ticket de la mesa</h4>";
                                    htmlTicket+="<p>"+infoTicket[0]+"</p>";
                                    htmlTicket+="<div>Total ticket: "+infoTicket[1]+" € </div>";
                                }                                
                            %>
                            <%=htmlTicket%>
                        </div>
                    </div>
                    
                </div>
                <div class="col-md-4">
                    <h3>Carta</h3>
                    <%
                        List<Plato> carta = (List<Plato>) request.getAttribute("carta");
                        String pr = "", s = "", b = "", ps = "";

                        for (Plato p : carta) {
                            if ("primero".equals(p.getTipo())) {
                                pr += "<li class='row' id='" + p.getNombre() + "'><p class='col-sm-9'>" + p.getNombre() + "</p><i class='col-sm-2 fas fa-cart-plus'></i></li>";
                            }
                            if ("segundo".equals(p.getTipo())) {
                                s += "<li class='row' id='" + p.getNombre() + "'><p class='col-sm-9'>" + p.getNombre() + "</p><i class='col-sm-2 fas fa-cart-plus'></i></li>";
                            }
                            if ("bebida".equals(p.getTipo())) {
                                b += "<li class='row' id='" + p.getNombre() + "'><p class='col-sm-9'>" + p.getNombre() + "</p><i class='col-sm-2 fas fa-cart-plus'></i></li>";
                            }
                            if ("postre".equals(p.getTipo())) {
                                ps += "<li class='row' id='" + p.getNombre() + "'><p class='col-sm-9'>" + p.getNombre() + "</p><i class='col-sm-2 fas fa-cart-plus'></i></li>";
                            }
                        }
                    %>
                    <div class="row carta">
                        <div class="col-md-6">
                            <h4>Primeros</h4>
                            <ul><%=pr%></ul>
                        </div>
                        <div class="col-md-6">
                            <h4>Segundos</h4>
                            <ul><%=s%></ul>
                        </div>
                        <div class="col-md-6">
                            <h4>Bebidas</h4>
                            <ul><%=b%></ul>
                        </div>
                        <div class="col-md-6">
                            <h4>Postres</h4>
                            <ul><%=ps%></ul>
                        </div>                                                                                             
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
