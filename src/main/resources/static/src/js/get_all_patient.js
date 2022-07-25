window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de patients con el método GET
      //nos devolverá un JSON con una colección de patients
      const url = '/patient';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
        for(patient of data){

            //por cada patient armaremos una fila de la tabla
            //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos al paciente
            var table = document.getElementById("patientTable");
            var patientRow =table.insertRow();
            let tr_id = 'tr_' + patient.id;
            patientRow.id = tr_id;

            //por cada paciente creamos un boton delete que agregaremos en cada fila para poder eliminarlo
            //dicho boton invocara a la funcion de java script deleteByKey que se encargará
            //de llamar a la API para eliminar un paciente
            let deleteButton = '<button' +
                                ' id=' + '\"' + 'btn_delete_' + patient.id + '\"' +
                                ' type="button" onclick="deleteBy('+patient.id+')" class="btn btn-danger btn_delete">' +
                                '&times</button>';

            //por cada patient creamos un boton que muestra el id y que al hacerle clic invocará
            //a la función de java script findBy que se encargará de buscar al paciente que queremos
            //modificar y mostrará sus  datos en un formulario.
            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + patient.id + '\"' +
                                      ' type="button" onClick="goToUpdate('+patient.id+')" class="btn btn-info btn_id">Modificar</button>';
//href="/patientUpdate.html?id='+patient.id+'"
            let seeTurnsButton = '<button' +
                                  ' id=' + '\"' + 'btn_id_' + patient.id + '\"' +
                                  ' type="button" onclick="findTurnsBy('+patient.id+')" class="btn btn-info btn_id">Ver Turnos</button>';

            //armamos cada columna de la fila
            //como primer columna pondremos el boton modificar
            //luego los datos del paciente
            //como ultima columna el boton eliminar
            patientRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_name\">' + patient.fullName.toUpperCase() + '</td>' +
                    '<td class=\"td_dni\">' + patient.dni.toUpperCase() + '</td>' +
                    '<td class=\"td_location\">' + patient.location.toUpperCase() + '</td>' +
                    '<td class=\"td_province\">' + patient.province.toUpperCase() + '</td>' +
                    '<td class=\"td_turns\">' + patient.numberOfTurns + '</td>' +
                    '<td>' + seeTurnsButton + '</td>' +
                    '<td>' + deleteButton + '</td>';
        };
      })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/patientList.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })

      const formulario = document.querySelector("#search");
      formulario.addEventListener('submit', function (event) {
            event.preventDefault();
            // Limpio la tabla y la vuelvo a crear:
            const OriginalTable = document.getElementById("patientTable");
            OriginalTable.innerHTML = '<thead><tr><th></th>'+
                                          '<th>Nombre</th>'+
                                          '<th>Dni</th>'+
                                          '<th>Localidad</th>'+
                                          '<th>Provincia</th>'+
                                        '<th>Cantidad de turnos</th>'+
                                          '<th></th><th></th>'+
                                      '</tr></thead><tbody id="patientTableBody">'+
                                      '</tbody>';

            //con fetch invocamos a la API de buscar paciente por DNI con el método GET
            const urlDNI = '/patient/dni/'+document.querySelector("#dni-search").value;
            const settingsDNI = {
              method: 'GET'
            }

            // Fetch
            fetch(urlDNI,settingsDNI)
            .then(response => response.json())
            .then(data => {
                  //armaremos una fila de la tabla con la info dle paciente buscado
                  var table = document.getElementById("patientTable");
                  var patientRow =table.insertRow();
                  let tr_id = 'tr_' + data.id;
                  patientRow.id = tr_id;

                  //por cada paciente creamos un boton delete que agregaremos en cada fila para poder eliminarlo
                  //dicho boton invocara a la funcion de java script deleteByKey que se encargará
                  //de llamar a la API para eliminar un paciente
                  let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + data.id + '\"' +
                                      ' type="button" onclick="deleteBy('+data.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

                  //por cada patient creamos un boton que muestra el id y que al hacerle clic invocará
                  //a la función de java script findBy que se encargará de buscar al paciente que queremos
                  //modificar y mostrará sus  datos en un formulario.
                  let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + data.id + '\"' +
                                      ' type="button" onClick="goToUpdate('+data.id+')" class="btn btn-info btn_id">Modificar</button>';

                  let seeTurnsButton = '<button' +
                                        ' id=' + '\"' + 'btn_id_' + data.id + '\"' +
                                        ' type="button" onclick="findTurnsBy('+data.id+')" class="btn btn-info btn_id">Ver Turnos</button>';

                  //armamos cada columna de la fila
                  //como primer columna pondremos el boton modificar
                  //luego los datos del paciente
                  //como ultima columna el boton eliminar
                  patientRow.innerHTML = '<td>' + updateButton + '</td>' +
                          '<td class=\"td_name\">' + data.fullName.toUpperCase() + '</td>' +
                          '<td class=\"td_dni\">' + data.dni.toUpperCase() + '</td>' +
                          '<td class=\"td_location\">' + data.location.toUpperCase() + '</td>' +
                          '<td class=\"td_province\">' + data.province.toUpperCase() + '</td>' +
                          '<td class=\"td_turns\">' + data.numberOfTurns + '</td>' +
                          '<td>' + seeTurnsButton + '</td>' +
                          '<td>' + deleteButton + '</td>';
    });
  })
})
function goToUpdate(id){
  window.location.href = window.location.href.split("/patient")[0]+"/patientUpdate.html?id="+id
}
function findTurnsBy(id){
  window.location.href = window.location.href.split("/")[0]+"turnList.html?idp="+id
}
