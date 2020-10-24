package combiners;

import com.hazelcast.mapreduce.Combiner;

public class Combiner1Q4 extends Combiner<Integer, Integer> {
    private Integer sum = 0;
    @Override
    public void combine( Integer value ) {
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
