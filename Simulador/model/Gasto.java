package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Gasto implements Serializable {
    private String categoria;
    private double cantidad;
    private LocalDate fecha;
    private String descripcion;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Gasto(String categoria, double cantidad, String fechaTexto, String descripcion) {
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.fecha = LocalDate.parse(fechaTexto, FORMATO);
        this.descripcion = descripcion;
    }

    public String getCategoria() { return categoria; }
    public double getCantidad() { return cantidad; }
    public LocalDate getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }

    public String getFechaFormateada() {
        return fecha.format(FORMATO);
    }
}