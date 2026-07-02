<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Usuario - IMC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6f9; }
        .card { border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card p-4">
                    <h3 class="text-center text-primary mb-3">Crear Cuenta Nueva</h3>
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger p-2 small text-center">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/imc/registro" method="POST">
                        <div class="mb-3">
                            <label class="form-label">Nombre Completo</label>
                            <input type="text" class="form-control" name="nombreCompleto" placeholder="Ej. Alejandro" required>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Edad (Mínimo 15)</label>
                                <input type="number" class="form-control" name="edad" min="15" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Sexo</label>
                                <select class="form-select" name="sexo" required>
                                    <option value="Masculino">Masculino</option>
                                    <option value="Femenino">Femenino</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Estatura en metros (1.0m - 2.5m)</label>
                            <input type="number" step="0.01" class="form-control" name="estatura" min="1.0" max="2.5" placeholder="Ej. 1.75" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Nombre de Usuario</label>
                            <input type="text" class="form-control" name="nombreUsuario" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Contraseña</label>
                            <input type="password" class="form-control" name="contrasenia" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100 py-2">Registrar Perfil</button>
                    </form>
                    <div class="text-center mt-3">
                        <a href="${pageContext.request.contextPath}/imc/login" class="small text-decoration-none">Regresar al Login</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>