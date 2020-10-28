package ar.edu.itba.pod.client.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    public static Map<String, Tree> loadTrees(String file, String city) {
        Map<String, Tree> map = new HashMap<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(
                                csvRecord.get(0),
                                new Tree(csvRecord.get(2), csvRecord.get(4), csvRecord.get(7), Double.parseDouble(csvRecord.get(11))))
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
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
                                csvRecord.get(0),
                                new Tree(csvRecord.get(12), csvRecord.get(11), csvRecord.get(6), Double.parseDouble(csvRecord.get(15))))
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }
        else{
            throw new RuntimeException("Invalid city");
        }

        return map;
    }

    public static Map<String,Map.Entry<String,String>> loadNeighToTreeName(String file, String city) {
        Map<String, Map.Entry<String,String>> map = new HashMap<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(csvRecord.get(0),
                                new AbstractMap.SimpleEntry<>(csvRecord.get(2),csvRecord.get(7)))
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
                logger.error("Errors Loading Trees");
                logger.error(ex.getMessage());
            }
        }

        else{
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(csvRecord.get(0),
                                new AbstractMap.SimpleEntry<>(csvRecord.get(12),csvRecord.get(6)))
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
                logger.error("Errors Loading Trees");
                logger.error(ex.getMessage());
            }
        }

        return map;
    }

    public static Map<String,Map.Entry<String,String>> loadNeighAndTree(String file, String city) {
        Map<String,Map.Entry<String,String>> map = new HashMap<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        map.putIfAbsent(csvRecord.get(0),new AbstractMap.SimpleEntry<>(csvRecord.get(2),csvRecord.get(4))
                                )
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
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
                        map.putIfAbsent(csvRecord.get(0),
                                new AbstractMap.SimpleEntry<>(csvRecord.get(11),csvRecord.get(12)))
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }

        return map;
    }

    public static List<Map.Entry<String,String>> loadNeighAndTreeList(String file, String city) {
        List<Map.Entry<String,String>> list = new ArrayList<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()

                );
                csvParser.forEach(csvRecord ->
                        list.add(new AbstractMap.SimpleEntry<>(csvRecord.get(2),csvRecord.get(4)))
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
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
                        list.add(new AbstractMap.SimpleEntry<>(csvRecord.get(11),csvRecord.get(12)))
                );

            } catch (IllegalArgumentException ignored){}

            catch (IOException ex) {
                logger.error("Errors Loading Trees");
            }
        }

        return list;
    }

    public static Set<String> loadNeighbourhoodsSet(String file, String city) {
        Set<String> set = new HashSet<>();

        if(city.compareTo("BUE") == 0){
            try {
                CSVParser csvParser = new CSVParser(
                        new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)),
                        CSVFormat.newFormat(';').withFirstRecordAsHeader()
                );
                csvParser.forEach(csvRecord -> {
                    set.add(csvRecord.get(0));
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
                    set.add(csvRecord.get(0));
                });
            } catch (IOException ex) {
                logger.error("Error Loading Neighbourhoods");
            }
        }
        else{
            throw new RuntimeException("Invalid city");
        }

        return set;
    }
}
