package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class CombinerFactory1Q4 implements CombinerFactory<String, Integer, Integer> {
    @Override
    public Combiner<Integer, Integer> newCombiner(String key ) {
        return new Combiner1Q4();
    }

    private static class Combiner1Q4 extends Combiner<Integer, Integer> {
        private Integer sum = 0;

        @Override
        public synchronized void combine( Integer value ) {
            sum+=value;
        }

        @Override
        public synchronized Integer finalizeChunk() {
            return sum;
        }

        @Override
        public synchronized void reset() {
            sum = 0;
        }
    }

}
