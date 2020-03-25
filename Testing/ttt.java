import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ttt {


    public static void givePer(List<Object> samples, int length, List<Object> ret, List<Object> li) {
        if (li.size() == length) {
            ret.add(li);
            return;
        }
        for(Object o : samples){
            List<Object> temp = new ArrayList<>(List.copyOf(li));
            temp.add(o);
            givePer(samples,length,ret,temp);
        }
    }
    public static void main(String[] args){
        List<Object> s = new LinkedList<>();
        List<Object> ret = new LinkedList<>();
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(4);
        List<Object> li = new LinkedList<>();
        HashSet<Object> set = new HashSet<>();
       // givePer(s,2,0,ret,li,set);
        int min = 0;
        int max = 2;
        for(int i = min;i<=max;i++){
            givePer(s,i,ret,li);
        }
        System.out.println(ret);
    }
}
