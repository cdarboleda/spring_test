<#macro updatePasswordPagoDocenteBody>
    <#assign expirationMinutes = linkExpiration?number>
    <#assign expirationHours = expirationMinutes / 60>
    <#assign expirationHoursRounded = expirationHours?string("0.#")>
    <h1>Sistema de Gestión de Procesos SIGEPRO, Consejo de Posgrado - Alta de usuario</h1>
    <p>Hola usuario con número de indentificación: <strong>${user.username}</strong></p>
    <p>Para completar el proceso de registro en XX y activar su cuenta, por favor haga click en el siguiente enlace:</p>
    <a href="${link}" target="_blank">Establecer contraseña</a>
    <p>Este link estará vigente durante <strong>${expirationHoursRounded} horas</strong></p>
    <p>Si lo desea, una vez que haya activado su cuenta, podrá iniciar el proceso de pago en el Consejo de Posgado de la Facultad de Ingeniería y Ciencias Aplicadas de la UCE, desde https://vue-app.whiteocean-0e11a556.westus.azurecontainerapps.io</p>
</#macro>