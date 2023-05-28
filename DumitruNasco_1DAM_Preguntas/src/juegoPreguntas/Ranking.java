package juegoPreguntas;

import java.io.*;
import java.util.*;

public class Ranking {
    private Map<String, Integer> jugadoresPuntajes;
    private String rankingFilePath; // Ruta del archivo para el ranking

    public Ranking(String rankingFilePath) {
        jugadoresPuntajes = new HashMap<>();
        this.rankingFilePath = rankingFilePath;
        cargarRankingDesdeArchivo();
    }

    public void actualizarPuntaje(String nombreJugador, int puntos) {
        jugadoresPuntajes.put(nombreJugador, jugadoresPuntajes.getOrDefault(nombreJugador, 0) + puntos);
    }

    public void eliminarJugadorDelRanking(String nombreJugador) {
        jugadoresPuntajes.remove(nombreJugador);
    }
    
    public void mostrarRanking() {
        System.out.println("========== Ranking ==========");
        List<Map.Entry<String, Integer>> listaPuntajes = new ArrayList<>(jugadoresPuntajes.entrySet());

        // Ordenar la lista de puntajes de mayor a menor
        Collections.sort(listaPuntajes, Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : listaPuntajes) {
            String nombreJugador = entry.getKey();
            int puntos = entry.getValue();

            if (puntos > 0 && !nombreJugador.startsWith("CPU")) { // Verificar si el jugador tiene más de 0 puntos y no empieza por "CPU"
                System.out.println(nombreJugador + ": " + puntos + " puntos");
            }
        }

        System.out.println("=============================");
    }



    public int obtenerPuntosJugador(String nombreJugador) {
        return jugadoresPuntajes.getOrDefault(nombreJugador, 0);
    }

    public String obtenerGanador() {
        List<Map.Entry<String, Integer>> listaPuntajes = new ArrayList<>(jugadoresPuntajes.entrySet());
        listaPuntajes.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return listaPuntajes.get(0).getKey();
    }

    public void guardarRankingEnArchivo() {
        guardarRankingEnArchivo(this.rankingFilePath);
    }

    public void guardarRankingEnArchivo(String filePath) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (Map.Entry<String, Integer> entry : jugadoresPuntajes.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
           // System.out.println("Ranking guardado en el archivo '" + filePath + "'.");
        } catch (IOException e) {
            //System.out.println("Error al guardar el ranking en el archivo.");
            e.printStackTrace();
        }
    }

    private void cargarRankingDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(rankingFilePath))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    String nombreJugador = datos[0];
                    int puntos = Integer.parseInt(datos[1]);
                    jugadoresPuntajes.put(nombreJugador, puntos);
                }
            }
            //System.out.println("Ranking cargado desde el archivo '" + rankingFilePath + "'.");
        } catch (IOException e) {
           System.out.println("No se pudo cargar el ranking desde el archivo. Se creará uno nuevo.");
        }
    }
}


