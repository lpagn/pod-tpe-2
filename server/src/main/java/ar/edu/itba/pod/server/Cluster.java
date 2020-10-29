package ar.edu.itba.pod.server;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class Cluster {
    public static void main(String[] args) throws FileNotFoundException {
        String interfaces = System.getProperty("interfaces");

        if(interfaces == null || interfaces.equals("")){
            interfaces = "10.16.1.*";
        }

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
                .addSetConfig(new SetConfig().setName("g10Q2N"));

        config.getNetworkConfig().getInterfaces().setInterfaces(Arrays.asList(interfaces.split(";")));
        Hazelcast.newHazelcastInstance(config);
    }
}
