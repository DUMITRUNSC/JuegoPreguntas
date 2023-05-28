package juegoPreguntas;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class PreguntaMates extends Pregunta {
    private String pregunta;
    private int resultado;

    public PreguntaMates(String enunciado, String respuestaCorrecta) {
        super(enunciado, respuestaCorrecta);
        generarPregunta();
    }

    @Override
    public boolean verificarRespuesta(String respuesta) {
        try {
            int respuestaInt = Integer.parseInt(respuesta);
            resultado = calcularResultado(pregunta);
            if (respuestaInt == resultado) {
            	System.out.println("Correcto");
                return true;
            } else {
                System.out.println("La respuesta correcta es: " + resultado);
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String generarPregunta() {
        Random random = new Random();
        int tamaño = random.nextInt(5) + 4;

        StringBuilder sb = new StringBuilder();
        int numero1 = random.nextInt(11) + 2;
        sb.append(numero1);

        for (int i = 1; i < tamaño; i++) {
            int numero2 = random.nextInt(11) + 2;
            char operacion = obtenerOperacionAleatoria(random);
            sb.append(" ").append(operacion).append(" ").append(numero2);
        }

        pregunta = sb.toString();
        return pregunta;
    }

    private char obtenerOperacionAleatoria(Random random) {
        int operador = random.nextInt(3);
        return (operador == 0) ? '+' : (operador == 1) ? '-' : '*';
    }

    protected int calcularResultado(String pregunta) {
        String[] elementos = pregunta.split(" ");
        int resultadoCalculado = Integer.parseInt(elementos[0]);

        for (int i = 1; i < elementos.length; i += 2) {
            char operacion = elementos[i].charAt(0);
            int numero = Integer.parseInt(elementos[i + 1]);

            switch (operacion) {
                case '+':
                    resultadoCalculado += numero;
                    break;
                case '-':
                    resultadoCalculado -= numero;
                    break;
                case '*':
                    resultadoCalculado *= numero;
                    break;
            }
        }

        return resultadoCalculado;
    }

    public int obtenerRespuestaCorrecta() {
        return resultado;
    }

    public String getPregunta() {
        return pregunta;
    }

    private void imprimirEncabezado() {
        System.out.println("********************* Operacion *********************");
        System.out.println(pregunta);
        System.out.println("====================================================");
    }

    public boolean jugarJugador(Scanner scanner) {
        imprimirEncabezado();
        System.out.print("Ingrese su respuesta: ");

        try {
            int respuestaJugador = scanner.nextInt();
            return verificarRespuesta(Integer.toString(respuestaJugador));
        } catch (InputMismatchException e) {
            System.out.println("Respuesta incorrecta. Debe ingresar un número.");
            return false;
        }
    }

    public boolean jugarCPU(int respuestaCPU) {
        imprimirEncabezado();
        System.out.println("Respuesta de la CPU: " + respuestaCPU);

        String respuestaStr = String.valueOf(respuestaCPU); // Convierte el entero a una cadena de texto
        enviarResultado(); // Envia el resultado calculado a otra clase

        return verificarRespuesta(respuestaStr);
    }
    
    public int enviarResultado() {
        int resultado = calcularResultado(pregunta); // Calcula el resultado

        CPU cpu = new CPU(); // Crea una instancia de la clase CPU
        cpu.generarRespuestaMatesCPU(resultado); // Llama al método generarRespuestaMatesCPU() de la instancia de CPU

        return resultado;
    }

    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PreguntaMates pregunta = new PreguntaMates("¿Cuál es el resultado de la siguiente operación?", "8");
        boolean respuestaCorrecta = pregunta.jugar(scanner, "");
        
        if (respuestaCorrecta) {
            System.out.println("¡Respuesta correcta!");
        } else {
            System.out.println("Respuesta incorrecta.");
        }
    }*/
}


