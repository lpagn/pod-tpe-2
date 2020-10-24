package ar.edu.itba.pod.server;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class Cluster {
    public static void main(String[] args) {
        Config config = new Config().setGroupConfig(
                new GroupConfig().setName("grupo10").setPassword("grupo10"))
                .setInstanceName("grupo10")
                .addMapConfig(new MapConfig().setName("Q1Trees"))
                .addMapConfig(new MapConfig().setName("Q1Neighbourhood"))
                .addMapConfig(new MapConfig().setName("Q2Trees"))
                .addMapConfig(new MapConfig().setName("Q3Trees"))
                .addMapConfig(new MapConfig().setName("Q4Trees"))
                .addMapConfig(new MapConfig().setName("Q5Trees"));

        Hazelcast.newHazelcastInstance(config);


    }
}
