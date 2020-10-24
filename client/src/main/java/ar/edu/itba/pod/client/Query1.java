package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import mappers.QueryOneMapper;
import mappers.mapperq2;
import models.Neighbourhood;
import models.Pair;
import models.Tree;
import reducers.QueryOneReducer;
import reducers.reducerq2;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 {

    public static void main(String [] args) throws ExecutionException, InterruptedException {
        System.out.println("Query 1");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");

        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<String, Integer> map = client.getMap("g10Q1Neighbourhood");
        map.clear();
        URL barrios = Query1.class.getClassLoader().getResource("barriosBUE.csv");
        map.putAll(Loader.loadNeighbourhoods(barrios.getFile()));


        final IMap<Integer,Tree> map2 = client.getMap("g10Q1Trees");
        map2.clear();
        URL arboles = Query1.class.getClassLoader().getResource("arbolesBUE.csv");
        map2.putAll(Loader.loadTrees(arboles.getFile()));

        Job<Integer, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map2));
        JobCompletableFuture<Map<Pair<String, Integer>, Pair<String, Double>>> future = job
                .mapper(new QueryOneMapper(map))
                .reducer(new QueryOneReducer())
                .submit();

        while(!future.isDone());

        Map<Pair<String, Integer>, Pair<String, Double>> result = future.get();
        System.out.println(result);

        /*System.out.println(map2.size());

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath);

        String s = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Inicio de la lectura del archivo\n";
        System.out.println(s);

        String t = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Fin de la lectura del archivo\n";
        System.out.println(t);

        String u = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Inicio del trabajo map/reduce\n";
        System.out.println(u);

        String v = QueryUtils.now() + " INFO [main] Query1 (Query1.java:xx) - Fin del trabajo map/reduce\n";
        System.out.println(v);*/
    }
}



