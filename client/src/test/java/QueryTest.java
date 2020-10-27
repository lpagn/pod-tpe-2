import ar.edu.itba.pod.client.utils.Loader;
import collators.CollatorQ1;
import collators.CollatorQ2;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.CombinerFactoryQ1;
import combiners.CombinerFactoryQ2;
import mappers.MapperQ1;
import mappers.MapperQ2;
import models.Tree;
import org.junit.*;
import predicate.KeyPredicateQ2;
import reducers.ReducerFactoryQ1;
import reducers.ReducerFactoryQ2;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class QueryTest {
    private static HazelcastInstance client;

    @BeforeClass
    public static void init(){
        // Hazelcast cluster
        Config config = new Config().setGroupConfig(
                new GroupConfig().setName("g10").setPassword("g10"))
                .setInstanceName("g10")
                .addMapConfig(new MapConfig().setName("g10Q1Trees"))
                .addMapConfig(new MapConfig().setName("g10Q1Neighbourhood"))
                .addMapConfig(new MapConfig().setName("g10Q2Trees"))
                .addMapConfig(new MapConfig().setName("g10Q3Trees"))
                .addMapConfig(new MapConfig().setName("g10Q4Trees"))
                .addMapConfig(new MapConfig().setName("g10Q5Trees"));
        Hazelcast.newHazelcastInstance(config);

        // Hazelcast client
        final ClientConfig ccfg = new ClientConfig()
                .setGroupConfig(new GroupConfig()
                        .setName("g10")
                        .setPassword("g10"));

        client = HazelcastClient.newHazelcastClient(ccfg);
    }

    @Test
    public void testQuery1() throws ExecutionException, InterruptedException {
        // Neighbourhood file parsing
        final IMap<String, Integer> map = client.getMap("g10Q1Neighbourhood");
        map.clear();
        URL neigh = QueryTest.class.getClassLoader().getResource("barriosBUEtestQ1.csv");

        if(neigh == null){
            System.exit(-1);
        }

        map.putAll(Loader.loadNeighbourhoods(neigh.getFile(), "BUE"));

        // Tree file parsing
        final IMap<Integer, Tree> map2 = client.getMap("g10Q1Trees");
        map2.clear();
        URL trees = QueryTest.class.getClassLoader().getResource("arbolesBUEtestQ1.csv");

        if(trees == null){
            System.exit(-1);
        }

        map2.putAll(Loader.loadTrees(trees.getFile(), "BUE"));

        // CompletableFuture object construction
        Job<Integer,Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map2));
        ICompletableFuture<List<Map.Entry<String,Double>>> future = job
                .mapper(new MapperQ1())
                .combiner(new CombinerFactoryQ1())
                .reducer(new ReducerFactoryQ1())
                .submit(new CollatorQ1(map));

        // Wait 15s till future is done
        try{ future.wait(15000);} catch (IllegalMonitorStateException ignored){}

        // Results assertion
        List<Map.Entry<String,Double>> result = future.get();
        for(Map.Entry<String,Double> entry : result){
            Assert.assertEquals("1", entry.getKey());
            Assert.assertEquals(0.3, entry.getValue(), 0.001);
        }
    }

    @Test
    public void testQuery2() throws ExecutionException, InterruptedException {
        // Neighbourhood file parsing
        final IMap<String, Integer> neighs = client.getMap("g10Q2Neighbourhood");
        neighs.clear();
        URL neigh = QueryTest.class.getClassLoader().getResource("barriosBUEtestQ2.csv");

        if(neigh == null){
            System.exit(-1);
        }

        neighs.putAll(Loader.loadNeighbourhoods(neigh.getFile(), "BUE"));

        // Tree file parsing
        final IMap<Map.Entry<Integer, String>, Tree> trees = client.getMap("g10Q2Trees");
        trees.clear();
        URL t = QueryTest.class.getClassLoader().getResource("arbolesBUEtestQ2.csv");

        if(t == null){
            System.exit(-1);
        }

        trees.putAll(Loader.loadNeighAndTree(t.getFile(), "BUE"));

        // CompletableFuture object construction
        Job<Map.Entry<Integer,String>, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(trees));
        JobCompletableFuture<Set<Map.Entry<String, String>>> future = job
                .keyPredicate(new KeyPredicateQ2(neighs.keySet()))
                .mapper( new MapperQ2() )
                .combiner(new CombinerFactoryQ2())
                .reducer( new ReducerFactoryQ2() )
                .submit(new CollatorQ2(0));


        // Results assertion
        Set<Map.Entry<String, String>> result = future.get();
        Assert.assertEquals(result.size(),2);
        for(Map.Entry<String,String> e : result){
            if(e.getKey().equals("1")){
                Assert.assertEquals(e.getValue(),"Pergamino;2");
            }
            else if(e.getKey().equals("2")){
                Assert.assertEquals(e.getValue(),"La Portena;1");
            }
        }

    }

    @AfterClass
    public static void tearDown(){
        Hazelcast.shutdownAll();
    }
}
