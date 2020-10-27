package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class CombinerFactoryQ1 implements CombinerFactory<String,Integer,Integer> {

    @Override
    public Combiner<Integer, Integer> newCombiner(String s) {
        return new InnerCombiner();
    }

    private static class InnerCombiner extends Combiner<Integer, Integer>{
        private Integer sum = 0;

        @Override
        public synchronized void combine(Integer integer) {
            sum += integer;
        }

        @Override
        public Integer finalizeChunk() {
            return sum;
        }

        @Override
        public synchronized void reset() {
            sum = 0;
        }
    }

}
