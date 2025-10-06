package model;

import java.io.Serializable;

public class Objetivo implements Serializable {
    private String descripcion;
    private double cantidadObjetivo;
    private double cantidadActual;
    private String comentario;

    public Objetivo(String descripcion, double cantidadObjetivo, String comentario) {
        this.descripcion = descripcion;
        this.cantidadObjetivo = cantidadObjetivo;
        this.comentario = comentario;
        this.cantidadActual = 0.0;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCantidadObjetivo() {
        return cantidadObjetivo;
    }

    public double getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(double cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public double getPorcentajeCompletado() {
        if (cantidadObjetivo <= 0) return 0;
        return (cantidadActual / cantidadObjetivo) * 100.0;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}