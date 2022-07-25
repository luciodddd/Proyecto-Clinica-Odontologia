window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos del odontólogo
    const formulario = document.querySelector('#post-odontologist');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
       //creamos un JSON que tendrá los datos del odontólogo
        const formData = {
            name: document.querySelector('#name').value,
            lastName: document.querySelector('#lastName').value,
            registrationNumber: document.querySelector('#registrationNumber').value
        };
        //invocamos utilizando la función fetch la API de odontólogos con el método POST que guardará
        //al missmo, que enviaremos en formato JSON
        const url = '/odontologist';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 //Si no hay ningun error se muestra un mensaje diciendo que el odontólogo
                 //se agrego bien
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Odontótolo agregado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    //Si hay algun error se muestra un mensaje diciendo que el odontólogo
                    //no se pudo guardar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     //se dejan todos los campos vacíos por si se quiere ingresar otro odontólogo
                     resetUploadForm();})
    });


    function resetUploadForm(){
        document.querySelector('#name').value = "";
        document.querySelector('#lastName').value = "";
         document.querySelector('#registrationNumber').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/odontologistList.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
})
