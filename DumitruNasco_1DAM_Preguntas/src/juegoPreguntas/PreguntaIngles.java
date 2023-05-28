package juegoPreguntas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PreguntaIngles extends Pregunta {
    private List<String> preguntas;
    private List<String> respuestas;

    public PreguntaIngles() {
        super("", "");
        preguntas = new ArrayList<>();
        respuestas = new ArrayList<>();
    }

    public void leerArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                preguntas.add(linea);
                for (int i = 0; i < 4; i++) {
                    linea = br.readLine();
                    respuestas.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    @Override
    public boolean verificarRespuesta(String respuesta) {
        String respuestaCorrecta = getRespuestaCorrecta();
        return respuesta.equalsIgnoreCase(respuestaCorrecta);
    }

    @Override
    public String generarPregunta() {
        Random random = new Random();
        int indicePregunta = random.nextInt(preguntas.size());
        String pregunta = preguntas.get(indicePregunta);
        String respuestaCorrecta = respuestas.get(indicePregunta * 4);

        setEnunciado(pregunta);
        setRespuestaCorrecta(respuestaCorrecta);

        return pregunta;
    }

    
    public boolean jugarJugador(Scanner scanner) {
        String pregunta = generarPregunta();
        String respuestaCorrecta = getRespuestaCorrecta();
        System.out.println(respuestaCorrecta);

        System.out.println("-------------------------------");
        System.out.println("Adivina la respuesta en inglés:");
        System.out.println("-------------------------------");
        System.out.println(pregunta);
        System.out.println("---------------------------------------------------");

        List<String> opciones = obtenerOpcionesAleatorias(respuestaCorrecta);

        System.out.println("Opciones:");
        System.out.println("A) " + opciones.get(0));
        System.out.println("B) " + opciones.get(1));
        System.out.println("C) " + opciones.get(2));
        System.out.println("D) " + opciones.get(3));
        System.out.println("=======================================");

        while (true) {
            System.out.print("Elige la opción correcta (A, B, C, D): ");
            String respuestaJugador = scanner.nextLine().toUpperCase();

            if (respuestaJugador.matches("[A-D]")) {
                int indiceRespuestaElegida = respuestaJugador.charAt(0) - 'A';

                String respuestaElegida = opciones.get(indiceRespuestaElegida);
                if (respuestaElegida.equals(respuestaCorrecta)) {
                    System.out.println("============================================");
                    System.out.println("¡Respuesta correcta!");
                    System.out.println("La respuesta correcta es: " + respuestaCorrecta + "\n");
                    
                  
                    return true;
                } else {
                    System.out.println("============================================");
                    System.out.println("¡Respuesta incorrecta!");
                    System.out.println("La respuesta correcta es: " + respuestaCorrecta+  "\n");
                  
                    return false;
                }
            } else {
                System.out.println("Opción inválida. Debes elegir A, B, C o D.");
            }
        }
    }

    public boolean jugarCPU(char respuestaCPU) {
        String pregunta = generarPregunta();
        String respuestaCorrecta = getRespuestaCorrecta();
        System.out.println(respuestaCorrecta);

        System.out.println("-------------------------------");
        System.out.println("Adivina la respuesta en inglés:");
        System.out.println("----------------------------------------------------");
        System.out.println(pregunta);
        System.out.println("-------------------");

        List<String> opciones = obtenerOpcionesAleatorias(respuestaCorrecta);

        System.out.println("Opciones:");
        System.out.println("A) " + opciones.get(0));
        System.out.println("B) " + opciones.get(1));
        System.out.println("C) " + opciones.get(2));
        System.out.println("D) " + opciones.get(3));
        System.out.println("=======================================");
        System.out.println("Elige la opción correcta (A, B, C, D): " + respuestaCPU);

        String respuestaElegida = Character.toString(Character.toUpperCase(respuestaCPU));

        if (respuestaElegida.matches("[A-D]")) {
        	
            if (respuestaElegida.equalsIgnoreCase(respuestaCorrecta)) {
                   System.out.println("============================================");
                System.out.println("¡Respuesta correcta!");
                System.out.println("La respuesta correcta es: " + respuestaCorrecta+ "\n");
         
                
                return true;
            } else {
                System.out.println("============================================");
                System.out.println("¡Respuesta incorrecta!");
                System.out.println("La respuesta correcta es: " + respuestaCorrecta + "\n");
           
                return false;
            }
        } else {
            System.out.println("Opción inválida. Debes elegir A, B, C o D.");
            return false;
        }
    }




    private List<String> obtenerOpcionesAleatorias(String respuestaCorrecta) {
        List<String> opciones = new ArrayList<>();
        opciones.add(respuestaCorrecta);
        int indiceRespuestaCorrecta = respuestas.indexOf(respuestaCorrecta);
        List<String> respuestasAleatorias = new ArrayList<>(respuestas);
        respuestasAleatorias.remove(indiceRespuestaCorrecta);
        Collections.shuffle(respuestasAleatorias);
        opciones.addAll(respuestasAleatorias.subList(0, 3));
        Collections.shuffle(opciones);
        return opciones;
    }

    public void mezclarRespuestas() {
        Collections.shuffle(respuestas);
    }


    
   /* public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PreguntasIngles juego = new PreguntasIngles();
        juego.leerArchivo("ingles.txt");
        juego.mezclarRespuestas();
        
        boolean respuestaCorrecta = juego.jugar(scanner," ");
        
        if (respuestaCorrecta) {
            System.out.println("¡Respuesta correcta!");
        } else {
            System.out.println("Respuesta incorrecta.");
        }
    }*/
}



