package ar.edu.itba.pod.server;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class Cluster {
    public static void main(String[] args) throws FileNotFoundException {
        Config config = new XmlConfigBuilder(Objects.requireNonNull(Cluster.class.getClassLoader().getResourceAsStream("hazelcast.xml"))).build();
        config.setGroupConfig(
                new GroupConfig().setName("g10").setPassword("g10-pass"))
                .setInstanceName("g10")
                .addMapConfig(new MapConfig().setName("g10Q1Trees"))
                .addMapConfig(new MapConfig().setName("g10Q1Neighbourhood"))
                .addMapConfig(new MapConfig().setName("g10Q2Trees"))
                .addMapConfig(new MapConfig().setName("g10Q2Neighbourhood"))
                .addMapConfig(new MapConfig().setName("g10Q3Trees"))
                .addMapConfig(new MapConfig().setName("g10Q4NeighToTreeName"))
                .addMapConfig(new MapConfig().setName("g10Q5Trees"))
                .addListConfig(new ListConfig().setName("g10Q2T"))
                .addSetConfig(new SetConfig().setName("g10Q2N"));


        Hazelcast.newHazelcastInstance(config);


    }
}
