package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.utils.Loader;
import ar.edu.itba.pod.client.utils.QueryUtils;
import collators.CollatorQ4;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;
import combiners.CombinerFactoryQ4;
import mappers.MapperQ4;
import reducers.ReducerFactoryQ4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query4 {

    public static void main(String [] args) throws ExecutionException, InterruptedException, IOException {


        final String city = System.getProperty("city");
        final String addresses = System.getProperty("addresses");
        final String inPath = System.getProperty("inPath");
        final String outPath = System.getProperty("outPath");
        final String name = System.getProperty("name");
        final int min= Integer.parseInt(System.getProperty("min"));

        final ClientConfig ccfg = new XmlClientConfigBuilder(Query4.class.getClassLoader().getResourceAsStream("hazelcast.xml")).build();
        ccfg.getNetworkConfig().setAddresses(Arrays.asList(addresses.split(";")));
        ccfg.getGroupConfig().setName("g10").setPassword("g10-pass");
        final HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);


        Files.deleteIfExists(Paths.get(outPath+"/time4.csv"));
        Files.deleteIfExists(Paths.get(outPath+"/query4.csv"));

        FileWriter timeStampWriter = new FileWriter(new File(outPath+"/time4.txt"));
        FileWriter csvWriter = new FileWriter(new File(outPath+"/query4.csv"));

        String s = QueryUtils.now() + " Query4: - Inicio de la lectura del archivo\n";
        timeStampWriter.append(s);

        final IMap< String,Map.Entry<String,String>> map = client.getMap("g10Q4NeighToTreeName");
        map.clear();

        map.putAll(Loader.loadNeighToTreeName(inPath + "/arboles" + city + ".csv", city));

        String t = QueryUtils.now() + " Query4: - Fin de la lectura del archivo\n";
        timeStampWriter.append(t);

        String u = QueryUtils.now() + " Query4: - Inicio del trabajo map/reduce\n";
        timeStampWriter.append(u);

        JobTracker jobTracker = client.getJobTracker("g10q4");
        final KeyValueSource<String, Map.Entry<String,String>> source = KeyValueSource.fromMap(map);
        Job<String, Map.Entry<String,String>> job = jobTracker.newJob(source);
        ICompletableFuture<List<Map.Entry<String,String>>> future = job
                .mapper( new MapperQ4(name))
                .combiner( new CombinerFactoryQ4() )
                .reducer( new ReducerFactoryQ4())
                .submit( new CollatorQ4(min));

        List<Map.Entry<String,String>> result = future.get();

        String v = QueryUtils.now() + " Query4: - Fin del trabajo map/reduce\n";
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
        client.shutdown();
//        JobTracker jobTracker = client.getJobTracker("g10q4");
//        final KeyValueSource<Integer, Tree> source = KeyValueSource.fromMap(map2);
//        Job<Integer, Tree> job = jobTracker.newJob(source);
//        ICompletableFuture<List<Pair<String,String>>> future = job
//                .mapper( new MapperQ4(specie))
//                .combiner( new CombinerFactoryQ4() )
//                .reducer( new Reducer1Q4())
//                .submit( new CollatorQ4());
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