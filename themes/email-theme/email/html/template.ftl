<#macro emailLayout>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Correo de Keycloak</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        h1 { color: #333; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Notificación de Keycloak</h1>
        <#nested>
    </div>
</body>
</html>
</#macro>
