package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.QueryUtils;

public class Query2 {
    public static void main(String [] args){
        System.out.println("Query 2");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String min = System.getProperty("min");

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath + " " + min);

        String s = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio de la lectura del archivo\n";
        System.out.println(s);

        String t = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin de la lectura del archivo\n";
        System.out.println(t);

        String u = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio del trabajo map/reduce\n";
        System.out.println(u);

        String v = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin del trabajo map/reduce\n";
        System.out.println(v);

    }
}
