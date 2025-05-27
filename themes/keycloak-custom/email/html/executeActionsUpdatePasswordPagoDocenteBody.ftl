<#macro updatePasswordPagoDocenteBody>
    <#assign expirationMinutes = linkExpiration?number>
    <#assign expirationHours = expirationMinutes / 60>
    <#assign expirationHoursRounded = expirationHours?string("0.#")>
        <p style="text-align: center;">
        <img src="https://posgrado-fica-uce.netlify.app/img/logo-posgrado.0df946c7.png"
            alt="Logo posgrado"
            width="75"
            style="max-width: 100%; height: auto;">
        </p>
        <h2>Sistema de Gestión de Procesos del Consejo de Posgrado SIGEPROCP</h2>
        <h3 style="text-align: center;">Alta de usuario</h3>
        <br>
    <p>Saludos <strong>${user.firstName} ${user.lastName}</strong></p>
    <p>Se ha creado su cuenta con el usuario: <strong>${user.username} / ${user.email}</strong></p>
    <p>Para completar el proceso de registro en SIGEPROCP y activar su cuenta, por favor haga click en el siguiente enlace:</p>
    <a href="${link}" target="_blank">Establecer contraseña</a>
    <p>Este link estará vigente durante <strong>${expirationHoursRounded} horas.</strong></p>
    <p>Si lo desea, una vez que haya activado su cuenta, podrá ingresar al Sistema SIGEPROCP de la Facultad de Ingeniería y Ciencias Aplicadas de la UCE.</p>
</#macro>