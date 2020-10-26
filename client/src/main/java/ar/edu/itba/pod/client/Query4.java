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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import predicate.Predicate1Q4;
import reducers.Reducer1Q4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query4 {

    private static final Logger logger = LoggerFactory.getLogger(Query1.class);

    public static void main(String [] args) throws ExecutionException, InterruptedException, IOException {
        System.out.println("Query 4");
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String name = System.getProperty("name");
        final int min= Integer.parseInt(System.getProperty("min"));
        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));
        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        System.out.println(city + " " + addresses+ " " + inPath + " " + outPath + " " + min);

        Files.deleteIfExists(Paths.get(outPath+"/timeStamps.csv"));
        Files.deleteIfExists(Paths.get(outPath+"/result.csv"));

        FileWriter timeStampWriter = new FileWriter(new File(outPath+"/timeStamps.txt"));
        FileWriter csvWriter = new FileWriter(new File(outPath+"/result.csv"));

        String s = QueryUtils.now() + " INFO [main] Query4 (Query4.java:xx) - Inicio de la lectura del archivo\n";
        timeStampWriter.append(s);

        final IMap<Pair<Integer,String>, String> map = client.getMap("g10Q1NeighToTreeName");
        map.clear();
        URL arboles = Query1.class.getClassLoader().getResource("arbolesBUE.csv");
        if(arboles == null){
            logger.error("Error loading tree file");
            System.exit(-1);
        }
        map.putAll(Loader.loadNeighToTreeName(arboles.getFile(),city));

        final IMap<Pair<Integer,String>, String> map3 = client.getMap("g10Q1NeighToTreeName");
        map3.clear();
        arboles = Query1.class.getClassLoader().getResource("arbolesBUE.csv");
        map3.putAll(Loader.loadNeighToTreeName(arboles.getFile(),"BUE"));


        String t = QueryUtils.now() + " INFO [main] Query4 (Query4.java:xx) - Fin de la lectura del archivo\n";
        timeStampWriter.append(t);

        String u = QueryUtils.now() + " INFO [main] Query4 (Query4.java:xx) - Inicio del trabajo map/reduce\n";
        timeStampWriter.append(u);

        JobTracker jobTracker = client.getJobTracker("g10q4");
        final KeyValueSource<Pair<Integer,String>, String> source = KeyValueSource.fromMap(map);
        Job<Pair<Integer,String>, String> job = jobTracker.newJob(source);
        ICompletableFuture<List<Pair<String,String>>> future = job
                .keyPredicate(new Predicate1Q4(name))
                .mapper( new Mapper1Q4())
                .combiner( new CombinerFactory1Q4() )
                .reducer( new Reducer1Q4())
                .submit( new Collator1Q4(min));
        // Attach a callback listener


        List<Pair<String,String>> result = future.get();

        String v = QueryUtils.now() + " INFO [main] Query4 (Query4.java:xx) - Fin del trabajo map/reduce\n";
        timeStampWriter.append(v);

        // Wait and retrieve the result
        csvWriter.append("Barrio A;Barrio B\n");
//        System.out.println("Barrio A;Barrio B");
        result.forEach((element) -> {
            try {
                csvWriter.append(element.getKey() + ";" + element.getValue()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();
        timeStampWriter.close();
        System.exit(0);
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