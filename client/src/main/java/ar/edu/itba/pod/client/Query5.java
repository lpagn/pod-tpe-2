package ar.edu.itba.pod.client;

public class Query5 {
    public static void main(String [] args){
        System.out.println("Query 5");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath);
    }
}
