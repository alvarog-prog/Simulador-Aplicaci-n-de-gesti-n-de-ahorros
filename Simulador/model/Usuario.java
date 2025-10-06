package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private String idUsuario;
    private String password;
    private List<Gasto> gastos;
    private List<Objetivo> objetivos;
    private List<Objetivo> objetivosCompletados;

    public Usuario(String idUsuario, String password) {
        this.idUsuario = idUsuario;
        this.password = password;
        this.gastos = new ArrayList<>();
        this.objetivos = new ArrayList<>();
        this.objetivosCompletados = new ArrayList<>();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos = objetivos;
    }

    public List<Objetivo> getObjetivosCompletados() {
        return objetivosCompletados;
    }

    public void setObjetivosCompletados(List<Objetivo> objetivosCompletados) {
        this.objetivosCompletados = objetivosCompletados;
    }

    public void agregarGasto(Gasto gasto) {
        this.gastos.add(gasto);
    }

    public void agregarObjetivo(Objetivo objetivo) {
        this.objetivos.add(objetivo);
    }

    public void moverACompletados(Objetivo objetivo) {
        this.objetivos.remove(objetivo);
        this.objetivosCompletados.add(objetivo);
    }
}