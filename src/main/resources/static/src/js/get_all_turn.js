window.addEventListener('load', function () {
  const formularioOdontologistRegistrationNumber = document.querySelector("#search-registrationNumber");
  const formularioPatientDni = document.querySelector("#search-dni");
  const formularioDate = document.querySelector("#search-date");

  const queryString = window.location.search;
  console.log(queryString);
  if (queryString=="") {

          //con fetch invocamos a la API de turnos con el método GET
          //nos devolverá un JSON con una colección de turnos
          const url = '/turn';
          const settings = {
            method: 'GET'
          }

          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
            for(turn of data){

                //por cada turn armaremos una fila de la tabla
                //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos al turno
                var table = document.getElementById("turnTable");
                var turnRow =table.insertRow();
                let tr_id = 'tr_' + turn.id;
                turnRow.id = tr_id;

                //por cada turno creamos un boton delete que agregaremos en cada fila para poder eliminarlo
                //dicho boton invocara a la funcion de java script deleteByKey que se encargará
                //de llamar a la API para eliminar un turno
                let deleteButton = '<button' +
                                    ' id=' + '\"' + 'btn_delete_' + turn.id + '\"' +
                                    ' type="button" onclick="deleteBy('+turn.id+')" class="btn btn-danger btn_delete">' +
                                    '&times</button>';

                //por cada turn creamos un boton que muestra el id y que al hacerle clic invocará
                //a la función de java script findBy que se encargará de buscar al turno que queremos
                //modificar y mostrará sus  datos en un formulario.
                let updateButton = '<button' +
                                          ' id=' + '\"' + 'btn_id_' + turn.id + '\"' +
                                          ' type="button" onClick="goToUpdate('+turn.id+')" class="btn btn-info btn_id">Modificar</button>';

                //armamos cada columna de la fila
                //como primer columna pondremos el boton modificar
                //luego los datos del turno
                //como ultima columna el boton eliminar
                turnRow.innerHTML = '<td>' + updateButton + '</td>' +
                        '<td class=\"td_date\">' + turn.date.slice(0,10) + '</td>' +
                        '<td class=\"td_patientName\">' + turn.patientName.toUpperCase() + '</td>' +
                        '<td class=\"td_patientDni\">' + turn.patientDni + '</td>' +
                        '<td class=\"td_odontologistName\">' + turn.odontologistName.toUpperCase() + '</td>' +
                        '<td class=\"td_odontologistRegistrationNumber\">' + turn.odontologistRegistrationNumber + '</td>' +
                        '<td>' + deleteButton + '</td>';
            };
          })

  } else if (queryString.slice(1,queryString.search("="))=="idpu"){
      document.querySelector("#ulTurnos").innerHTML="";
      showPatient(queryString.slice(1,queryString.search("=")),queryString.slice(queryString.search("=")+1));
  }
  else {
    showPatient(queryString.slice(1,queryString.search("=")),queryString.slice(queryString.search("=")+1));
  }



    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/turnList.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })


      formularioPatientDni.addEventListener('submit', function (event) {
            event.preventDefault();
            // Limpio la tabla y la vuelvo a crear:
            const OriginalTable = document.getElementById("turnTable");
            OriginalTable.innerHTML = '<thead><tr><th></th>'+
                                          '<th>Fecha</th>'+
                                          '<th>Paciente</th>'+
                                          '<th>Dni</th>'+
                                          '<th>Odontólogo</th>'+
                                          '<th>Matrícula</th>'+
                                          '<th></th>'+
                                          '</tr></thead><tbody id="turnTableBody">'+
                                          '</tbody>';

            //con fetch invocamos a la API de buscar turno por Dni con el método GET
            const urlDni = '/turn/patient-dni/'+document.querySelector("#dni-search").value;
            const settingsDni = {
              method: 'GET'
            }

            // Fetch
            fetch(urlDni,settingsDni)
            .then(response => response.json())
            .then(data => {
                  for(turn of data){
                  //armaremos una fila de la tabla con la info de los turnos buscados
                  var table = document.getElementById("turnTable");
                  var turnRow =table.insertRow();
                  let tr_id = 'tr_' + turn.id;
                  turnRow.id = tr_id;

                  //por cada turno creamos un boton delete que agregaremos en cada fila para poder eliminarlo
                  //dicho boton invocara a la funcion de java script deleteByKey que se encargará
                  //de llamar a la API para eliminar un turno
                  let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + turn.id + '\"' +
                                      ' type="button" onclick="deleteBy('+turn.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

                  //por cada turn creamos un boton que muestra el id y que al hacerle clic invocará
                  //a la función de java script findBy que se encargará de buscar al turno que queremos
                  //modificar y mostrará sus  datos en un formulario.
                  let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + turn.id + '\"' +
                                      ' type="button" onClick="goToUpdate('+turn.id+')" class="btn btn-info btn_id">Modificar</button>';

                  let seeTurnsButton = '<button' +
                                        ' id=' + '\"' + 'btn_id_' + turn.id + '\"' +
                                        ' type="button" onclick="findTurnsBy('+turn.id+')" class="btn btn-info btn_id">Ver Turnos</button>';

                  //armamos cada columna de la fila
                  //como primer columna pondremos el boton modificar
                  //luego los datos del turno
                  //como ultima columna el boton eliminar
                  turnRow.innerHTML = '<td>' + updateButton + '</td>' +
                                      '<td class=\"td_date\">' + turn.date.slice(0,10) + '</td>' +
                                      '<td class=\"td_patientName\">' + turn.patientName.toUpperCase() + '</td>' +
                                      '<td class=\"td_patientDni\">' + turn.patientDni + '</td>' +
                                      '<td class=\"td_odontologistName\">' + turn.odontologistName.toUpperCase() + '</td>' +
                                      '<td class=\"td_odontologistRegistrationNumber\">' + turn.odontologistRegistrationNumber + '</td>' +
                                      '<td>' + deleteButton + '</td>'
                  }
    });
  })

      formularioOdontologistRegistrationNumber.addEventListener('submit', function (event) {
              event.preventDefault();
              // Limpio la tabla y la vuelvo a crear:
              const OriginalTable = document.getElementById("turnTable");
              OriginalTable.innerHTML = '<thead><tr><th></th>'+
                                            '<th>Fecha</th>'+
                                            '<th>Paciente</th>'+
                                            '<th>Dni</th>'+
                                            '<th>Odontólogo</th>'+
                                            '<th>Matrícula</th>'+
                                            '<th></th>'+
                                            '</tr></thead><tbody id="turnTableBody">'+
                                            '</tbody>';

              //con fetch invocamos a la API de buscar turno por Dni con el método GET
              const urlRegistrationNumber = '/turn/odontologist-registrationNumber/'+document.querySelector("#registrationNumber-search").value;
              const settingsRegistrationNumber = {
                method: 'GET'
              }

              // Fetch
              fetch(urlRegistrationNumber,settingsRegistrationNumber)
              .then(response => response.json())
              .then(data => {
                    for(turn of data){
                    //armaremos una fila de la tabla con la info de los turnos buscados
                    var table = document.getElementById("turnTable");
                    var turnRow =table.insertRow();
                    let tr_id = 'tr_' + turn.id;
                    turnRow.id = tr_id;

                    //por cada turno creamos un boton delete que agregaremos en cada fila para poder eliminarlo
                    //dicho boton invocara a la funcion de java script deleteByKey que se encargará
                    //de llamar a la API para eliminar un turno
                    let deleteButton = '<button' +
                                        ' id=' + '\"' + 'btn_delete_' + turn.id + '\"' +
                                        ' type="button" onclick="deleteBy('+turn.id+')" class="btn btn-danger btn_delete">' +
                                        '&times' +
                                        '</button>';

                    //por cada turn creamos un boton que muestra el id y que al hacerle clic invocará
                    //a la función de java script findBy que se encargará de buscar al turno que queremos
                    //modificar y mostrará sus  datos en un formulario.
                    let updateButton = '<button' +
                                        ' id=' + '\"' + 'btn_id_' + turn.id + '\"' +
                                        ' type="button" onClick="goToUpdate('+turn.id+')" class="btn btn-info btn_id">Modificar</button>';

                    let seeTurnsButton = '<button' +
                                          ' id=' + '\"' + 'btn_id_' + turn.id + '\"' +
                                          ' type="button" onclick="findTurnsBy('+turn.id+')" class="btn btn-info btn_id">Ver Turnos</button>';

                    //armamos cada columna de la fila
                    //como primer columna pondremos el boton modificar
                    //luego los datos del turno
                    //como ultima columna el boton eliminar
                    turnRow.innerHTML = '<td>' + updateButton + '</td>' +
                                        '<td class=\"td_date\">' + turn.date.slice(0,10) + '</td>' +
                                        '<td class=\"td_patientName\">' + turn.patientName.toUpperCase() + '</td>' +
                                        '<td class=\"td_patientDni\">' + turn.patientDni + '</td>' +
                                        '<td class=\"td_odontologistName\">' + turn.odontologistName.toUpperCase() + '</td>' +
                                        '<td class=\"td_odontologistRegistrationNumber\">' + turn.odontologistRegistrationNumber + '</td>' +
                                        '<td>' + deleteButton + '</td>'
                    }
      });
    })

    formularioDate.addEventListener('submit', function (event) {
                  event.preventDefault();
                  // Limpio la tabla y la vuelvo a crear:
                  const OriginalTable = document.getElementById("turnTable");
                  OriginalTable.innerHTML = '<thead><tr><th></th>'+
                                                '<th>Fecha</th>'+
                                                '<th>Paciente</th>'+
                                                '<th>Dni</th>'+
                                                '<th>Odontólogo</th>'+
                                                '<th>Matrícula</th>'+
                                                '<th></th>'+
                                                '</tr></thead><tbody id="turnTableBody">'+
                                                '</tbody>';


                  //con fetch invocamos a la API de buscar turno por Fecha con el método GET
                  const formData = {
                  initialDate: document.querySelector("#minDate-search").value,
                  finalDate: document.querySelector("#maxDate-search").value}
                  const urlDate = '/turn/by-date/'+document.querySelector("#minDate-search").value+"/"+document.querySelector("#maxDate-search").value;
                  const settingsDate = {
                    method: 'GET',
                    /*headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(formData)*/
                  }

                  // Fetch
                  fetch(urlDate,settingsDate)
                  .then(response => response.json())
                  .then(data => {
                        for(turn of data){
                        //armaremos una fila de la tabla con la info de los turnos buscados
                        var table = document.getElementById("turnTable");
                        var turnRow =table.insertRow();
                        let tr_id = 'tr_' + turn.id;
                        turnRow.id = tr_id;

                        //por cada turno creamos un boton delete que agregaremos en cada fila para poder eliminarlo
                        //dicho boton invocara a la funcion de java script deleteByKey que se encargará
                        //de llamar a la API para eliminar un turno
                        let deleteButton = '<button' +
                                            ' id=' + '\"' + 'btn_delete_' + turn.id + '\"' +
                                            ' type="button" onclick="deleteBy('+turn.id+')" class="btn btn-danger btn_delete">' +
                                            '&times' +
                                            '</button>';

                        //por cada turn creamos un boton que muestra el id y que al hacerle clic invocará
                        //a la función de java script findBy que se encargará de buscar al turno que queremos
                        //modificar y mostrará sus  datos en un formulario.
                        let updateButton = '<button' +
                                            ' id=' + '\"' + 'btn_id_' + turn.id + '\"' +
                                            ' type="button" onClick="goToUpdate('+turn.id+')" class="btn btn-info btn_id">Modificar</button>';

                        let seeTurnsButton = '<button' +
                                              ' id=' + '\"' + 'btn_id_' + turn.id + '\"' +
                                              ' type="button" onclick="findTurnsBy('+turn.id+')" class="btn btn-info btn_id">Ver Turnos</button>';

                        //armamos cada columna de la fila
                        //como primer columna pondremos el boton modificar
                        //luego los datos del turno
                        //como ultima columna el boton eliminar
                        turnRow.innerHTML = '<td>' + updateButton + '</td>' +
                                            '<td class=\"td_date\">' + turn.date.slice(0,10) + '</td>' +
                                            '<td class=\"td_patientName\">' + turn.patientName.toUpperCase() + '</td>' +
                                            '<td class=\"td_patientDni\">' + turn.patientDni + '</td>' +
                                            '<td class=\"td_odontologistName\">' + turn.odontologistName.toUpperCase() + '</td>' +
                                            '<td class=\"td_odontologistRegistrationNumber\">' + turn.odontologistRegistrationNumber + '</td>' +
                                            '<td>' + deleteButton + '</td>'
                        }
          });
        })
})
function goToUpdate(id){
  window.location.href = window.location.href.split("/turn")[0]+"/turnUpdate.html?id="+id
}
