package juegoPreguntas;

import java.io.IOException;
import java.util.*;



public class JugarPartida {
    private PreguntaLetras preguntasLetras;
    private PreguntaMates preguntaMates;
    private PreguntaIngles preguntaIngles;
    private Scanner entrada;

    public JugarPartida() throws IOException {
        preguntasLetras = new PreguntaLetras(null, null);
        preguntaMates = new PreguntaMates("¿Cuánto es 2 + 2?", "4");
        preguntaIngles = new PreguntaIngles();
        entrada = new Scanner(System.in);
    }

    public void iniciarPartida(Jugadores jugadores, Ranking ranking) throws IOException {
        boolean jugarOtro = true;

        while (jugarOtro) {
            int opcionJugar = elegirOpcionJugar();

            switch (opcionJugar) {
                case 1:
                    jugarPartida(jugadores, ranking);
                    break;
                case 2:
                    entrenar();
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }

            jugarOtro = preguntarJugarOtro();
        }

        System.out.println("¡Gracias por jugar!");
    }

    private int elegirOpcionJugar() {
        int opcionMenu;

        do {
            System.out.println("========================================");
            System.out.println("Elija una opción:");
            System.out.println("1. Jugar partida");
            System.out.println("2. Entrenar");
            System.out.print("Ingrese su opción: ");

            opcionMenu = obtenerEnteroEntrada();

            if (opcionMenu < 1 || opcionMenu > 2) {
                System.out.println("¡Opción inválida! Intente de nuevo.");
            }
        } while (opcionMenu < 1 || opcionMenu > 2);

        return opcionMenu;
    }

    private boolean preguntarJugarOtro() {
        char opcion;

        do {
            System.out.print("¿Desea jugar otra partida? (S/N): ");
            opcion = obtenerCharEntrada();

            if (opcion != 'S' && opcion != 'N') {
                System.out.println("¡Opción inválida! Intente de nuevo.");
            }
        } while (opcion != 'S' && opcion != 'N');

        return opcion == 'S';
    }

    private void jugarPartida(Jugadores jugadores, Ranking ranking) throws IOException {
        int cantidadJugadores = obtenerCantidadJugadores();

        List<String> listaPartida = obtenerListaPartida(cantidadJugadores, jugadores);

        int tipoPregunta = elegirTipoPregunta();
        int cantidadRondas = elegirCantidadRondas();

        preguntasLetras.generarPregunta();
        preguntaMates.generarPregunta();
        preguntaIngles.leerArchivo("ingles.txt");

        jugarRondas(listaPartida, tipoPregunta, cantidadRondas, ranking, entrada);

        boolean jugarOtro = preguntarJugarOtro();
        if (jugarOtro) {
            jugarPartida(jugadores, ranking);
        } else {
            System.out.println("¡Gracias por jugar!");
        }
    }

    private int obtenerCantidadJugadores() {
        int cantidadJugadores;

        do {
            System.out.println("\n=======================================");
            System.out.print("Ingrese la cantidad de jugadores (mínimo 2 - máximo 4): ");
            cantidadJugadores = obtenerEnteroEntrada();

            if (cantidadJugadores < 2 || cantidadJugadores > 4) {
                System.err.println("! La cantidad introducida es incorrecta ¡");
            }
        } while (cantidadJugadores < 2 || cantidadJugadores > 4);

        return cantidadJugadores;
    }

