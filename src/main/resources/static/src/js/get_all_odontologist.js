window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de odontologistas con el método GET
      //nos devolverá un JSON con una colección de odontologistas
      const url = '/odontologist';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
        for(odontologist of data){

            //por cada odontologist armaremos una fila de la tabla
            //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos al odontólogo
            var table = document.getElementById("odontologistTable");
            var odontologistRow =table.insertRow();
            let tr_id = 'tr_' + odontologist.id;
            odontologistRow.id = tr_id;

            //por cada odontólogo creamos un boton delete que agregaremos en cada fila para poder eliminarlo
            //dicho boton invocara a la funcion de java script deleteByKey que se encargará
            //de llamar a la API para eliminar un odontólogo
            let deleteButton = '<button' +
                                ' id=' + '\"' + 'btn_delete_' + odontologist.id + '\"' +
                                ' type="button" onclick="deleteBy('+odontologist.id+')" class="btn btn-danger btn_delete">' +
                                '&times</button>';

            //por cada odontologist creamos un boton que muestra el id y que al hacerle clic invocará
            //a la función de java script findBy que se encargará de buscar al odontólogo que queremos
            //modificar y mostrará sus  datos en un formulario.
            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + odontologist.id + '\"' +
                                      ' type="button" onClick="goToUpdate('+odontologist.id+')" class="btn btn-info btn_id">Modificar</button>';

            let seeTurnsButton = '<button' +
                                  ' id=' + '\"' + 'btn_id_' + odontologist.id + '\"' +
                                  ' type="button" onclick="findTurnsBy('+odontologist.id+')" class="btn btn-info btn_id">Ver Turnos</button>';

            //armamos cada columna de la fila
            //como primer columna pondremos el boton modificar
            //luego los datos del odontólogo
            //como ultima columna el boton eliminar
            console.log(odontologist)
            odontologistRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_name\">' + odontologist.fullName.toUpperCase() + '</td>' +
                    '<td class=\"td_registrationNumber\">' + odontologist.registrationNumber.toUpperCase() + '</td>' +
                    '<td class=\"td_turns\">' + odontologist.numberOfTurns + '</td>' +
                    '<td>' + seeTurnsButton + '</td>' +
                    '<td>' + deleteButton + '</td>';
        };
      })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/odontologistList.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })

      const formulario = document.querySelector("#search");
      formulario.addEventListener('submit', function (event) {
            event.preventDefault();
            // Limpio la tabla y la vuelvo a crear:
            const OriginalTable = document.getElementById("odontologistTable");
            OriginalTable.innerHTML = '<thead><tr><th></th>'+
                                          '<th>Nombre</th>'+
                                          '<th>Matrícula</th>'+
                                          '<th>Cantidad de turnos</th>'+
                                          '<th></th><th></th>'+
                                          '</tr></thead><tbody id="odontologistTableBody">'+
                                          '</tbody>';

            //con fetch invocamos a la API de buscar odontólogo por Matrícula con el método GET
            const urlRegistrationNumber = '/odontologist/registrationNumber/'+document.querySelector("#registrationNumber-search").value;
            const settingsRegistrationNumber = {
              method: 'GET'
            }

            // Fetch
            fetch(urlRegistrationNumber,settingsRegistrationNumber)
            .then(response => response.json())
            .then(data => {
                  //armaremos una fila de la tabla con la info dle odontólogo buscado
                  var table = document.getElementById("odontologistTable");
                  var odontologistRow =table.insertRow();
                  let tr_id = 'tr_' + data.id;
                  odontologistRow.id = tr_id;

                  //por cada odontólogo creamos un boton delete que agregaremos en cada fila para poder eliminarlo
                  //dicho boton invocara a la funcion de java script deleteByKey que se encargará
                  //de llamar a la API para eliminar un odontólogo
                  let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + data.id + '\"' +
                                      ' type="button" onclick="deleteBy('+data.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

                  //por cada odontologist creamos un boton que muestra el id y que al hacerle clic invocará
                  //a la función de java script findBy que se encargará de buscar al odontólogo que queremos
                  //modificar y mostrará sus  datos en un formulario.
                  let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + data.id + '\"' +
                                      ' type="button" onClick="goToUpdate('+data.id+')" class="btn btn-info btn_id">Modificar</button>';

                  let seeTurnsButton = '<button' +
                                        ' id=' + '\"' + 'btn_id_' + data.id + '\"' +
                                        ' type="button" onclick="findTurnsBy('+data.id+')" class="btn btn-info btn_id">Ver Turnos</button>';

                  //armamos cada columna de la fila
                  //como primer columna pondremos el boton modificar
                  //luego los datos del odontólogo
                  //como ultima columna el boton eliminar
                  odontologistRow.innerHTML = '<td>' + updateButton + '</td>' +
                          '<td class=\"td_name\">' + data.fullName.toUpperCase() + '</td>' +
                          '<td class=\"td_registrationNumber\">' + data.registrationNumber.toUpperCase() + '</td>' +
                          '<td class=\"td_turns\">' + data.numberOfTurns + '</td>' +
                          '<td>' + seeTurnsButton + '</td>' +
                          '<td>' + deleteButton + '</td>';
    });
  })
})
function goToUpdate(id){
  window.location.href = window.location.href.split("/odontologist")[0]+"/odontologistUpdate.html?id="+id
}
function findTurnsBy(id){
  window.location.href = window.location.href.split("/")[0]+"turnList.html?ido="+id
}
