package ar.edu.itba.pod.client.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import models.Neighbourhood;
import models.Tree;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class Loader {

    public static void loadNeighbourhoods(Map<String, Neighbourhood> map, String file) {
        try {
            CSVParser csvParser = new CSVParser(
                    Files.newBufferedReader(Paths.get(file)),
                    CSVFormat.newFormat(';').withFirstRecordAsHeader()
            );
            csvParser.forEach(csvRecord -> {
                Neighbourhood b = new Neighbourhood(csvRecord.get(1),Integer.parseInt(csvRecord.get(2)));
                //map.putIfAbsent(b.name, b);
            });
        } catch (IOException ex) {
            System.out.println("Error Loading Neighbourhoods");
        }
    }

    public static void loadTrees(Map<String, Tree> map, String file) {
        try {

            CSVParser csvParser = new CSVParser(
                    Files.newBufferedReader(Paths.get(file)),
                    CSVFormat.newFormat(';').withFirstRecordAsHeader()
            );
            csvParser.forEach(csvRecord -> map.put(
                    String.valueOf(csvRecord.getRecordNumber()),
                    new Tree(csvRecord.get(1),csvRecord.get(2),csvRecord.get(3),Double.parseDouble(csvRecord.get(4))
                    )));
                    //TODO ver cada campo que indice es
                    //
        } catch (IllegalArgumentException ex){
            System.out.println("Error in Arguments");
        } catch (IOException ex) {
            System.out.println("Errors Loading Trees");
        }
    }
}
