package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.CollatorQ5;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.CombinerFactoryQ5;
import mappers.MapperQ5;
import models.Q5ans;
import models.Tree;
import reducers.ReducerFactoryQ5;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Query5 {

    static final String QUERY= "/query5.csv";
    static final String TIME = "/time5.txt";

    public static void main(String [] args) throws ExecutionException, InterruptedException, IOException {
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");


        Files.deleteIfExists(Paths.get(outPath+QUERY));
        Files.deleteIfExists(Paths.get(outPath+TIME));

        FileWriter csvWriter = new FileWriter(new File(outPath+QUERY));
        FileWriter timeStampWriter = new FileWriter(new File(outPath+TIME));


        final ClientConfig ccfg = new XmlClientConfigBuilder(Query4.class.getClassLoader().getResourceAsStream("hazelcast.xml")).build();
        ccfg.getNetworkConfig().setAddresses(Arrays.asList(addresses.split(";")));
        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        final IMap<String, Tree> map5 = client.getMap("g10Q5Trees");
        map5.clear();

        String s = QueryUtils.now() + " Query5: - Inicio de la lectura del archivo\n";
        timeStampWriter.append(s);

        map5.putAll(Loader.loadTrees(inPath + "/arboles" + city + ".csv", city));

        String t = QueryUtils.now() + " Query5: - Fin de la lectura del archivo\n";
        timeStampWriter.append(t);

        String u = QueryUtils.now() + " Query5: - Inicio del trabajo map/reduce\n";
        timeStampWriter.append(u);

        Job<String, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map5));
        JobCompletableFuture<List<Q5ans>> future = job
                .mapper( new MapperQ5() )
                .combiner( new CombinerFactoryQ5())
                .reducer( new ReducerFactoryQ5())
                .submit(new CollatorQ5());

        while(!future.isDone());

        String v = QueryUtils.now() + " Query5: - Fin del trabajo map/reduce\n";
        timeStampWriter.append(v);

        List<Q5ans> result = future.get();

//        result.forEach(System.out::println);

        csvWriter.append("Grupo;Barrio A;Barrio B\n");
        result.forEach((element) -> {
            try {
                csvWriter.append(element.toString()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();
        timeStampWriter.close();
        client.shutdown();
    }
}
