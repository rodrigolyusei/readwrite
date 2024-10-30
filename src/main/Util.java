package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {
    public static boolean titlePrinted = false;

    public static void printProgressBar(String processTitle, int currentStep, int totalSteps) {
        int progressBarLength = 50; // Largura da barra de progresso
        int progress = (currentStep * progressBarLength) / totalSteps;

        if (!titlePrinted) {
            System.out.println("Processando " + processTitle + "!");
            titlePrinted = true; // Marca que o título foi impresso
        }

        StringBuilder bar = new StringBuilder();
        bar.append("Processando ").append(processTitle).append("!");
        bar.append("\r["); // Retorna ao início da linha e abre a barra
        for (int i = 0; i < progressBarLength; i++) {
            if (i < progress) {
                bar.append("■"); // Parte preenchida da barra
            } else {
                bar.append("□"); // Parte vazia da barra
            }
        }
        bar.append("] ").append(currentStep * 100 / totalSteps).append("%"); // Exibe a porcentagem

        System.out.print(bar); // Atualiza a barra de progresso no terminal

        try {
            Thread.sleep(50); // Ajuste o tempo conforme necessário
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void writeToFile(HashMap<Integer, Long> valores, String filePath) {
        Map<Integer, Long> sortedMap = valores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, _) -> e1,
                        HashMap::new
                ));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Integer, Long> entry : sortedMap.entrySet()) {
                writer.write("Reader count: " + entry.getKey() + ", Average time: " + entry.getValue() + " ms");
                writer.newLine();
            }
            System.out.println("Dados escritos com sucesso em " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}
