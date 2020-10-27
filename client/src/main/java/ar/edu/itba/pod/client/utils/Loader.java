package ar.edu.itba.pod.client.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import models.Pair;
import models.Tree;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loader {
    private static final Logger logger = LoggerFactory.getLogger(Loader.class);

    public static Map<String, Integer> loadNeighbourhoods(String file, String city) {
        Map<String, Integer> map = new HashMap<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()
                );
                csvParser.forEach(csvRecord -> {
                    map.putIfAbsent(csvRecord.get(0), Integer.valueOf(csvRecord.get(1)));
                });
            } catch (IOException ex) {
                logger.error("Error Loading Neighbourhoods");
            }
        }

        else if(city.compareTo("VAN") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()
                );
                csvParser.forEach(csvRecord -> {
                    map.putIfAbsent(csvRecord.get(0), Integer.valueOf(csvRecord.get(1)));
                });
            } catch (IOException ex) {
                logger.error("Error Loading Neighbourhoods");
            }
        }
        else{
            throw new RuntimeException("Invalid city");
        }

        return map;
    }

    public static Map<Integer, Tree> loadTrees(String file, String city) {
        Map<Integer, Tree> map = new HashMap<>();

        if(city.compareTo("BUE") == 0){
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
                logger.error("Error in Arguments");
            } catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }

        else if(city.compareTo("VAN") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(
                                Integer.valueOf(csvRecord.get(0)),
                                new Tree(csvRecord.get(12), csvRecord.get(11), csvRecord.get(6), Double.parseDouble(csvRecord.get(15))))
                );

            } catch (IllegalArgumentException ex){
                logger.error("Error in Arguments");
            } catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }
        else{
            throw new RuntimeException("Invalid city");
        }

        return map;
    }

    public static Map<Map.Entry<Integer,String>,String> loadNeighToTreeName(String file, String city) {
        Map<Map.Entry<Integer,String>, String> map = new HashMap<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(new AbstractMap.SimpleEntry<>(Integer.valueOf(csvRecord.get(0)),csvRecord.get(7)),
                                csvRecord.get(2))
                );

            } catch (IllegalArgumentException ex){
                logger.error("Error in Arguments");
            } catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }

        else{
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(new AbstractMap.SimpleEntry<>(Integer.valueOf(csvRecord.get(0)),csvRecord.get(6)),
                                csvRecord.get(12))
                );

            } catch (IllegalArgumentException ex){
                logger.error("Error in Arguments");
            } catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }

        return map;
    }

    public static Map<Map.Entry<Integer,String>,Tree> loadNeighAndTree(String file, String city) {
        Map<Map.Entry<Integer,String>, Tree> map = new HashMap<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(new AbstractMap.SimpleEntry<>(Integer.valueOf(csvRecord.get(0)),csvRecord.get(2)),
                                new Tree(csvRecord.get(2), csvRecord.get(4), csvRecord.get(7), Double.parseDouble(csvRecord.get(11))))
                );

            } catch (IllegalArgumentException ex){
                logger.error("Error in Arguments");
            } catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }

        else{
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(new AbstractMap.SimpleEntry<>(Integer.valueOf(csvRecord.get(0)),csvRecord.get(12)),
                                new Tree(csvRecord.get(12), csvRecord.get(11), csvRecord.get(6), Double.parseDouble(csvRecord.get(15))))
                );

            } catch (IllegalArgumentException ex){
                logger.error("Error in Arguments");
            } catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }

        return map;
    }
}
