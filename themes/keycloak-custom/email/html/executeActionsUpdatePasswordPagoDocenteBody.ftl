<#macro updatePasswordPagoDocenteBody>
    <#assign expirationMinutes = linkExpiration?number>
    <#assign expirationHours = expirationMinutes / 60>
    <#assign expirationHoursRounded = expirationHours?string("0.#")>
    <img class="logo" src="https://posgrado-fica-uce.netlify.app/img/logo-posgrado.0df946c7.png"
            alt="Logo posgrado">
        <h1>Sistema de Gestión de Procesos de Consejo de Posgrado SIGEPROCP</h1>
        <h2>Alta de usuario</h2>
        <br>
    <p>Saludos <strong>${user.firstName} ${user.lastName}</strong></p>
    <p>Se ha creado su cuenta con el usuario: <strong>${user.username} / ${user.email}</strong></p>
    <p>Para completar el proceso de registro en SIGEPRO y activar su cuenta, por favor haga click en el siguiente enlace:</p>
    <a href="${link}" target="_blank">Establecer contraseña</a>
    <p>Este link estará vigente durante <strong>${expirationHoursRounded} horas</strong></p>
    <p>Si lo desea, una vez que haya activado su cuenta, podrá ingresar al Sistema SIGEPRO Consejo de Posgrado de la Facultad de Ingeniería y Ciencias Aplicadas de la UCE desde https://posgrado-app.jollybush-eb2fdd97.westus.azurecontainerapps.io</p>
</#macro>