package org.example.imcwebapp.model;

public class Persona {
    private Long id;
    private String nombre;
    private double peso;      // en kilogramos
    private double estatura;  // en metros
    private double imc;
    private String resultado; // Categoría (Bajo peso, Normal, etc.)

    // Constructor vacío
    public Persona() {
    }

    // Constructor con parámetros esenciales
    public Persona(String nombre, double peso, double estatura) {
        this.nombre = nombre;
        this.peso = peso;
        this.estatura = estatura;
        calcularIMC();
    }

    // Método encapsulado para calcular el IMC y su diagnóstico
    public final void calcularIMC() {
        if (this.estatura > 0) {
            this.imc = this.peso / (this.estatura * this.estatura);
            
            // Determinar categoría según la OMS
            if (this.imc < 18.5) {
                this.resultado = "Bajo peso";
            } else if (this.imc >= 18.5 && this.imc < 25.0) {
                this.resultado = "Normal";
            } else if (this.imc >= 25.0 && this.imc < 30.0) {
                this.resultado = "Sobrepeso";
            } else {
                this.resultado = "Obesidad";
            }
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { 
        this.peso = peso; 
        calcularIMC();
    }

    public double getEstatura() { return estatura; }
    public void setEstatura(double estatura) { 
        this.estatura = estatura; 
        calcularIMC();
    }

    public double getImc() { return imc; }
    public void setImc(double imc) { this.imc = imc; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}