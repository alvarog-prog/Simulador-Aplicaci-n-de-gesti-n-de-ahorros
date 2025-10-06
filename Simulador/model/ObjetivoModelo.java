package model;

public class ObjetivoModelo {
    private String descripcion;
    private double cantidadObjetivo;
    private double cantidadActual;
    private String fechaLimite;

    public ObjetivoModelo(String descripcion, double cantidadObjetivo, String fechaLimite) {
        this.descripcion = descripcion;
        this.cantidadObjetivo = cantidadObjetivo;
        this.cantidadActual = 0.0; // al iniciar
        this.fechaLimite = fechaLimite;
    }

    public double getPorcentajeCompletado() {
        return (cantidadActual / cantidadObjetivo) * 100.0;
    }

    // Getters
    public String getDescripcion() { return descripcion; }
    public double getCantidadObjetivo() { return cantidadObjetivo; }
    public double getCantidadActual() { return cantidadActual; }
    public String getFechaLimite() { return fechaLimite; }

    // Setters
    public void setCantidadActual(double cantidadActual) {
        this.cantidadActual = cantidadActual;
    }
}