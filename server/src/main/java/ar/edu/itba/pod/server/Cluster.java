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
                .addMapConfig(new MapConfig().setName("g10m1"))
                .addMapConfig(new MapConfig().setName("g10m2"))
                .addMapConfig(new MapConfig().setName("g10m3"))
                .addMapConfig(new MapConfig().setName("g10m4"))
                .addMapConfig(new MapConfig().setName("g10m5"));

        Hazelcast.newHazelcastInstance(config);


    }
}
