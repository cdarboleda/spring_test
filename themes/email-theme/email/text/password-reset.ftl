<#import "/email/html/template.ftl" as layout>
<@layout.emailLayout>
Estimado ${user.firstName},

Para restablecer tu contraseña, haz clic en el siguiente enlace:

${link}

Si no solicitaste un restablecimiento de contraseña, ignora este mensaje.

Saludos,  
El equipo de soporte  
</@layout.emailLayout>