    private List<String> obtenerListaPartida(int cantidadJugadores, Jugadores jugadores) {
        List<String> listaPartida = new ArrayList<>();

        while (listaPartida.size() < cantidadJugadores) {
            int opcionTipoJugador = elegirTipoJugador();

            switch (opcionTipoJugador) {
                case 1:
                    String nombreJugadorRegistrado = obtenerNombreJugadorRegistrado(jugadores, listaPartida);
                    if (!nombreJugadorRegistrado.isEmpty()) {
                        listaPartida.add(nombreJugadorRegistrado);
                    }
                    break;
                case 2:
                    String nombreJugadorNuevo = obtenerNombreJugadorNuevo(jugadores);
                    if (!nombreJugadorNuevo.isEmpty()) {
                        listaPartida.add(nombreJugadorNuevo);
                    }
                    break;
                case 3:
                    String nombreCPU = obtenerNombreCPU(listaPartida);
                    if (!nombreCPU.isEmpty()) {
                        listaPartida.add(nombreCPU);
                        System.out.println("Se ha añadido un jugador CPU a la partida: " + nombreCPU);
                    }
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        }

        System.out.println("\n============================================");
        System.out.println("Jugadores en la partida: " + listaPartida);
        System.out.println("============================================\n");
        return listaPartida;
    }

    private String obtenerNombreJugadorNuevo(Jugadores jugadores) {
        String nombreJugador = jugadores.agregarJugador(entrada);
        return nombreJugador;
    }


    private static String obtenerNombreCPU(List<String> listaPartida) {
        String nombreCPU = "CPU" + (listaPartida.stream().filter(j -> j.startsWith("CPU")).count() + 1);
        return nombreCPU;
    }

    
    private String obtenerNombreJugadorRegistrado(Jugadores jugadores, List<String> listaPartida) {
        System.out.println("====================================================");
        System.out.println("Para buscar un jugador, ingrese el nombre del jugador:");
        String nombreBuscado = entrada.nextLine().toUpperCase().replaceAll("\\s+", "");

        if (jugadores.nombreExiste(nombreBuscado)) {
            System.out.println("=================================");
            System.out.println("El jugador " + nombreBuscado + " está en la lista.");

            if (listaPartida.contains(nombreBuscado)) {
                System.out.println("El jugador ya está en la partida. Intente con otro nombre.");
                return "";
            } else {
                return nombreBuscado;
            }
        } else {
            System.out.println("===========================================");
            System.out.println("El jugador " + nombreBuscado + " no ha sido encontrado en la lista.");
            return "";
        }
    }

    private int elegirTipoJugador() {
        int opcionMenu;

        do {
            System.out.println("============================================");
            System.out.println("Elija el tipo de jugador:");
            System.out.println("1. Jugador registrado");
            System.out.println("2. Jugador nuevo");
            System.out.println("3. Jugador CPU");
            System.out.print("Ingrese su opción: ");

            opcionMenu = obtenerEnteroEntrada();

            if (opcionMenu < 1 || opcionMenu > 3) {
                System.out.println("¡Opción inválida! Intente de nuevo.");
            }
        } while (opcionMenu < 1 || opcionMenu > 3);

        return opcionMenu;
    }

    private int elegirTipoPregunta() {
        int opcionMenu;

        do {
            System.out.println("==============================================");
            System.out.println("Elija el tipo de pregunta que desea:");
            System.out.println("1. Preguntas de Letras");
            System.out.println("2. Preguntas de Matemáticas");
            System.out.println("3. Preguntas en Inglés");
            System.out.print("Ingrese su opción: ");

            opcionMenu = obtenerEnteroEntrada();
            System.out.println("============================================\n");

            if (opcionMenu < 1 || opcionMenu > 3) {
                System.out.println("¡Opción inválida! Intente de nuevo.");
            }
        } while (opcionMenu < 1 || opcionMenu > 3);

        return opcionMenu;
    }

    private int elegirCantidadRondas() {
        int opcionMenu;

        do {
            System.out.println("============================================");
            System.out.println("Elija la cantidad de rondas que desea jugar:");
            System.out.println("1. Partida Rápida (3 rondas)");
            System.out.println("2. Partida Corta (5 rondas)");
            System.out.println("3. Partida Normal (10 rondas)");
            System.out.println("4. Partida Larga (20 rondas)");
            System.out.print("Ingrese su opción: ");

            opcionMenu = obtenerEnteroEntrada();
            System.out.println("============================================\n");

            if (opcionMenu < 1 || opcionMenu > 4) {
                System.out.println("¡Opción inválida! Intente de nuevo.");
            }
        } while (opcionMenu < 1 || opcionMenu > 4);

        switch (opcionMenu) {
            case 1:
                return 3;
            case 2:
                return 5;
            case 3:
                return 10;
            case 4:
                return 20;
            default:
                return 0;
        }
    }

    private void jugarRondas(List<String> jugadores, int tipoPregunta, int cantidadRondas, Ranking ranking, Scanner scanner) throws IOException {
        Collections.shuffle(jugadores);
        CPU cpu = new CPU(); // Crear una instancia de la CPU

        Map<String, Integer> puntajes = new HashMap<>();

        for (String jugador : jugadores) {
            puntajes.put(jugador, 0);
        }

        for (int ronda = 1; ronda <= cantidadRondas; ronda++) {
            System.out.println("============================");
            System.out.println("Ronda " + ronda);

            for (String jugador : jugadores) {
                System.out.println("============================");
                System.out.println("Turno de " + jugador);

                boolean respuestaCorrecta = false;

                if (esCPU(jugador)) {
                    // Generar respuestas de la CPU según el tipo de pregunta
                    String respuestaLetrasCPU = cpu.generarRespuestaLetrasCPU();
                    int respuestaMatesCPU = preguntaMates.enviarResultado();
                    char respuestaInglesCPU = cpu.generarRespuestaInglesCPU();

                    // Realizar la lógica correspondiente a cada tipo de pregunta utilizando las respuestas de la CPU
                    switch (tipoPregunta) {
                        case 1: // Preguntas de Letras
                            preguntasLetras.regenerarPregunta();
                            respuestaCorrecta = preguntasLetras.jugarCPU(respuestaLetrasCPU);
                            break;
                        case 2: // Preguntas de Matemáticas
                            preguntaMates.generarPregunta();
                            respuestaCorrecta = preguntaMates.jugarCPU(respuestaMatesCPU);
                            break;
                        case 3: // Preguntas en Inglés
                            preguntaIngles.generarPregunta();
                            respuestaCorrecta = preguntaIngles.jugarCPU(respuestaInglesCPU);
                            break;
                        default:
                            System.out.println("¡Opción inválida! Intente de nuevo.");
                            break;
                    }
                } else {
                    switch (tipoPregunta) {
                        case 1: // Preguntas de Letras
                            preguntasLetras.regenerarPregunta();
                            respuestaCorrecta = preguntasLetras.jugarJugador(scanner);
                            break;
                        case 2: // Preguntas de Matemáticas
                            preguntaMates.generarPregunta();
                            respuestaCorrecta = preguntaMates.jugarJugador(scanner);
                            break;
                        case 3: // Preguntas en Inglés
                            preguntaIngles.generarPregunta();
                            respuestaCorrecta = preguntaIngles.jugarJugador(scanner);
                            break;
                        default:
                            System.out.println("¡Opción inválida! Intente de nuevo.");
                            break;
                    }
                }

                if (respuestaCorrecta) {
                    int puntajeActual = puntajes.get(jugador);
                    puntajes.put(jugador, puntajeActual + 1);
                  
                } else {
                   
                }
            }
        }

        mostrarPuntuacionesFinales(puntajes);

        List<String> ganadores = obtenerGanadores(puntajes);
        if (ganadores.size() == 1) {
            System.out.println("El ganador de la partida es: " + ganadores.get(0));
        } else {
            System.out.println("La partida ha terminado en empate entre los siguientes jugadores:");
            for (String ganador : ganadores) {
                System.out.println(ganador + ": " + puntajes.get(ganador) + " puntos");
            }
        }

        actualizarRanking(puntajes, ranking);

        System.out.println("=========================");
        System.out.println("La partida ha finalizado.");
    }

    private void mostrarPuntuacionesFinales(Map<String, Integer> puntajes) {
        System.out.println("-----------Puntuaciones finales de la partida-----------------");
        for (Map.Entry<String, Integer> entry : puntajes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " puntos");
        }
    }

