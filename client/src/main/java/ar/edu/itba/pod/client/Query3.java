package ar.edu.itba.pod.client;

public class Query3 {
    public static void main(String [] args){
        System.out.println("Query 3");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String n = System.getProperty("n");

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath + " " + n);
    }
}
