public class Obj {
    private Object input;
    Obj(){}
    Obj(Object o){
        input = o;
    }
    public Obj isNotNull() {
        if(input == null) { throw new RuntimeException(); }
        return this;
    }

    public Obj isNull() {
        if(input != null) { throw new RuntimeException(); }
        return this;
    }

    public Obj isEqualTo(Object o2) {
        if(o2 != input) { throw new RuntimeException(); }
        return this;
    }

    public Obj isNotEqualTo(Object o2) {
        if(o2 == input) { throw new RuntimeException(); }
        return this;
    }

    public Obj isInstanceOf(Class c) {

        if(!c.isInstance(input)) { throw new RuntimeException(); }
        return this;
    }
}
