package juegoPreguntas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class PreguntaLetras extends Pregunta {
    private static final String ARCHIVO_DICCIONARIO = "diccionario.txt";
    private static final int LETRAS_TAPADAS_FACTOR = 3;

    private List<String> palabras;
    private String palabraOriginal;
    private String palabraMostrada;
    private StringBuilder letrasAdivinadas;

    public PreguntaLetras(String enunciado, String respuestaCorrecta) throws IOException {
        super(enunciado, respuestaCorrecta);
        this.palabras = new ArrayList<>();
        cargarPalabras();
        this.palabraOriginal = seleccionarPalabraAleatoria();
        this.palabraMostrada = ocultarLetras(palabraOriginal);
        this.letrasAdivinadas = new StringBuilder();
    }

    private void cargarPalabras() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_DICCIONARIO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                palabras.add(linea);
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo: " + e.getMessage());
        }
    }

    private String seleccionarPalabraAleatoria() {
        Random rand = new Random();
        String palabra = palabras.get(rand.nextInt(palabras.size()));
        return quitarTildes(palabra);
    }

    private String quitarTildes(String palabra) {
        String palabraSinTildes = Normalizer.normalize(palabra, Normalizer.Form.NFD);
        palabraSinTildes = palabraSinTildes.replaceAll("\\p{M}", "");
        return palabraSinTildes;
    }

    private String ocultarLetras(String palabra) {
        StringBuilder sb = new StringBuilder(palabra);
        Random rand = new Random();
        int longitudPalabra = palabra.length();
        int letrasTapadas = longitudPalabra / LETRAS_TAPADAS_FACTOR;

        for (int i = 0; i < letrasTapadas; i++) {
            int indiceAleatorio = rand.nextInt(longitudPalabra);
            while (sb.charAt(indiceAleatorio) == '_') {
                indiceAleatorio = rand.nextInt(longitudPalabra);
            }
            sb.setCharAt(indiceAleatorio, '_');
        }

        return sb.toString();
    }

    @Override
    public String generarPregunta() {
 
        return palabraMostrada;
    }

    public boolean verificarRespuesta(String respuesta) {
        respuesta = respuesta.toLowerCase(Locale.ROOT).trim();
        return respuesta.equals(palabraOriginal.toLowerCase(Locale.ROOT));
    }
    
    public boolean jugarJugador(Scanner scanner) {
        System.out.println("-----------------------");
        System.out.println("Adivina qué palabra es:");
        System.out.println(generarPregunta());
        System.out.println("-------------------");
        System.out.print("Escribe la plabra:");
        
        String respuestaJugador =  scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        return jugar(respuestaJugador, respuestaJugador);
    }

    public boolean jugarCPU(String respuestaCPU) {
        System.out.println("-----------------------");
        System.out.println("Adivina qué palabra es:");
        System.out.println(generarPregunta());
        System.out.println("-------------------");
        System.out.println("Ecribe la palabra:" + respuestaCPU);

       return jugar(respuestaCPU, respuestaCPU);
    }

    private void mostrarMensajeAdivinado(String palabra) {
        System.out.println("=====================================================================");
        System.out.println("¡Correcto, has adivinado la palabra!");
    }

    private void mostrarMensajeIncorrecto(String palabra) {
        System.out.println("=====================================================");
        System.out.println("Palabra incorrecta. La palabra era " + palabra);
    }

    public boolean jugar(String respuestaJugador, String respuestaCPU) {
        letrasAdivinadas.append(respuestaJugador);

        if (verificarRespuesta(respuestaJugador) || verificarRespuesta(respuestaCPU) ) {
            mostrarMensajeAdivinado(palabraOriginal);
            return true;
        } else {
            mostrarMensajeIncorrecto(palabraOriginal);
            return false;
        }
    }


    public void regenerarPregunta() {
        this.palabraOriginal = seleccionarPalabraAleatoria();
        this.palabraMostrada = ocultarLetras(palabraOriginal);
        this.letrasAdivinadas = new StringBuilder();
    }
    
   /* public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PreguntaLetras pregunta = new PreguntaLetras("Adivina la palabra oculta", "apple");
        boolean respuestaCorrecta = pregunta.jugar(scanner);

        if (respuestaCorrecta) {
            System.out.println("¡Respuesta correcta!");
        } else {
            System.out.println("Respuesta incorrecta.");
        }
    }*/

}
