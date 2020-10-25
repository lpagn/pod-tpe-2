package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

import java.util.Map;

public class combinerq2 implements CombinerFactory<Map.Entry<String,String>,Integer,Integer> {
    @Override
    public Combiner<Integer, Integer> newCombiner(Map.Entry<String, String> stringStringEntry) {
        return null;
    }

    private class cq2 extends Combiner<Integer,Integer>{
        private Integer sum = 0;

        @Override
        public void reset() {
            sum = 0;
        }

        @Override
        public void combine(Integer value) {
            sum += value;
        }

        @Override
        public Integer finalizeChunk() {
            return sum;
        }
    }
}
