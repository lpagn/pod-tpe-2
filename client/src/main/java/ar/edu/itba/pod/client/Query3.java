package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import collators.collatorq3;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import mappers.mapperq3;
import models.Pair;
import models.Tree;
import reducers.reducerq3;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query3 {
    public static void main(String [] args) throws ExecutionException, InterruptedException{
        System.out.println("Query 3");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String n = System.getProperty("n");

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath + " " + n);

        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<Integer, Tree> map3 = client.getMap("g10Q3Trees");
        map3.clear();
        URL arboles = Query3.class.getClassLoader().getResource("arbolesBUE.csv");
        map3.putAll(Loader.loadTrees(arboles.getFile(),"BUE"));


        Job<Integer, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map3));
        JobCompletableFuture<List<Pair<String, Double>>> future = job
                .mapper( new mapperq3() )
                .reducer( new reducerq3() )
                .submit(new collatorq3(10));//TODO: parametrize

        while(!future.isDone());

        List<Pair<String, Double>> result = future.get();
        //System.out.println(result);

        result.forEach((x)->System.out.println(x.getKey() + " " + x.getValue()));

        //result.forEach((key,value) -> System.out.println("Key = [" + key + "], Value = [" + value + "]"));

//        String s = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Inicio de la lectura del archivo\n";
//        System.out.println(s);
//
//        String t = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Fin de la lectura del archivo\n";
//        System.out.println(t);
//
//        String u = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Inicio del trabajo map/reduce\n";
//        System.out.println(u);
//
//        String v = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Fin del trabajo map/reduce\n";
//        System.out.println(v);
    }
}
