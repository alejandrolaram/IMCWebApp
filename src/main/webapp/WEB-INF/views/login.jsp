<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - IMC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6f9; }
        .card { border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="card p-4 mt-5">
                    <h3 class="text-center text-primary mb-4">Control de IMC</h3>
                    <h5 class="text-center text-muted mb-4">Iniciar Sesión</h5>
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger p-2 small text-center">${error}</div>
                    </c:if>
                    <c:if test="${not empty exito}">
                        <div class="alert alert-success p-2 small text-center">${exito}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/imc/login" method="POST">
                        <div class="mb-3">
                            <label class="form-label">Nombre de Usuario</label>
                            <input type="text" class="form-control" name="nombreUsuario" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Contraseña</label>
                            <input type="password" class="form-control" name="contrasenia" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 py-2 mb-3">Ingresar</button>
                    </form>
                    
                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/imc/registro" class="small text-decoration-none">¿No tienes cuenta? Regístrate aquí</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>