    private List<String> obtenerGanadores(Map<String, Integer> puntajes) {
        int maxPuntaje = Collections.max(puntajes.values());
        List<String> ganadores = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : puntajes.entrySet()) {
            if (entry.getValue() == maxPuntaje) {
                ganadores.add(entry.getKey());
            }
        }

        return ganadores;
    }
    
    private void actualizarRanking(Map<String, Integer> puntajes, Ranking ranking) {
        if (ranking != null) {
            for (Map.Entry<String, Integer> entry : puntajes.entrySet()) {
                String jugador = entry.getKey();
                int puntaje = entry.getValue();
                ranking.actualizarPuntaje(jugador, puntaje);
            }
        }
    }

    private boolean esCPU(String jugador) {
        return jugador.startsWith("CPU");
    }


    private int obtenerEnteroEntrada() {
        while (!entrada.hasNextInt()) {
            entrada.nextLine();
            System.out.println("¡Entrada inválida! Intente de nuevo.");
        }
        int entero = entrada.nextInt();
        entrada.nextLine(); // Consumir el carácter de nueva línea después del entero
        return entero;
    }

    private char obtenerCharEntrada() {
        String entradaUsuario = entrada.nextLine().trim().toUpperCase();
        while (entradaUsuario.length() != 1) {
            System.out.println("¡Entrada inválida! Intente de nuevo.");
            entradaUsuario = entrada.nextLine().trim();
        }
        return entradaUsuario.charAt(0);
    }

    private void entrenar() throws IOException {
        String jugadorInvitado = "Invitado";
        String jugadorCPU = obtenerNombreCPU(Arrays.asList(jugadorInvitado));

        List<String> jugadores = Arrays.asList(jugadorInvitado, jugadorCPU);
        int tipoPregunta = elegirTipoPregunta();
        int cantidadRondas = elegirCantidadRondas();

        preguntasLetras.generarPregunta();
        preguntaMates.generarPregunta();
        preguntaIngles.leerArchivo("ingles.txt");

        jugarRondas(jugadores, tipoPregunta, cantidadRondas, null, entrada);
    }


}


