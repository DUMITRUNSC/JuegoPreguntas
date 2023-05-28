package juegoPreguntas;

import java.io.*;
import java.util.*;

public class Jugadores implements Serializable {
    private static final long serialVersionUID = 2L;

    private Set<Jugador> personas;
    private Ranking ranking;

    public Jugadores(Ranking ranking) {
        personas = new HashSet<>();
        this.ranking = ranking;
    }

    public Set<Jugador> getPersonas() {
        return personas;
    }


    
    public void verJugadores() {
        System.out.println("---------Jugadores Registrados--------");
        for (Jugador persona : personas) {
            System.out.println(persona.getNombre());
        }
       
    }

    public void aniadirJugador(Jugador persona) {
        personas.add(persona);
    }

    public void eliminarJugador(String nombre) {
        personas.removeIf(persona -> persona.getNombre().equalsIgnoreCase(nombre));
        ranking.eliminarJugadorDelRanking(nombre); // Eliminar el jugador del ranking
        guardarJugadores();
        ranking.guardarRankingEnArchivo(); // Guardar el ranking actualizado
    }

    public boolean nombreExiste(String nombre) {
        for (Jugador persona : personas) {
            if (persona.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void guardarJugadores() {
        try (PrintWriter writer = new PrintWriter("jugadores.txt")) {
            for (Jugador jugador : personas) {
                writer.println(jugador.getNombre());
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarJugadores() {
        try (BufferedReader reader = new BufferedReader(new FileReader("jugadores.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("CPU")) {
                   
                } else {
                    Jugador jugador = new Jugador(linea.trim());
                    personas.add(jugador);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo jugadores.txt. Se creará uno nuevo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void menuJugadores(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            mostrarMenuJugadores();

            String opcionMenuJugadores = scanner.nextLine();

            switch (opcionMenuJugadores) {
                case "1":
                    verJugadores();
                    break;
                case "2":
                    agregarJugador(scanner);
                    break;
                case "3":
                    eliminarJugador(scanner);
                    break;
                case "4":
                    eliminarTodosLosJugadores(scanner);
                    break;
                case "5":
                    salir = true;
                    break;
                default:
                    System.out.println("=======================================");
                    System.err.println("! La opción introducida es incorrecta ¡");
                    break;
            }
        }
    }

    private void mostrarMenuJugadores() {
        System.out.println("------------ Menu Jugadores --------------");
        System.out.println("1. Ver Jugadores");
        System.out.println("2. Añadir Jugador");
        System.out.println("3. Eliminar Jugador");
        System.out.println("4. Eliminar todos los jugadores");
        System.out.println("5. Volver");
        System.out.print("Ingrese una opción: ");
    }

    public String agregarJugador(Scanner scanner) {
        System.out.println("=============================================================");
        System.out.println("Para añadir un nuevo jugador, ingrese el nombre de la persona:");
        String nombrePersona = scanner.nextLine();

        nombrePersona = nombrePersona.trim();

        if (nombrePersona.isEmpty()) {
            System.out.println("===================================");
            System.err.println("! El nombre no puede estar vacío ¡");
            return null;
        }

        if (nombreExiste(nombrePersona)) {
            System.out.println("===================================");
            System.err.println("! El nombre introducido ya existe ¡");
            return null;
        }

        nombrePersona = nombrePersona.toUpperCase(); // Convertir a mayúsculas

        Jugador nuevoJugador = new Jugador(nombrePersona);
        aniadirJugador(nuevoJugador);
        guardarJugadores();
        System.out.println("=============================");
        System.out.println("Jugador añadido correctamente.");
        return nombrePersona;
    }

    private void eliminarJugador(Scanner scanner) {
        System.out.println("=============================================================");
        System.out.println("Para eliminar un jugador, ingrese el nombre de la persona:");
        String nombrePersona = scanner.nextLine();

        nombrePersona = nombrePersona.trim();

        if (nombrePersona.isEmpty()) {
            System.out.println("===================================");
            System.err.println("! El nombre no puede estar vacío ¡");
            return;
        }

        if (!nombreExiste(nombrePersona)) {
            System.out.println("==========================================");
            System.err.println("! Este jugador no existe en el historial ¡");
            return;
        }

        ranking.eliminarJugadorDelRanking(nombrePersona); // Eliminar al jugador del ranking
        eliminarJugador(nombrePersona); // Eliminar al jugador de la lista de personas
        guardarJugadores();
        ranking.guardarRankingEnArchivo(); // Guardar el ranking actualizado
        System.out.println("====================================");
        System.out.println("Jugador eliminado correctamente.");
    }

    private void eliminarTodosLosJugadores(Scanner scanner) {
        System.out.println("=============================================================");
        System.out.println("¿Está seguro de que desea eliminar todos los jugadores? (s/n)");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            personas.clear();
            guardarJugadores();
            System.out.println("========================================");
            System.out.println("Todos los jugadores han sido eliminados.");
        } else if (respuesta.equalsIgnoreCase("n")) {
            return;
        } else {
            System.out.println("==================================================");
            System.err.println("Respuesta no válida. Por favor, ingrese 's' o 'n'.");
        }
    }
}



