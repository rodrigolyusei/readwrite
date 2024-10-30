package main;

import readwriter.Philosophers;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PhilosopherMain {

    private static int concluded = 0;
    private static final HashMap<Integer, Long> valores = new HashMap<>();

    public static void main(String[] args) {
        try {
            FileReader.readFile();
        }
        catch(FileNotFoundException e) {
            System.err.println("Não existe o diretório de dados /data. Por favor, crie e coloque os arquivos necessários.");
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<Void> filosofos = CompletableFuture.runAsync(
                () -> filosofos(executor), executor);

        CompletableFuture.allOf(filosofos).join();

        executor.shutdown();
        try {
            boolean _ = executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Processo interrompido: " + e.getMessage());
        }
    }

    public static void filosofos(ExecutorService executor){
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        Util.printProgressBar("Filósofos", 0, 100);
        for (int i = 1; i <= 100; i++) {
            int readerCount = i;

            CompletableFuture<Void> task = CompletableFuture.runAsync(
                    () -> rodarFilosofo(readerCount), executor);

            tasks.add(task);
        }

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();


        System.out.println();
        Util.writeToFile(valores, "filosofos.txt");
    }

    public static void rodarFilosofo(int readerCount){
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
        Util.printProgressBar("Filósofos",concluded, 100);
    }

}
