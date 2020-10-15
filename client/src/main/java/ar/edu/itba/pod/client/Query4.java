package ar.edu.itba.pod.client;

public class Query4 {
    public static void main(String [] args){
        System.out.println("Query 4");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String min = System.getProperty("min");
        final String name = System.getProperty("name");

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath + " " + min + " " + min + name + " ");
    }
}
