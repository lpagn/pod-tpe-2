package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.collatorq3;
import collators.collatorq5;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import mappers.mapperq3;
import mappers.mapperq5;
import models.Pair;
import models.Q5ans;
import models.Tree;
import reducers.reducerq3;
import reducers.reducerq5;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Query5 {
    public static void main(String [] args) throws ExecutionException, InterruptedException{
        System.out.println("Query 5");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");

        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<Integer, Tree> map5 = client.getMap("g10Q5Trees");
        map5.clear();
        URL arboles = Query3.class.getClassLoader().getResource("arbolesBUE.csv");
        map5.putAll(Loader.loadTrees(arboles.getFile()));


        Job<Integer, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map5));
        JobCompletableFuture<List<Q5ans>> future = job
                .mapper( new mapperq5() )
                .reducer( new reducerq5())
                .submit(new collatorq5());

        while(!future.isDone());

        List<Q5ans> result = future.get();

        result.forEach(System.out::println);

//        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath);
//        String s = QueryUtils.now() + " INFO [main] Query5 (Query5.java:xx) - Inicio de la lectura del archivo\n";
//        System.out.println(s);
//
//        String t = QueryUtils.now() + " INFO [main] Query5 (Query5.java:xx) - Fin de la lectura del archivo\n";
//        System.out.println(t);
//
//        String u = QueryUtils.now() + " INFO [main] Query5 (Query5.java:xx) - Inicio del trabajo map/reduce\n";
//        System.out.println(u);
//
//        String v = QueryUtils.now() + " INFO [main] Query5 (Query5.java:xx) - Fin del trabajo map/reduce\n";
//        System.out.println(v);
    }
}
