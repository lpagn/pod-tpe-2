package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class CombinerFactoryQ3 implements CombinerFactory <String,Double,Double>{

    @Override
    public Combiner<Double, Double> newCombiner(String s) {
        return new CombinerQ3();
    }

    private class CombinerQ3 extends Combiner<Double,Double> {
        private volatile double sumDiameters;
        private volatile double sumTotals;

        @Override
        public void reset() {
            sumDiameters = 0;
            sumTotals = 0;
        }

        @Override
        public void combine(Double x) {
            sumDiameters += x;
            sumTotals += 1;
        }

        @Override
        public Double finalizeChunk() {
            return sumDiameters/sumTotals;
        }
    }
}
