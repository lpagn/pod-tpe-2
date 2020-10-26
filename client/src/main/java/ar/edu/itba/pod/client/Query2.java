package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.collatorq2;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.CombinerFactoryQ2;
import mappers.MapperQ2;
import models.Tree;
import reducers.ReducerFactoryQ2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Query2 {
    public static void main(String [] args) throws ExecutionException, InterruptedException, IOException {
        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<Integer, Tree> map = client.getMap("g10Q2Trees");
        map.clear();

        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String min = System.getProperty("min");
        String[] address = addresses.split(";");


        System.out.println(city + " " + address+ " " + inPath + " " + outPath + " " + min);

        Files.deleteIfExists(Paths.get(outPath+"/timeStamps.csv"));
        Files.deleteIfExists(Paths.get(outPath+"/result.csv"));

        FileWriter timeStampWriter = new FileWriter(new File(outPath+"/timeStamps.txt"));
        FileWriter csvWriter = new FileWriter(new File(outPath+"/result.csv"));

        String s = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio de la lectura del archivo\n";
        timeStampWriter.append(s);
        map.putAll(Loader.loadTrees(inPath + "/arboles" + city + ".csv", city));
        String t = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Fin de la lectura del archivo\n";
        timeStampWriter.append(t);

        String u = QueryUtils.now() + " INFO [main] Query2 (Query2.java:xx) - Inicio del trabajo map/reduce\n";
        timeStampWriter.append(u);
        Job<Integer, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map));
        JobCompletableFuture<Set<Map.Entry<String, String>>> future = job
                .mapper( new MapperQ2() )
                .combiner(new CombinerFactoryQ2())
                .reducer( new ReducerFactoryQ2() )
                .submit(new collatorq2(10));

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
