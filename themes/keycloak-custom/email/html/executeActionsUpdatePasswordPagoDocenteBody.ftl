<#macro updatePasswordPagoDocenteBody>
    <#assign expirationMinutes = linkExpiration?number>
    <#assign expirationHours = expirationMinutes / 60>
    <#assign expirationHoursRounded = expirationHours?string("0.#")>
    <img class="logo" src="https://posgrado-fica-uce.netlify.app/img/logo-posgrado.0df946c7.png"
            alt="Logo posgrado">
        <h1>Sistema de Gestión de Procesos SIGEPRO Consejo de Posgrado</h1>
        <h2>Alta de usuario</h2>
        <br>
    <p>Saludos usuario con número de indentificación: <strong>${user.username}</strong></p>
    <p>Para completar el proceso de registro en SIGEPRO y activar su cuenta, por favor haga click en el siguiente enlace:</p>
    <a href="${link}" target="_blank">Establecer contraseña</a>
    <p>Este link estará vigente durante <strong>${expirationHoursRounded} horas</strong></p>
    <p>Si lo desea, una vez que haya activado su cuenta, podrá iniciar el proceso de pago en el Sistema SIGEPRO Consejo de Posgado de la Facultad de Ingeniería y Ciencias Aplicadas de la UCE </p>
    <#if client?? && client.rootUrl??>
    <p>...desde <a href="${client.rootUrl}">${client.rootUrl}</a></p>
    </#if>
    <#if client?? && client.baseUrl??>
    <p>...desde <a href="${client.baseUrl}">${client.baseUrl}</a></p>
    </#if>
</#macro>