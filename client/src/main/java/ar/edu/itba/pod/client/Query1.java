package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import collators.QueryOneCollator;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.KeyValueSource;
import mappers.QueryOneMapper;
import models.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reducers.QueryOneReducer;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 {
    private static final Logger logger = LoggerFactory.getLogger(Query1.class);

    public static void main(String [] args) throws ExecutionException, InterruptedException {

        // Getting params
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");

        // Hazelcast config
        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        // Neighbourhood file parsing
        final IMap<String, Integer> map = client.getMap("g10Q1Neighbourhood");
        map.clear();
        URL neigh = Query1.class.getClassLoader().getResource("barriosBUE.csv");

        if(neigh == null){
            logger.error("Error loading neighbourhood file");
            System.exit(-1);
        }

        map.putAll(Loader.loadNeighbourhoods(neigh.getFile()));

        // Tree file parsing
        final IMap<Integer,Tree> map2 = client.getMap("g10Q1Trees");
        map2.clear();
        URL trees = Query1.class.getClassLoader().getResource("arbolesBUE.csv");

        if(trees == null){
            logger.error("Error loading tree file");
            System.exit(-1);
        }

        map2.putAll(Loader.loadTrees(trees.getFile()));

        // CompletableFuture object construction
        Job<Integer,Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map2));
        ICompletableFuture<Map<String,Double>> future = job
                .mapper(new QueryOneMapper())
                .reducer(new QueryOneReducer())
                .submit(new QueryOneCollator(map));

        // Wait 15s till future is done
        try{ future.wait(15000);} catch (IllegalMonitorStateException ignored){}

        // Results printing
        Map<String,Double> result = future.get();
        for(Map.Entry<String,Double> entry : result.entrySet()){
            logger.info(String.format("%s: %f\n", entry.getKey(), entry.getValue()));
        }
    }
}



