package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.Collator1Q4;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;
import combiners.CombinerFactory1Q4;
import mappers.Mapper1Q4;
import models.Pair;
import models.Tree;
import predicate.Predicate1Q4;
import reducers.Reducer1Q4;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query4 {
    public static void main(String [] args) throws ExecutionException, InterruptedException {
        System.out.println("Query 4");
        String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String n = System.getProperty("n");

        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));
        int min=11000;
        city="BUE";
        String specie ="Fraxinus pennsylvanica";

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        String s = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio de la lectura del archivo\n";
        System.out.println(s);

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath + " " + min);

        final IMap<Pair<Integer,String>, String> map3 = client.getMap("g10Q1NeighToTreeName");
        map3.clear();
        URL arboles = Query1.class.getClassLoader().getResource("arbolesBUE.csv");
        map3.putAll(Loader.loadNeighToTreeName(arboles.getFile(),city));

        String t = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin de la lectura del archivo\n";
        System.out.println(t);

        String u = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio del trabajo map/reduce\n";
        System.out.println(u);

        JobTracker jobTracker = client.getJobTracker("g10q4");
        final KeyValueSource<Pair<Integer,String>, String> source = KeyValueSource.fromMap(map3);
        Job<Pair<Integer,String>, String> job = jobTracker.newJob(source);
        ICompletableFuture<List<Pair<String,String>>> future = job
                .keyPredicate(new Predicate1Q4(specie))
                .mapper( new Mapper1Q4())
                .combiner( new CombinerFactory1Q4() )
                .reducer( new Reducer1Q4())
                .submit( new Collator1Q4(min));
        // Attach a callback listener

        String v = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin del trabajo map/reduce\n";
        System.out.println(v);

        List<Pair<String,String>> result = future.get();

        // Wait and retrieve the result
        System.out.println("Barrio A;Barrio B");
        result.forEach((element) -> System.out.println(element.getKey() + ";" + element.getValue()));

//        JobTracker jobTracker = client.getJobTracker("g10q4");
//        final KeyValueSource<Integer, Tree> source = KeyValueSource.fromMap(map2);
//        Job<Integer, Tree> job = jobTracker.newJob(source);
//        ICompletableFuture<List<Pair<String,String>>> future = job
//                .mapper( new Mapper1Q4(specie))
//                .combiner( new CombinerFactory1Q4() )
//                .reducer( new Reducer1Q4())
//                .submit( new Collator1Q4());
//        // Attach a callback listener
//        List<Pair<String,String>> result=new LinkedList<>();
//        try {
//            while(!future.isDone());
//            System.out.println("termine de esperar");
//            result = future.get();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        // Wait and retrieve the result
//        result.forEach((element) -> System.out.println("Key = [" + element.getKey() + "], Value = [" + element.getValue() + "]"));
    }
}