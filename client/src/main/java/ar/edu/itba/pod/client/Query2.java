package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.CollatorQ2;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ISet;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.CombinerFactoryQ2;
import mappers.MapperQ2;
import models.Tree;
import predicate.KeyPredicateQ2;
import reducers.ReducerFactoryQ2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Query2 {
    public static void main(String [] args) throws ExecutionException, InterruptedException, IOException {

        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String min = System.getProperty("min");

        final ClientConfig ccfg = new XmlClientConfigBuilder(Query4.class.getClassLoader().getResourceAsStream("hazelcast.xml")).build();
        ccfg.getNetworkConfig().setAddresses(Arrays.asList(addresses.split(";")));
        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<String,Map.Entry<String, String>> trees = client.getMap("g10Q2Trees");
        trees.clear();

        final ISet<String> ineighs = client.getSet("g10Q2N");
        ineighs.clear();


        Files.deleteIfExists(Paths.get(outPath+"/time2.txt"));
        Files.deleteIfExists(Paths.get(outPath+"/query2.csv"));

        FileWriter timeStampWriter = new FileWriter(new File(outPath+"/time2.txt"));
        FileWriter csvWriter = new FileWriter(new File(outPath+"/query2.csv"));

        String s = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio de la lectura del archivo\n";
        timeStampWriter.append(s);
        trees.putAll(Loader.loadNeighAndTree(inPath + "/arboles" + city + ".csv", city));
        ineighs.addAll(Loader.loadNeighbourhoodsSet(inPath + "/barrios" + city + ".csv", city));

        Set<String> neighs = new HashSet<>(ineighs);
        String t = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin de la lectura del archivo\n";
        timeStampWriter.append(t);

        String u = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio del trabajo map/reduce\n";
        timeStampWriter.append(u);
        Job<String,Map.Entry<String,String>> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(trees));
        JobCompletableFuture<Set<Map.Entry<String, String>>> future = job
                .mapper( new MapperQ2(neighs) )
                .combiner(new CombinerFactoryQ2())
                .reducer( new ReducerFactoryQ2() )
                .submit(new CollatorQ2(Integer.parseInt(min)));

        Set<Map.Entry<String, String>> result = future.get();
        
        String v = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin del trabajo map/reduce\n";
        timeStampWriter.append(v);

        csvWriter.append("BARRIO;CALLE_CON_MAS_ARBOLES;ARBOLES\n");
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

    }
}
