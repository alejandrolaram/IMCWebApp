package org.example.imcwebapp.controller;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.example.imcwebapp.model.Persona;
import org.example.imcwebapp.model.Usuario;
import org.example.imcwebapp.repository.PersonaRepository;
import org.example.imcwebapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/imc")
public class IMCController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    // ==========================================
    // 1. AUTENTICACIÓN Y REGISTRO (WEB)
    // ==========================================

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Buscará /WEB-INF/views/login.jsp
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("nombreUsuario") String nombreUsuario,
                                @RequestParam("contrasenia") String contrasenia,
                                HttpSession session, Model model) {
        Usuario usuario = usuarioRepository.login(nombreUsuario, contrasenia);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/imc/formulario";
        }
        model.addAttribute("error", "Credenciales incorrectas. Intente de nuevo.");
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Buscará /WEB-INF/views/registro.jsp
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario, Model model) {
        // Validaciones estrictas de la rúbrica
        if (usuario.getEdad() < 15) {
            model.addAttribute("error", "No se permiten edades menores a 15 años.");
            return "registro";
        }
        if (usuario.getEstatura() < 1.0 || usuario.getEstatura() > 2.5) {
            model.addAttribute("error", "La estatura debe estar entre 1.0m y 2.5m.");
            return "registro";
        }
        if (usuarioRepository.existeUsuario(usuario.getNombreUsuario())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "registro";
        }

        if (usuarioRepository.registrar(usuario)) {
            model.addAttribute("exito", "Registro exitoso. Inicie sesión.");
            return "login";
        }
        model.addAttribute("error", "Error interno al registrar. Intente más tarde.");
        return "registro";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/imc/login";
    }

    // ==========================================
    // 2. FLUJO WEB TRADICIONAL (FORMULARIO Y HISTÓRICO)
    // ==========================================

    @GetMapping("/formulario")
    public String mostrarFormulario(HttpSession session, Model model) {
        // Validación de sesión activa obligatoria
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/imc/login";
        }
        
        model.addAttribute("usuario", usuarioLogueado);
        return "formulario";
    }

    @PostMapping("/calcular")
    public String calcularWeb(@RequestParam("peso") double peso, HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/imc/login";
        }

        // Validación estricta: masa corporal mayor a 0
        if (peso <= 0) {
            model.addAttribute("error", "El peso debe ser mayor a 0 kg.");
            model.addAttribute("usuario", usuarioLogueado);
            return "formulario";
        }

        Persona nuevaMedicion = new Persona();
        nuevaMedicion.setUsuarioId(usuarioLogueado.getId());
        nuevaMedicion.setPeso(peso);
        nuevaMedicion.setEstatura(usuarioLogueado.getEstatura());
        nuevaMedicion.calcularIMC();

        personaRepository.guardar(nuevaMedicion);
        
        // Pasamos la última medición calculada para mostrar la pantalla de éxito
        model.addAttribute("medicion", nuevaMedicion);
        return "resultado";
    }

    // ==========================================
    // 3. ENDPOINT API REST (REQUERIDO PARA CONSUMIR EL HISTÓRICO)
    // ==========================================

    // Endpoint REST solicitado para consumir el histórico mediante AJAX/Fetch
    @GetMapping("/api/historial")
    @ResponseBody
    public List<Persona> obtenerHistorialRest(HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return null; 
        }
        return personaRepository.obtenerHistorialPorUsuario(usuarioLogueado.getId());
    }
}