window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos del turno
    const formulario = document.querySelector('#post-turn');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
       //creamos un JSON que tendrá los datos del turno
        const formData = {
            date: document.querySelector('#date').value,
            dni: document.querySelector('#dni').value,
            registrationNumber: document.querySelector('#registrationNumber').value
        };
        //invocamos utilizando la función fetch la API de turnos con el método POST que guardará
        //al missmo, que enviaremos en formato JSON
        const url = '/turn/save';
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
                 //Si no hay ningun error se muestra un mensaje diciendo que el turno
                 //se agrego bien
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Turno agregado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    //Si hay algun error se muestra un mensaje diciendo que el turno
                    //no se pudo guardar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     //se dejan todos los campos vacíos por si se quiere ingresar otro turno
                     resetUploadForm();})
    });


    function resetUploadForm(){
        document.querySelector('#date').value = "";
        document.querySelector('#dni').value = "";
         document.querySelector('#registrationNumber').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/turnList.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
})
