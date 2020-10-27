package models;

public class Q5ans {
    public int group;
    public String n1;
    public String n2;
    public Q5ans(int group, String n1, String n2){ ;
        this.group = group;
        this.n1 = n1;
        this.n2 = n2;
    }
    @Override
    public String toString(){
        return group + ";" + n1 + ";" + n2;
    }

    public int compareTo(Q5ans x1) {
        int f = n1.compareTo(x1.n1);
        if(f==0) {
            return n2.compareTo(x1.n2);
        }
        else{
            return f;
        }
    }
}



