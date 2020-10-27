package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class CombinerFactoryQ4 implements CombinerFactory<String, Integer, Integer> {
    @Override
    public Combiner<Integer, Integer> newCombiner(String key ) {
        return new CombinerQ4();
    }

    private static class CombinerQ4 extends Combiner<Integer, Integer> {
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
