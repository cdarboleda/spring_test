<#macro updatePasswordPagoDocenteBody>
    <#assign expirationMinutes = linkExpiration?number>
    <#assign expirationHours = expirationMinutes / 60>
    <#assign expirationHoursRounded = expirationHours?string("0.#")>

        <h3 style="text-align: center;">Alta de usuario</h3>
        <br>
    <p>Saludos <strong>${user.firstName} ${user.lastName}</strong></p>
    <p>Se ha creado su cuenta con el usuario: <strong>${user.username} / ${user.email}</strong></p>
<#if user.attributes?has_content && user.attributes["roles_info"]?has_content>
  <#assign rawRoles = user.attributes["roles_info"]>
  <#assign rolesList = rawRoles?split(",")>
  <p>Sus roles asignados son:</p>
  <ul>
    <#list rolesList as rol>
      <li>${rol?trim}</li>
    </#list>
  </ul>
</#if>
    <p>Para completar el proceso de registro en SIGEPROCP y activar su cuenta, por favor haga click en el siguiente enlace:</p>
    <a href="${link}" target="_blank">Establecer contraseña</a>
    <p>Este link estará vigente durante <strong>${expirationHoursRounded} horas.</strong></p>
    <p>Si lo desea, una vez que haya activado su cuenta, podrá ingresar al Sistema SIGEPROCP de la Facultad de Ingeniería y Ciencias Aplicadas de la UCE.</p>
</#macro>