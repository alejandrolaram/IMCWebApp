<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculadora de IMC</title>
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
                <div class="card p-4">
                    <h2 class="text-center text-primary mb-4">Cálculo de IMC</h2>
                    
                    <form action="${pageContext.request.contextPath}/imc/calcular" method="POST">
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre Completo</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej. Alejandro" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="peso" class="form-label">Peso (kg)</label>
                            <input type="number" step="0.1" class="form-control" id="peso" name="peso" placeholder="Ej. 75.5" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="estatura" class="form-label">Estatura (m)</label>
                            <input type="number" step="0.01" class="form-control" id="estatura" name="estatura" placeholder="Ej. 1.75" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary w-100 py-2">Calcular e Insertar Datos</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>