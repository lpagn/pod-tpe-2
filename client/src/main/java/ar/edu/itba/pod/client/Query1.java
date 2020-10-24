package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import models.Neighbourhood;
import models.Tree;

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

        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("grupo10")
                        .setPassword("grupo10"));

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

//        final IMap<String, Long> map = client.getMap("g10m1");
//        map.clear();
//
//        map.putAll(Loader.loadNeighbourhoods("C:\\Users\\fedeb\\Desktop\\barriosBUE.csv"));

        final IMap<String, Integer> map = client.getMap("g10m1");
        map.clear();
        map.putAll(Loader.loadNeighbourhoods("C:\\Users\\fedeb\\Desktop\\barriosBUE.csv"));

        System.out.println(Loader.loadTrees("C:\\Users\\fedeb\\Desktop\\arbolesBUE.csv"));

        final IMap<Integer,Tree> map2 = client.getMap("g10m2");
        map2.clear();
        map2.putAll(Loader.loadTrees("C:\\Users\\fedeb\\Desktop\\arbolesBUE.csv"));


        System.out.println(map2.size());


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



