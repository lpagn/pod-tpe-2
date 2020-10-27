package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class ReducerFactoryQ3 implements ReducerFactory<String,Double,Double> {
    @Override
    public Reducer<Double,Double> newReducer(String s) {
        return new ReducerQ3();
    }

    private class ReducerQ3 extends Reducer<Double,Double>{
        private volatile double sumDiameters;
        private volatile double sumTotals;

        @Override
        public void beginReduce() {
            sumDiameters = 0;
            sumTotals = 0;
        }

        @Override
        public void reduce(Double x) {
            sumDiameters += x;
            sumTotals += 1;
            //System.out.println(x);
        }

        @Override
        public Double finalizeReduce() {
            return sumDiameters/sumTotals;
        }
    }
}
