package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;
import mappers.Mapper1Q4;
import models.Tree;
import reducers.Reducer1Q4;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Query4 {
    public static void main(String [] args){
        System.out.println("Query 4");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String n = System.getProperty("n");

        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));
        int min=20;
        String specie ="Abutilon grandiflorum";
        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<String, Integer> map = client.getMap("g10Q1Neighbourhood");
        map.clear();
        URL barrios = Query1.class.getClassLoader().getResource("barriosBUE.csv");
        map.putAll(Loader.loadNeighbourhoods(barrios.getFile()));

        final IMap<Integer, Tree> map2 = client.getMap("g10Q1Trees");
        map2.clear();
        URL arboles = Query1.class.getClassLoader().getResource("arbolesBUE.csv");
        map2.putAll(Loader.loadTrees(arboles.getFile()));



        JobTracker jobTracker = client.getJobTracker("g10q4");
        final KeyValueSource<Integer, Tree> source = KeyValueSource.fromMap(map2);
        Job<Integer, Tree> job = jobTracker.newJob(source);
        ICompletableFuture<Map<String, Integer>> future = job
                .mapper( new Mapper1Q4(specie))
                .reducer( new Reducer1Q4())
                .submit();
        // Attach a callback listener
        Map<String, Integer> result=new HashMap<>();
        try {
            while(!future.isDone());
            System.out.println("termine de esperar");
            result = future.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        // Wait and retrieve the result
        result.forEach((key,value) -> System.out.println("Key = [" + key + "], Value = [" + value + "]"));
    }
}