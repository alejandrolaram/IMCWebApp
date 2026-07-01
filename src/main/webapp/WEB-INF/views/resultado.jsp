<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultado del IMC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6f9; }
        .card { border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card p-4 text-center">
                    <h2 class="text-success mb-4">¡Cálculo Exitoso!</h2>
                    
                    <p class="fs-5">Hola <strong>${persona.nombre}</strong>, de acuerdo a tus datos:</p>
                    
                    <div class="my-4 p-3 bg-light rounded border">
                        <span class="text-muted d-block small text-uppercase">Tu Índice de Masa Corporal es</span>
                        <h1 class="display-4 text-primary fw-bold">${String.format("%.2f", persona.imc)}</h1>
                    </div>
                    
                    <h4 class="mb-4">
                        Estado: 
                        <span class="badge ${persona.resultado == 'Normal' ? 'bg-success' : 'bg-warning text-dark'}">
                            ${persona.resultado}
                        </span>
                    </h4>
                    
                    <div class="alert alert-info small" role="alert">
                        Los datos han sido registrados correctamente en la base de datos de XAMPP de forma nativa.
                    </div>
                    
                    <a href="${pageContext.request.contextPath}/imc/formulario" class="btn btn-outline-primary w-100">Calcular de nuevo</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>