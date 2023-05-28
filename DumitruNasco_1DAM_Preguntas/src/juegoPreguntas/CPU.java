package juegoPreguntas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class CPU {
    
    private String respuestaLetrasCPU;

    public char generarRespuestaInglesCPU() {
        Random random = new Random();
        char respuestaInglesCPU = (char) ('a' + random.nextInt(4));
        respuestaInglesCPU = Character.toUpperCase(respuestaInglesCPU);
        return respuestaInglesCPU;
    }

    public String generarRespuestaLetrasCPU() throws IOException {
        File file = new File("diccionario.txt");
        List<String> lines = Files.readAllLines(file.toPath());

        Random random = new Random();
        String word = lines.get(random.nextInt(lines.size()));

        // Quitamos las tildes de la palabra
        respuestaLetrasCPU = Normalizer.normalize(word, Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return respuestaLetrasCPU;
    }

    public void generarRespuestaMatesCPU(int resultado) {
        // Aquí puedes realizar alguna operación con el resultado, como imprimirlo
        //System.out.println("Resultado de la pregunta Mates: " + resultado);
    }

       
   /* public static void main(String[] args) throws IOException {
          
    	  CPU cpu = new CPU();
    	  
    	  cpu.respuestaCpuLetras();
          System.out.println(cpu.getRespuestaLetrasCPU());
          
      
          
          cpu.respuestaCpuIngles();
          System.out.println(cpu.getRespuestaInglesCPU());
        }*/

    }

