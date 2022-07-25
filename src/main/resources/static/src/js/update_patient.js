window.addEventListener('load', function () {
    const queryString = window.location.search;
    //const id = queryString.slice(-2)
    const id = queryString.slice(queryString.search("=")+1)
    const formulario = document.querySelector('#update-patient');

    // Cargo los datos del paciente que quiero modificar en el forms
    document.querySelector("h3").innerHTML = "Modificar paciente"
    const url = '/patient'+"/"+id;
    const settings = {
        method: 'GET'
    }
    fetch(url,settings)
    .then(response => response.json())
    .then(data => {
        document.querySelector('#btn-submit').name = data.id;
        document.querySelector('#name').value = data.name;
        document.querySelector('#lastName').value = data.lastName;
        document.querySelector('#dni').value = data.dni;
        document.querySelector('#entryDate').value = data.entryDate.slice(0,10);
        document.querySelector('#street').value = data.address.street;
        document.querySelector('#number').value = data.address.number;
        document.querySelector('#location').value = data.address.location;
        document.querySelector('#province').value = data.address.province;

    }).catch(error => {
        alert("Error: " + error);
    })
    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
        console.log(document.querySelector('#btn-submit').name)
       //creamos un JSON que tendrá los datos del paciente
        const formData = {
            id: parseInt(document.querySelector('#btn-submit').name),
            name: document.querySelector('#name').value,
            lastName: document.querySelector('#lastName').value,
            dni: document.querySelector('#dni').value,
            entryDate: document.querySelector('#entryDate').value,
            address: {
                      street: document.querySelector('#street').value,
                      number: document.querySelector('#number').value,
                      location: document.querySelector('#location').value,
                      province: document.querySelector('#province').value,
                      }
        };
        //invocamos utilizando la función fetch la API de pacientes con el método POST que guardará
        //al missmo, que enviaremos en formato JSON
        let urlUpdate = '/patient';
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
                 //Si no hay ningun error se muestra un mensaje diciendo que el paciente
                 //se modificó bien
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Paciente modificado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
            })
            .catch(error => {
                    //Si hay algun error se muestra un mensaje diciendo que el paciente
                    //no se pudo modificar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     })
    });
})


