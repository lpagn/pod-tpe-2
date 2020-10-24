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
import com.hazelcast.mapreduce.KeyValueSource;
import mappers.mapperq2;
import models.Tree;
import reducers.reducerq2;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query2 {
    public static void main(String [] args) throws ExecutionException, InterruptedException {
        System.out.println("Query 2");
        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<Integer, Tree> map2 = client.getMap("g10Q2Trees");
        map2.clear();
        URL arboles = Query1.class.getClassLoader().getResource("arbolesBUE.csv");
        map2.putAll(Loader.loadTrees(arboles.getFile()));


        Job<Integer, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map2));
        ICompletableFuture<Map<String, Integer>> future = job
                .mapper( new mapperq2() )
                .reducer( new reducerq2() )
                .submit();
// Attach a callback listener
//        future.andThen( buildCallback() );
// Wait and retrieve the result
        while(!future.isDone());

        Map<String, Integer> result = future.get();
        System.out.println(result);

//        final String city = System.getProperty("city");
//        final String addresses = System.getProperty("addresses");
//        final String inPath = System.getProperty("inPath");
//        final String outPath = System.getProperty("outPath");
//        final String min = System.getProperty("min");
//
//        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath + " " + min);
//
//        String s = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio de la lectura del archivo\n";
//        System.out.println(s);
//
//        String t = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin de la lectura del archivo\n";
//        System.out.println(t);
//
//        String u = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio del trabajo map/reduce\n";
//        System.out.println(u);
//
//        String v = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin del trabajo map/reduce\n";
//        System.out.println(v);

    }
}
