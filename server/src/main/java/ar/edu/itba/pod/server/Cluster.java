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
                new GroupConfig().setName("g10").setPassword("g10"))
                .setInstanceName("g10")
                .addMapConfig(new MapConfig().setName("g10Q1Trees"))
                .addMapConfig(new MapConfig().setName("g10Q1Neighbourhood"))
                .addMapConfig(new MapConfig().setName("g10Q2Trees"))
                .addMapConfig(new MapConfig().setName("g10Q3Trees"))
                .addMapConfig(new MapConfig().setName("g10Q4Trees"))
                .addMapConfig(new MapConfig().setName("g10Q5Trees"));

        Hazelcast.newHazelcastInstance(config);


    }
}
