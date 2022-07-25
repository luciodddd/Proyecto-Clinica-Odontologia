window.addEventListener('load', function () {
    const queryString = window.location.search;
    //const id = queryString.slice(-2)
    const id = queryString.slice(queryString.search("=")+1)
    const formulario = document.querySelector('#update-turn');

    // Cargo los datos del turno que quiero modificar en el forms
    const url = '/turn'+"/"+id;
    const settings = {
        method: 'GET'
    }
    fetch(url,settings)
    .then(response => response.json())
    .then(data => {
        document.querySelector('#update-turn').name = data.id;
        document.querySelector('#date').value = data.date.slice(0,10);
        document.querySelector('#dni').value = data.patient.dni;
        document.querySelector('#registrationNumber').value = data.odontologist.registrationNumber;
    }).catch(error => {
        alert("Error: " + error);
    })
    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

       //creamos un JSON que tendrá los datos del turno
        const formData = {
            id: parseInt(document.querySelector('#update-turn').name),
            date: document.querySelector('#date').value,
            dni: document.querySelector('#dni').value,
            registrationNumber: document.querySelector('#registrationNumber').value
        };
        //invocamos utilizando la función fetch la API de turnos con el método POST que guardará
        //al missmo, que enviaremos en formato JSON
        let urlUpdate = '/turn';
        let settingsUpdate = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(urlUpdate, settingsUpdate)
            .then(response => response.json())
            .then(data => {
                 //Si no hay ningun error se muestra un mensaje diciendo que el turno
                 //se modificó bien
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Turno modificado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
            })
            .catch(error => {
                    //Si hay algun error se muestra un mensaje diciendo que el turno
                    //no se pudo modificar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     })
    });
})


