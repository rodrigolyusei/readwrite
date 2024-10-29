package main;

import readwriter.Info;
import readwriter.Philosophers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    private static int concluded = 0;
    private static final HashMap<Integer, Long> valores = new HashMap<>();

    public static void main(String[] args) {
        FileReader.readFile();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        printProgressBar(0, 100);
        for (int i = 1; i <= 100; i++) {
            int readerCount = i;

            CompletableFuture<Void> task = CompletableFuture.runAsync(
                    () -> rodarFilosofos(readerCount), executor);

            tasks.add(task);
        }

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        executor.shutdown();
        try {
            boolean _ = executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Processo interrompido: " + e.getMessage());
        }
        System.out.println();
        writeToFile(valores, "resultados.txt");
    }

    public static void rodarFilosofos(int readerCount){
        long total = 0;

        for (int j = 0; j < 50; j++) {
            Info info = new Info(FileReader.data);
            Philosophers phi = new Philosophers();

            // inicializa o array de filósofos;
            phi.init(readerCount, info);

            // Inicia Threads
            long initialTime = System.currentTimeMillis();
            phi.runAll();

            // Espera terminar
            phi.waitAll();
            long endTime = System.currentTimeMillis();
            long tempo = endTime - initialTime;
            total += tempo;
        }

        long media = total / 50;
        synchronized (valores) {
            valores.put(readerCount, media);
        }
        concluded++;
        printProgressBar(concluded, 100);
    }

    public static void printProgressBar(int currentStep, int totalSteps) {
        int progressBarLength = 50; // Largura da barra de progresso
        int progress = (currentStep * progressBarLength) / totalSteps;

        StringBuilder bar = new StringBuilder();
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
                writer.write("Philosopher count: " + entry.getKey() + ", Average time: " + entry.getValue() + " ms");
                writer.newLine();
            }
            System.out.println("Dados escritos com sucesso em " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}
