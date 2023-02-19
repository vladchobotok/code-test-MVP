package org.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class GameReader {

    public List<List<String>> readAllGames(String pathname){

        //reading all csv files and converting them into list of files (lists of strings)
        File[] files = new File(pathname).listFiles(x -> x.isFile() && x.getName().endsWith(".csv"));
        List<List<String>> records = new ArrayList<>();

        if (files != null) {
            for (File file: files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(";");
                        records.add(Arrays.asList(values));
                    }
                    System.out.println("Successfully read the file " + file.getName());

                } catch (IOException e) {
                    System.err.println("An IOException caught in GameReader.readAllGames(): something wrong happened while reading the file " + file.getName());
                    System.exit(0);
                }

            }
        }
        System.out.println("Successfully read all the files \n");
        return records;
    }
}
