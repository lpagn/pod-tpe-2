package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.CollatorQ1;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.CombinerFactoryQ1;
import mappers.MapperQ1;
import models.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reducers.ReducerQ1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 {
    private static final Logger logger = LoggerFactory.getLogger(Query1.class);

    public static void main(String [] args) throws ExecutionException, InterruptedException, IOException {

        // Getting params
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");

        // Parsing addresses
        String[] address = addresses.split(";");

        // Hazelcast config
        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        for(String addr : address){
            ccfg.getNetworkConfig().addAddress(addr);
        }

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        // File creation
        Files.deleteIfExists(Paths.get(outPath + "/time1.csv"));
        Files.deleteIfExists(Paths.get(outPath + "/query1.csv"));
        FileWriter timeStampWriter = new FileWriter(new File(outPath + "/time1.txt"));
        FileWriter csvWriter = new FileWriter(new File(outPath + "/query1.csv"));

        // Neighbourhood file parsing
        String s = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio de la lectura del archivo\n";
        timeStampWriter.append(s);

        final IMap<String, Integer> map = client.getMap("g10Q1Neighbourhood");
        map.clear();
        map.putAll(Loader.loadNeighbourhoods(inPath + "/barrios" + city + ".csv", city));

        // Tree file parsing
        final IMap<Integer,Tree> map2 = client.getMap("g10Q1Trees");
        map2.clear();
        map2.putAll(Loader.loadTrees(inPath + "/arboles" + city + ".csv", city));

        String t = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin de la lectura del archivo\n";
        timeStampWriter.append(t);

        // CompletableFuture object construction
        String u = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio del trabajo map/reduce\n";
        timeStampWriter.append(u);

        Job<Integer,Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map2));
        ICompletableFuture<List<Map.Entry<String,Double>>> future = job
                .mapper(new MapperQ1())
                .combiner(new CombinerFactoryQ1())
                .reducer(new ReducerQ1())
                .submit(new CollatorQ1(map));

        // Wait 15s till future is done
        try{ future.wait(15000);} catch (IllegalMonitorStateException ignored){}

        // Results printing and writing
        List<Map.Entry<String,Double>> result = future.get();
        String v = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin del trabajo map/reduce\n";
        timeStampWriter.append(v);
        csvWriter.append("BARRIO;ARBOLES_POR_HABITANTE\n");

        for(Map.Entry<String,Double> entry : result){
            csvWriter.append(String.format("%s;%2.2f\n", entry.getKey(), entry.getValue()));
            logger.info(String.format("%s;%2.2f\n", entry.getKey(), entry.getValue()));
        }

        // Exit
        csvWriter.close();
        timeStampWriter.close();
        System.exit(1);
    }
}



