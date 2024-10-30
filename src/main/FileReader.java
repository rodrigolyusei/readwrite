package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    private static final String path = "./data/bd.txt";
    public static ArrayList<String> data = new ArrayList<>();

    public static void readFile() throws FileNotFoundException {
        File obj = new File(FileReader.path);
        Scanner myReader = new Scanner(obj);
        while (myReader.hasNextLine()) {
            String nextLine = myReader.nextLine();
            data.add(nextLine);
        }
        myReader.close();
    }
}
