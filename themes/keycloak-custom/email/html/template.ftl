<#macro emailLayout>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Correo de Keycloak</title>
    <style>
        body { font-family: sans-serif;  padding: 20px;
            background-color: #f4f4f4;
            color: rgba(22, 24, 35, 0.75);}
        .container { max-width: 600px;
            margin: 0 auto;
            background: white;
            border-radius: 10px;
            padding: 20px; }
        .logo {
            display: block;
            margin: 0 auto 20px;
            height: 80px;
        }

        h2, h1 {
            text-align: center;
        }

        .saludo {
            margin-top: 20px;
            font-size: 0.75rem;
            line-height: 1rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <#nested>
    </div>
</body>
</html>
</#macro>
