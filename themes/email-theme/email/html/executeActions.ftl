<#outputformat "plainText">
<#assign requiredActionsText><#if requiredActions??><#list requiredActions><#items as reqActionItem>${msg("requiredAction.${reqActionItem}")}<#sep>, </#sep></#items></#list></#if></#assign>
</#outputformat>

<#import "template.ftl" as layout>
<@layout.emailLayout>
    <#--  ${kcSanitize(msg("executeActionsBodyHtml", link, ))?no_esc}  -->
    <#--  <h1>Thanks for signing up!</h1>
<p> Please verify email address ${user.username} <br>activate your account and to proceed registration process.</p>
Sistema/Plataforma XXX, Consejo de Posgrado - Alta de usuario  -->
 
<p>Hola <strong>${user.username}</strong></p>

<p>Para completar el proceso de registro en XX y activar su cuenta, por favor haga click en el siguiente enlace:</p>
 
  <a href="${link}" target="_blank">Actualizar</a>
 
<p>Este link estará vigente durante ${linkExpiration}</p>
 
<p>Si lo desea, una vez que haya activado su cuenta, podrá iniciar el proceso de pago en el Consejo de Posgado de la Facultad de Ingeniería y Ciencias Aplicadas de la UCE, desde http://localhost:8081</p>
 
<p>Si necesita ayuda con su cuenta, por favor envíe un e-mail a bvalmache@uce.edu.ec</p>
 
<p>Muchas gracias. 
<br>
Cordialmente,<br>
 
Mesa de ayuda/XX (http://sedici.unlp.edu.ar) <br>
Consejo de Posgrado<br>
Facultad de Ingeneiría y Ciencias Aplicadas<br>
Ubicación: 49 y 115 s/n, 1er piso, Departamento de Ciencias Básicas, Facultad de Ingeniería.<br>
Mapa: http://sedici.unlp.edu.ar/pages/comoLlegar<br>
Tel 0221- 4236677/6696 Interno 141<br>
</p>
<#--  ${kcSanitize(msg("executeActionsBodyHtml",link, linkExpiration, realmName, requiredActionsText, linkExpirationFormatter(linkExpiration)))?no_esc}  -->
</@layout.emailLayout>
