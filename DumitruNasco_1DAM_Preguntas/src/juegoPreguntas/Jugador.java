package juegoPreguntas;

import java.io.Serializable;

public class Jugador implements Serializable {
    private static final long serialVersionUID = 3L;

    private String nombre;
    private int puntos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void incrementarPuntos(int cantidad) {
        puntos += cantidad;
    }

    public void responderPregunta(String respuesta, boolean esCorrecta) {
        if (esCorrecta) {
            System.out.println("Respuesta correcta. ¡Sumas puntos!");
            incrementarPuntos(1);
        } else {
            System.out.println("Respuesta incorrecta. No sumas puntos.");
        }
    }
}




