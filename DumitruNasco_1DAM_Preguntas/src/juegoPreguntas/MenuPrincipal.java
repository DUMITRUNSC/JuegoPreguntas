package juegoPreguntas;

import java.io.IOException;
import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) throws IOException {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;

        Ranking ranking = new Ranking("ranking.txt"); // Crea un objeto Ranking y le pasa el nombre del archivo de ranking
        Jugadores jugadores = new Jugadores(ranking); // Crea un objeto Jugadores y le pasa el objeto Ranking

        jugadores.cargarJugadores(); // Carga los jugadores desde un archivo

        while (!salir) {
            mostrarMenuPrincipal(); // Muestra el menú principal

            String opcionMenuPrincipal = entrada.nextLine(); // Lee la opción ingresada por el usuario

            switch (opcionMenuPrincipal) {
                case "1":
                    JugarPartida juego = new JugarPartida(); // Crea un objeto JugarPartida
                    juego.iniciarPartida(jugadores, ranking); // Inicia una partida y le pasa los objetos Jugadores y Ranking
                    break;
                case "2":
                    ranking.mostrarRanking(); // Muestra el ranking de jugadores
                    break;
                case "3":
                	 Historico.mostrarHistorico(); // Muestra el historial de partidas
                    break;
                case "4":
                    jugadores.menuJugadores(entrada); // Muestra el menú de gestión de jugadores y le pasa el objeto Scanner para leer la entrada del usuario
                    break;
                case "5":
                    jugadores.guardarJugadores(); // Guarda los jugadores en un archivo
                    System.out.println("Saliendo del programa...");
                    salir = true; // Establece salir como true para finalizar el bucle
                    break;
                default:
                    System.out.println("=======================================");
                    System.err.println("! La opción introducida es incorrecta ¡");
                    break;
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("------------ Menu principal ---------------");
        System.out.println("1. Jugar Partida");
        System.out.println("2. Ranking");
        System.out.println("3. Historico");
        System.out.println("4. Gestion Jugadores");
        System.out.println("5. Salir");
        System.out.print("Ingrese una opción: ");
    }
}

