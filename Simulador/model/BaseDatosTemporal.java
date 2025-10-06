package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDatosTemporal {
    private static List<Gasto> gastos = new ArrayList<>();
    private static List<String> objetivos = new ArrayList<>();
    private static Map<String, Double> valorObjetivos = new HashMap<>();

    public static void agregarGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    public static List<Gasto> obtenerGastos() {
        return gastos;
    }

    public static List<Gasto> getGastos() {
        return gastos;
    }

    // Objetivos
    public static void agregarObjetivo(String nombre, double valor) {
        objetivos.add(nombre);
        valorObjetivos.put(nombre, valor);
    }

    public static List<String> getObjetivos() {
        return objetivos;
    }

    public static double getValorObjetivo(String nombre) {
        return valorObjetivos.getOrDefault(nombre, 0.0);
    }
}