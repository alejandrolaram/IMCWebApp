<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <nav class="navbar navbar-dark bg-primary mb-4">
        <div class="container">
            <span class="navbar-brand">Bienvenido, <strong>${usuario.nombreCompleto}</strong></span>
            <a href="${pageContext.request.contextPath}/imc/logout" class="btn btn-outline-light btn-sm">Cerrar Sesión</a>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-lg-5 mb-4">
                <div class="card p-4">
                    <h4 class="text-center text-primary mb-3">Nueva Medición</h4>
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger p-2 small text-center">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/imc/calcular" method="POST">
                        <div class="mb-3">
                            <label class="form-label text-muted small">Tus datos fijos de perfil:</label>
                            <div class="p-2 bg-light rounded border text-secondary small">
                                <strong>Estatura:</strong> ${usuario.estatura} m | <strong>Edad:</strong> ${usuario.edad} años
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="peso" class="form-label">Ingrese su Peso Actual (kg)</label>
                            <input type="number" step="0.1" class="form-control form-control-lg text-center" id="peso" name="peso" placeholder="Ej. 75.5" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 py-2 fw-bold">Calcular e Insertar Datos</button>
                    </form>
                </div>
            </div>

            <div class="col-lg-7">
                <div class="card p-4">
                    <h4 class="text-primary mb-3">Historial de Progreso</h4>
                    <div class="table-responsive">
                        <table class="table table-striped align-middle text-center small">
                            <thead class="table-dark">
                                <tr>
                                    <th>Fecha</th>
                                    <th>Peso (kg)</th>
                                    <th>IMC</th>
                                    <th>Resultado OMS</th>
                                </tr>
                            </thead>
                            <tbody id="tabla-historial">
                                <tr>
                                    <td colspan="4" class="text-muted text-center py-3">Cargando historial...</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const ctxPath = "${pageContext.request.contextPath}";
            fetch(ctxPath + "/imc/api/historial")
                .then(response => response.json())
                .then(data => {
                    const tbody = document.getElementById("tabla-historial");
                    tbody.innerHTML = ""; // Limpiar mensaje de carga
                    
                    if(data.length === 0) {
                        tbody.innerHTML = `<tr><td colspan="4" class="text-muted">No hay mediciones registradas aún.</td></tr>`;
                        return;
                    }

                    data.forEach(medicion => {
                        // Formatear la fecha
                        const fecha = new Date(medicion.fechaRegistro).toLocaleString('es-MX', {
                            year: 'numeric', month: '2-digit', day: '2-digit',
                            hour: '2-digit', minute:'2-digit'
                        });
                        
                        // Badge dinámico según resultado
                        let badgeClass = "bg-success";
                        if(medicion.resultado === "Bajo peso") badgeClass = "bg-info text-dark";
                        if(medicion.resultado === "Sobrepeso") badgeClass = "bg-warning text-dark";
                        if(medicion.resultado === "Obesidad") badgeClass = "bg-danger";

                        tbody.innerHTML += `
                            <tr>
                                <td>\${fecha}</td>
                                <td>\${medicion.peso.toFixed(1)} kg</td>
                                <td class="fw-bold text-primary">\${medicion.imc.toFixed(2)}</td>
                                <td><span class="badge \${badgeClass}">\${medicion.resultado}</span></td>
                            </tr>
                        `;
                    });
                })
                .catch(err => {
                    console.error("Error al consumir la API REST:", err);
                    document.getElementById("tabla-historial").innerHTML = 
                        `<tr><td colspan="4" class="text-danger">Error al cargar el historial.</td></tr>`;
                });
        });
    </script>
</body>
</html>