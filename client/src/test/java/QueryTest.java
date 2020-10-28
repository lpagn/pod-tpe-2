import ar.edu.itba.pod.client.utils.Loader;
import collators.CollatorQ1;
import collators.CollatorQ2;
import collators.CollatorQ3;
import collators.CollatorQ4;
import collators.CollatorQ5;
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
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.CombinerFactoryQ1;
import combiners.CombinerFactoryQ2;
import combiners.CombinerFactoryQ3;
import combiners.CombinerFactoryQ4;
import combiners.CombinerFactoryQ5;
import mappers.MapperQ1;
import mappers.MapperQ2;
import mappers.MapperQ3;
import mappers.MapperQ4;
import models.Pair;
import mappers.MapperQ5;
import models.Q5ans;
import models.Tree;
import org.junit.*;
import predicate.KeyPredicateQ2;
import predicate.KeyPredicateQ4;
import reducers.ReducerFactoryQ1;
import reducers.ReducerFactoryQ2;
import reducers.ReducerFactoryQ3;
import reducers.ReducerFactoryQ4;
import reducers.ReducerFactoryQ5;

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
                .addMapConfig(new MapConfig().setName("g10Q4NeighToTreeName"))
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
        final IMap<String, Tree> map2 = client.getMap("g10Q1Trees");
        map2.clear();
        URL trees = QueryTest.class.getClassLoader().getResource("arbolesBUEtestQ1.csv");

        if(trees == null){
            System.exit(-1);
        }

        map2.putAll(Loader.loadTrees(trees.getFile(), "BUE"));

        // CompletableFuture object construction
        Job<String,Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map2));
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
        final IMap<Map.Entry<String, String>, String> trees = client.getMap("g10Q2Trees");
        trees.clear();
        URL t = QueryTest.class.getClassLoader().getResource("arbolesBUEtestQ2.csv");

        if(t == null){
            System.exit(-1);
        }

        trees.putAll(Loader.loadNeighAndTree(t.getFile(), "BUE"));

        // CompletableFuture object construction
        Job<Map.Entry<String,String>, String> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(trees));
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

    @Test
    public void testQuery4Filled() throws ExecutionException, InterruptedException {
        // Neighbourhood file parsing

        // Tree file parsing
        final IMap<Map.Entry<String,String>, String> map = client.getMap("g10Q4NeighToTreeName");
        map.clear();
        URL trees = QueryTest.class.getClassLoader().getResource("arbolesBUEtestQ4.csv");

        if(trees == null){
            System.exit(-1);
        }
        String name = "Fraxinus pennsylvanica";
        Integer min = 1;
        map.putAll(Loader.loadNeighToTreeName(trees.getFile(), "BUE"));
        JobTracker jobTracker = client.getJobTracker("g10q4");
        final KeyValueSource<Map.Entry<String,String>, String> source = KeyValueSource.fromMap(map);
        // CompletableFuture object construction
        Job<Map.Entry<String,String>, String> job = jobTracker.newJob(source);
        ICompletableFuture<List<Map.Entry<String,String>>> future = job
                .keyPredicate(new KeyPredicateQ4(name))
                .mapper( new MapperQ4())
                .combiner( new CombinerFactoryQ4() )
                .reducer( new ReducerFactoryQ4())
                .submit( new CollatorQ4(min));

        // Results assertion
        List<Map.Entry<String,String>> result = future.get();
        Assert.assertEquals(result.get(0).getKey(),"1");
        Assert.assertEquals(result.get(0).getValue(),"2");
    }

    @Test
    public void testQuery4Empty() throws ExecutionException, InterruptedException {
        // Neighbourhood file parsing

        // Tree file parsing
        final IMap<Map.Entry<String,String>, String> map = client.getMap("g10Q4NeighToTreeName");
        map.clear();
        URL trees = QueryTest.class.getClassLoader().getResource("arbolesBUEtestQ4.csv");

        if(trees == null){
            System.exit(-1);
        }
        String name = "Fraxinus pennsylvanica";
        Integer min = 10;
        map.putAll(Loader.loadNeighToTreeName(trees.getFile(), "BUE"));
        JobTracker jobTracker = client.getJobTracker("g10q4");
        final KeyValueSource<Map.Entry<String,String>, String> source = KeyValueSource.fromMap(map);
        // CompletableFuture object construction
        Job<Map.Entry<String,String>, String> job = jobTracker.newJob(source);
        ICompletableFuture<List<Map.Entry<String,String>>> future = job
                .keyPredicate(new KeyPredicateQ4(name))
                .mapper( new MapperQ4())
                .combiner( new CombinerFactoryQ4() )
                .reducer( new ReducerFactoryQ4())
                .submit( new CollatorQ4(min));

        // Results assertion
        List<Map.Entry<String,String>> result = future.get();
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testQuery3() throws ExecutionException, InterruptedException {

        // Tree file parsing
        final IMap<String, Tree> map = client.getMap("g10Q3Trees");
        map.clear();
        URL trees = QueryTest.class.getClassLoader().getResource("arbolesBUEtestQ5.csv");

        map.putAll(Loader.loadTrees(trees.getFile(), "BUE"));

        // CompletableFuture object construction
        Job<String, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map));
        JobCompletableFuture<List<Pair<String, Double>>> future = job
                .mapper(new MapperQ3())
                .combiner(new CombinerFactoryQ3())
                .reducer(new ReducerFactoryQ3())
                .submit(new CollatorQ3(3));

        // Wait 15s till future is done
        try {
            future.wait(15000);
        } catch (IllegalMonitorStateException ignored) {
        }

        // Results assertion
        List<Pair<String, Double>> result = future.get();

        Pair<String, Double> item1 = result.get(2);
        Assert.assertEquals(item1.getKey(), "No identificado");
        Assert.assertEquals((double)item1.getValue(),142.0,0.01);

        Pair<String, Double> item2 = result.get(0);
        Assert.assertEquals(item2.getKey(), "Fraxinus excelsior");
        Assert.assertEquals((double)item2.getValue(),143.0, 0.01);

        Pair<String, Double> item3 = result.get(1);
        Assert.assertEquals(item3.getKey(), "Fraxinus pennsylvanica");
        Assert.assertEquals((double)item3.getValue(),142.5,0.01);
    }

    @Test
    public void testQuery5() throws ExecutionException, InterruptedException {
        // Tree file parsing
        final IMap<String, Tree> map = client.getMap("g10Q5Trees");
        map.clear();
        URL trees = QueryTest.class.getClassLoader().getResource("vantest.csv");

        map.putAll(Loader.loadTrees(trees.getFile(), "VAN"));

        // CompletableFuture object construction
        Job<String, Tree> job = client.getJobTracker("g10jt").newJob(KeyValueSource.fromMap(map));
        JobCompletableFuture<List<Q5ans>> future = job
                .mapper(new MapperQ5())
                .combiner(new CombinerFactoryQ5())
                .reducer(new ReducerFactoryQ5())
                .submit(new CollatorQ5());

        // Wait 15s till future is done
        try {
            future.wait(15000);
        } catch (IllegalMonitorStateException ignored) {
        }
        List<Q5ans> result = future.get();
        //result.forEach(System.out::println);
        Assert.assertEquals(result.get(0).n1,"ARBUTUS-RIDGE");
        Assert.assertEquals(result.get(0).n2,"DUNBAR-SOUTHLANDS");
    }

    @AfterClass
    public static void tearDown(){
        Hazelcast.shutdownAll();
    }
}
