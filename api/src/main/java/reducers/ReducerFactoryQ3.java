package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.Map;

public class ReducerFactoryQ3 implements ReducerFactory<String, Map.Entry<Double,Integer>,Double> {
    @Override
    public Reducer<Map.Entry<Double,Integer>,Double> newReducer(String s) {
        return new ReducerQ3();
    }

    private class ReducerQ3 extends Reducer<Map.Entry<Double,Integer>,Double>{
        private volatile double sumDiameters;
        private volatile double sumTotals;

        @Override
        public void beginReduce() {
            sumDiameters = 0;
            sumTotals = 0;
        }

        @Override
        public void reduce(Map.Entry<Double,Integer> x) {
            sumDiameters += x.getKey();
            sumTotals += x.getValue();
            //System.out.println(x);
        }

        @Override
        public Double finalizeReduce() {
            return sumDiameters/sumTotals;
        }
    }
}
