/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('document').ready(() => {
    $('#mesaSel').attr('class', 'ocultar');
    if (localStorage.getItem("mesaSeleccionada")) {
        $('#mesaId').html(localStorage.getItem("mesaSeleccionada"));
    }


    $('.me').click((m) => {
        for (var i = 0; i < $('.me').length; i++) {
            if ($('.me')[i] === m.currentTarget) {
                $('#infoTicket').html("");
                m.currentTarget.className = "me mesaSeleccionada";
                var band = m.currentTarget.children[0];
                var img = "<img class='" + $(band.children[0]).attr('class') + "' src='" + $(band.children[0]).attr('src') + "'>";
                var mes = "<span id='Mesa" + m.currentTarget.id + "'>Mesa " + m.currentTarget.id + "</span>";
                $('#mesaId').html(img + mes);
                localStorage.setItem("mesaSeleccionada", img + mes);
                localStorage.setItem("mesaId", m.currentTarget.id);
                $('#id_mesa').val(m.currentTarget.id);
                $('#mesaSel').removeClass('ocultar');
                $('#mesaSel').attr('class', 'mostrar');
                if (localStorage.getItem(m.currentTarget.id)) {
                    $('#infoMesa').html(localStorage.getItem(m.currentTarget.id));
                } else {
                    $('#infoMesa').html("");
                }


            } else {
                $('.me')[i].className = "me mesa";
            }

        }

    });

    $('.fas').click((ele) => {
        var plato = $(ele.currentTarget).siblings('p').html();
        var mesa = localStorage.getItem("mesaId");
        var datos = {"mesa": mesa, "plato": plato};
        $.ajax({
            type: 'POST',
            dataType: 'text',
            url: "/RestauranteWeb",
            data: datos,
            success: (dat) => {
                $('#infoMesa').html(dat);
                localStorage.setItem(localStorage.getItem("mesaId"), dat);
            },
            error: (err) => {
                console.log(err);
            }

        });


    });

    $('#btnPagar').click(() => {
        var id = localStorage.getItem("mesaId");
        localStorage.removeItem(id);
        localStorage.removeItem("mesaSeleccionada");
        localStorage.removeItem("mesaId");

    })
});
