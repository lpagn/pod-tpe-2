package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

import java.util.AbstractMap;
import java.util.Map;

public class CombinerFactoryQ3 implements CombinerFactory <String, Map.Entry<Double,Integer>,Map.Entry<Double,Integer>>{

    @Override
    public Combiner<Map.Entry<Double,Integer>, Map.Entry<Double,Integer>> newCombiner(String s) {
        return new CombinerQ3();
    }

    private class CombinerQ3 extends Combiner<Map.Entry<Double,Integer>,Map.Entry<Double,Integer>> {
        private volatile double sumDiameters;
        private volatile int sumTotals;

        @Override
        public void reset() {
            sumDiameters = 0;
            sumTotals = 0;
        }

        @Override
        public void combine(Map.Entry<Double,Integer> x) {
            sumDiameters += x.getKey();
            sumTotals += x.getValue();
        }

        @Override
        public Map.Entry<Double,Integer> finalizeChunk() {
            return new AbstractMap.SimpleEntry<>(sumDiameters,sumTotals);
        }
    }
}
