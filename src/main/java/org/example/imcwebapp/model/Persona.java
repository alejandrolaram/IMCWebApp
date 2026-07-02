package org.example.imcwebapp.model;

import java.util.Date;

public class Persona {
    private int id;
    private int usuarioId; // Relación con el usuario logueado
    private double peso;
    private double estatura; // Obtenida del perfil del usuario para el cálculo
    private double imc;
    private String resultado;
    private Date fechaRegistro;

    public Persona() {}

    // Método de negocio para calcular el IMC y asignar la categoría de la OMS
    public void calcularIMC() {
        if (this.estatura > 0 && this.peso > 0) {
            this.imc = this.peso / (this.estatura * this.estatura);
            if (this.imc < 18.5) {
                this.resultado = "Bajo peso";
            } else if (this.imc >= 18.5 && this.imc < 25) {
                this.resultado = "Normal";
            } else if (this.imc >= 25 && this.imc < 30) {
                this.resultado = "Sobrepeso";
            } else {
                this.resultado = "Obesidad";
            }
        }
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getEstatura() { return estatura; }
    public void setEstatura(double estatura) { this.estatura = estatura; }

    public double getImc() { return imc; }
    public void setImc(double imc) { this.imc = imc; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}