package model;

import java.util.ArrayList;
import java.util.List;

public class BaseObjetivos {
    private static List<ObjetivoModelo> objetivos = new ArrayList<>();

    public static void agregarObjetivo(ObjetivoModelo objetivo) {
        objetivos.add(objetivo);
    }

    public static List<ObjetivoModelo> obtenerObjetivos() {
        return objetivos;
    }
}