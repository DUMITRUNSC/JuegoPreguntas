package juegoPreguntas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Historico {
    private static final String NOMBRE_ARCHIVO = "historico.txt";

    public static void guardarPartida(Map<String, Integer> puntajes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : puntajes.entrySet()) {
                String nombreJugador = entry.getKey();
                int puntaje = entry.getValue();
                sb.append(nombreJugador).append(" ").append(puntaje).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()); // Eliminar la última coma y espacio
            writer.write(sb.toString());
            writer.newLine();
        }
    }

    public static void mostrarHistorico() throws IOException {
        System.out.println("-------------- Historico Resultados Partidas-----------------");
        try (BufferedReader reader = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = reader.readLine()) != null) {
                System.out.println(numeroLinea + ". " + linea);
                numeroLinea++;
            }
        }
        System.out.println("--------------------------------------------------------------");
    }
}
