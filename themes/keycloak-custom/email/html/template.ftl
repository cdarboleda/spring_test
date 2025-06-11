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
            color: rgba(22, 24, 35, 0.75);
            }
        .container { 
            max-width: 600px;
            margin: 0 auto;
            background: white;
            border-radius: 10px;
            padding: 20px; 
            }
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
        },
        .numerosContainer{
            display:flex;
            flex-direction:row;
            text-align: left;
        }
        .numerosContainer p{
            margin: 0 0 0.25rem 0;
        }
    </style>
</head>
<body>
    <div class="container" style="max-width: 625px; margin: 0 auto; background: white; border-radius: 10px; padding: 20px;">
        <p style="text-align: center;">
        <img src="https://posgrado-fica-uce.netlify.app/img/logo-posgrado.0df946c7.png"
            alt="Logo posgrado"
            width="75"
            style="max-width: 100%; height: auto;">
        </p>
        <h2 style="text-align: center;">Sistema de Gestión de Procesos del Consejo de Posgrado SIGEPROCP</h2>
        <#nested>
                <p>Si necesita ayuda con su cuenta, por favor envíe un e-mail a <a style="display:inline;"
                href="mailto:fing.consejo.posgrado@uce.edu.ec" target="_blank">fing.consejo.posgrado@uce.edu.ec</a></p>
        <p>
            <br>
            Cordialmente<br>
            Equipo de soporte<br>
        </p>
            <hr>
        <div style="margin-top: 20px; font-size: 0.75rem; line-height: 1rem;">
            <strong> Dirección: </strong><span style="display:inline;">Gilberto Gato Sobral S/N y Gaspar de Carvajal.</span> <span>Facultad de Ingeniería y Ciencias Aplicadas.</span> <span>Edificio de Aulas, 4to. Piso.</span><br>
            <strong> Ubicación: </strong><a style="display:inline;" href="https://maps.app.goo.gl/n1QtFFtyyYdXRpEC9" target="_blank">Facultad de Ingeniería y Ciencias Aplicadas </a><br>
            <div style="display:flex; flex-direction:row; text-align: left;">
            <strong>Teléfonos: </strong>
            <div style="margin-left: 0.2rem;"><p style="margin: 0 0 0.25rem 0;">(593-02) 2551 270</p> <p style="margin: 0 0 0.25rem 0;">(593-02) 3216975</p>  <p style="margin: 0 0 0.25rem 0;"><a href="https://wa.me/+593983747088"  target="_blank">+593 98 374 7088</a></p></div>
            </div>
        </div>
    </div>
</body>
</html>
</#macro>
