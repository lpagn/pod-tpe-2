package ar.edu.itba.pod.server;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;

public class Cluster {
    public static void main(String[] args) throws FileNotFoundException {
        URL resource = Cluster.class.getClassLoader().getResource("hazelcast.xml");
        Config config = new XmlConfigBuilder(resource.getFile()).build();
        config.setGroupConfig(
                new GroupConfig().setName("g10").setPassword("g10-pass"))
                .setInstanceName("g10")
                .addMapConfig(new MapConfig().setName("g10Q1Trees"))
                .addMapConfig(new MapConfig().setName("g10Q1Neighbourhood"))
                .addMapConfig(new MapConfig().setName("g10Q2Trees"))
                .addMapConfig(new MapConfig().setName("g10Q2Neighbourhood"))
                .addMapConfig(new MapConfig().setName("g10Q3Trees"))
                .addMapConfig(new MapConfig().setName("g10Q4NeighToTreeName"))
                .addMapConfig(new MapConfig().setName("g10Q5Trees"));

        Hazelcast.newHazelcastInstance(config);


    }
}
