package ar.edu.itba.pod.client.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import models.Pair;
import models.Tree;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class Loader {

    public static Map<String, Integer> loadNeighbourhoods( String file) {
        Map<String, Integer> map = new HashMap<>();

        try {
            CSVParser csvParser = new CSVParser(
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                    CSVFormat.newFormat(';').withFirstRecordAsHeader()

            );
            csvParser.forEach(csvRecord -> {
//                Neighbourhood b = new Neighbourhood(csvRecord.get(1),Long.parseLong(csvRecord.get(2)));
                map.putIfAbsent(csvRecord.get(0), Integer.valueOf(csvRecord.get(1)));
            });
        } catch (IOException ex) {
            System.out.println("Error Loading Neighbourhoods");
        }
        return map;
    }

    public static Map<Integer, Tree> loadTrees(String file) {
        Map<Integer, Tree> map = new HashMap<>();
        try {

            CSVParser csvParser = new CSVParser(
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                    CSVFormat.newFormat(';').withFirstRecordAsHeader()

            );
            csvParser.forEach(csvRecord ->
                map.putIfAbsent(
                        Integer.valueOf(csvRecord.get(0)),
                        new Tree(csvRecord.get(2), csvRecord.get(4), csvRecord.get(7), Double.parseDouble(csvRecord.get(11))))
            );

        } catch (IllegalArgumentException ex){
            System.out.println("Error in Arguments");
        } catch (IOException ex) {
            System.out.println("Errors Loading Trees");
            ex.printStackTrace();
        }
        return map;
    }

    public static Map<Pair<Integer,String>,String> loadNeighToTreeName(String file) {
        Map<Pair<Integer,String>, String> map = new HashMap<>();
        try {

            CSVParser csvParser = new CSVParser(
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                    CSVFormat.newFormat(';').withFirstRecordAsHeader()

            );
            csvParser.forEach(csvRecord ->
                    map.putIfAbsent(new Pair<>(Integer.valueOf(csvRecord.get(0)),csvRecord.get(7)),
                            csvRecord.get(2))
            );

        } catch (IllegalArgumentException ex){
            System.out.println("Error in Arguments");
        } catch (IOException ex) {
            System.out.println("Errors Loading Trees");
            ex.printStackTrace();
        }
        return map;
    }
}
