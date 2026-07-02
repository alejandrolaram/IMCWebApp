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
                    
                    <p class="fs-5">De acuerdo al peso de <strong>${medicion.peso} kg</strong> introducido:</p>
                    
                    <div class="my-4 p-3 bg-light rounded border">
                        <span class="text-muted d-block small text-uppercase">Tu Índice de Masa Corporal es</span>
                        <h1 class="display-4 text-primary fw-bold">${String.format("%.2f", medicion.imc)}</h1>
                    </div>
                    
                    <h4 class="mb-4">
                        Estado OMS: 
                        <span class="badge ${medicion.resultado == 'Normal' ? 'bg-success' : 
                                             (medicion.resultado == 'Sobrepeso' ? 'bg-warning text-dark' : 'bg-danger')}">
                            ${medicion.resultado}
                        </span>
                    </h4>
                    
                    <div class="alert alert-info small" role="alert">
                        La medición ha sido enlazada a tu cuenta y registrada en la tabla histórica.
                    </div>
                    
                    <a href="${pageContext.request.contextPath}/imc/formulario" class="btn btn-primary w-100 py-2">Volver al Tablero / Ver Historial completo</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>