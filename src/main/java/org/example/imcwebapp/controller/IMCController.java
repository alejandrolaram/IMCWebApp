package org.example.imcwebapp.controller;

import org.example.imcwebapp.model.Persona;
import org.example.imcwebapp.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/imc")
public class IMCController {

    @Autowired
    private PersonaRepository personaRepository;

    // ==========================================
    // 1. FLUJO WEB TRADICIONAL (Páginas JSP)
    // ==========================================

    // Muestra el formulario web
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("persona", new Persona());
        return "formulario"; // Buscará /WEB-INF/views/formulario.jsp
    }

    // Procesa el envío del formulario web
    @PostMapping("/calcular")
    public String calcularWeb(@ModelAttribute("persona") Persona persona, Model model) {
        // Al setear el peso/estatura, el modelo calcula el IMC automáticamente
        personaRepository.guardar(persona);
        model.addAttribute("persona", persona);
        return "resultado"; // Buscará /WEB-INF/views/resultado.jsp
    }

    // ==========================================
    // 2. FLUJO API REST (Intercambio JSON)
    // ==========================================

    // Endpoint RESTful que recibe y retorna JSON
    @PostMapping("/api/calcular")
    @ResponseBody
    public Persona calcularRest(@RequestBody Persona persona) {
        // Spring convierte el JSON entrante en el objeto Persona automáticamente
        persona.calcularIMC(); // Aseguramos el cálculo
        personaRepository.guardar(persona);
        return persona; // Spring convierte el objeto de retorno a JSON automáticamente
    }
}