package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.CollatorQ3;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import mappers.MapperQ3;
import models.Pair;
import models.Tree;
import reducers.ReducerFactoryQ3;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Query3 {

    static final String QUERY= "/query3.csv";
    static final String TIME = "/time3.txt";

    public static void main(String [] args) throws ExecutionException, InterruptedException, IOException {
        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String n = System.getProperty("n");


        final ClientConfig ccfg = new XmlClientConfigBuilder(Query4.class.getClassLoader().getResourceAsStream("hazelcast.xml")).build();
        ccfg.getNetworkConfig().setAddresses(Arrays.asList(addresses.split(";")));
        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        Files.deleteIfExists(Paths.get(outPath+QUERY));
        Files.deleteIfExists(Paths.get(outPath+TIME));
        FileWriter csvWriter = new FileWriter(new File(outPath+QUERY));
        FileWriter timeStampWriter = new FileWriter(new File(outPath+TIME));

        final IMap<Integer, Tree> map3 = client.getMap("g10Q3Trees");
        map3.clear();

        String s = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Inicio de la lectura del archivo\n";
        timeStampWriter.append(s);

        map3.putAll(Loader.loadTrees(inPath + "/arboles" + city + ".csv", city));

        String t = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Fin de la lectura del archivo\n";
        timeStampWriter.append(t);

        String u = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Inicio del trabajo map/reduce\n";
        timeStampWriter.append(u);

        Job<Integer, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map3));
        JobCompletableFuture<List<Pair<String, Double>>> future = job
                .mapper( new MapperQ3() )
                .reducer( new ReducerFactoryQ3() )
                .submit(new CollatorQ3(Integer.parseInt(n)));

        while(!future.isDone());

        String v = QueryUtils.now() + " INFO [main] Query3 (Query3.java:xx) - Fin del trabajo map/reduce\n";
        timeStampWriter.append(v);

        List<Pair<String, Double>> result = future.get();

//        result.forEach((x)->System.out.println(x.getKey() + " " + x.getValue()));

        csvWriter.append("NOMBRE_CIENTIFICO;PROMEDIO_DIAMETRO\n");
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
