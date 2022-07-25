window.addEventListener('load', function () {
    const formulario = document.querySelector('#login');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
        if (document.querySelector('#password').value=="admin" && document.querySelector('#dni').value=="admin") {
                             window.location.href = window.location.origin+"/admin-panel.html"}
        else {
        //invocamos utilizando la función fetch la API de turnos con el método POST que guardará
        //al missmo, que enviaremos en formato JSON
        let url = '/login/'+document.querySelector('#dni').value;
        let settings = {method: 'GET'}

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 //Si no hay ningun error se muestra un mensaje diciendo que el turno
                 //se modificó bien
                 console.log(document.querySelector('#password').value);
                 console.log(data.password);
                 if(document.querySelector('#password').value==data.password){
                  window.location.href = window.location.href.split("/")[0]+"turnList.html?idpu="+data.id
                 }
                 else {
                          let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                                       '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                                       '<strong></strong> Error, datos incorrectos </div>'
                          document.querySelector('#response').innerHTML = successAlert;
                 }


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
                     })}
    });
})


