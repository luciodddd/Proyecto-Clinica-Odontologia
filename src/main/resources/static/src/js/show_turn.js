function showPatient(parametro,id)
{

          console.log("--PACIENTE--")
          //con fetch invocamos a la API de turnos con el método GET
          //nos devolverá un JSON con una colección de turnos
          let url = ""
          if (parametro=="idp"){
            url = '/turn/patient-id/'+id.toString();
          } else if (parametro=="ido"){
            url = '/turn/odontologist-id/'+id.toString()} else {
          throw "Hubo un error en los parámetros invocados"}
          console.log(url)
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
}
