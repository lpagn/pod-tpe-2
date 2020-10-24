package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.QueryUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Query1 {

    public static void main(String [] args){
        System.out.println("Query 1");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath);

        String s = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Inicio de la lectura del archivo\n";
        System.out.println(s);

        String t = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Fin de la lectura del archivo\n";
        System.out.println(t);

        String u = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Inicio del trabajo map/reduce\n";
        System.out.println(u);

        String v = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Fin del trabajo map/reduce\n";
        System.out.println(v);
    }
}



