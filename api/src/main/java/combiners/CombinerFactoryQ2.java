package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

import java.util.Map;

public class CombinerFactoryQ2 implements CombinerFactory<Map.Entry<String,String>,Integer,Integer> {
    @Override
    public Combiner<Integer, Integer> newCombiner(Map.Entry<String, String> stringStringEntry) {
        return new CombinerQ2();
    }

    private class CombinerQ2 extends Combiner<Integer,Integer>{
        private volatile int sum = 0;
        @Override
        public synchronized void combine( Integer value ) {
            sum++;
        }
        @Override
        public Integer finalizeChunk() {
            return sum;
        }

        @Override
        public void reset() {
            sum = 0;
        }
    }
}
