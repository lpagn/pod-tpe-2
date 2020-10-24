//package ar.edu.itba.pod.client.utils;
//
//import ar.edu.itba.pod.services.Neighbourhood;
//import ar.edu.itba.pod.services.Tree;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Map;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//
//public class Loader {
//
//    public static void loadNeighbourhoods(Map<String, Neighbourhood> map, String file) {
//        try {
//            CSVParser csvParser = new CSVParser(
//                    Files.newBufferedReader(Paths.get(file)),
//                    CSVFormat.newFormat(';').withFirstRecordAsHeader()
//            );
//            csvParser.forEach(csvRecord -> {
//                Neighbourhood b = new Neighbourhood(csvRecord.get(1),Long.parseLong(csvRecord.get(2)));
//                map.putIfAbsent(b.name, b);
//            });
//        } catch (IOException ex) {
//            System.out.println("Error Loading Neighbourhoods");
//        }
//    }
//
//    public static void loadTrees(Map<String, Tree> map, String file) {
//        try {
//
//            CSVParser csvParser = new CSVParser(
//                    Files.newBufferedReader(Paths.get(file)),
//                    CSVFormat.newFormat(';').withFirstRecordAsHeader()
//            );
//            csvParser.forEach(csvRecord -> map.put(
//                    String.valueOf(csvRecord.getRecordNumber()),
//                    new Tree(/*field1,field2,...,fieldN*/
//                    )));
//        } catch (IllegalArgumentException ex){
//            System.out.println("Error in Arguments");
//        } catch (IOException ex) {
//            System.out.println("Errors Loading Trees");
//        }
//    }
//}
