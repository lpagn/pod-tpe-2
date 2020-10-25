package models;

public class Q5ans {
    int group;
    String n1;
    String n2;
    public Q5ans(int group, String n1, String n2){ ;
        this.group = group;
        this.n1 = n1;
        this.n2 = n2;
    }
    @Override
    public String toString(){
        return group + ";" + n1 + ";" + n2;
    }
}



