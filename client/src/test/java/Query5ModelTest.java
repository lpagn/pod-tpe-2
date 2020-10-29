import models.Q5ans;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Query5ModelTest {
    @Test
    public void compareTest(){
        Q5ans x = new Q5ans(1,"A","B");
        Q5ans y = new Q5ans(2,"A","B");
        Q5ans z = new Q5ans(2,"A","A");
        Q5ans w = new Q5ans(2,"A","C");
        List<Q5ans> l = new LinkedList<>();

        l.add(x);

        l.add(z);

        l.add(y);

        l.add(w);

        l.sort(Q5ans::compareTo);

        System.out.println(l.get(0));
        System.out.println(l.get(1));
        System.out.println(l.get(2));
        System.out.println(l.get(3));
    }
}
