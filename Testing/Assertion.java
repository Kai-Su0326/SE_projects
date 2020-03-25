public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    static Obj assertThat(Object o) {
        return new Obj(o);
    }
    static Str assertThat(String s) {
        return new Str(s);
    }
    static Boo assertThat(boolean b) {
	    return new Boo(b);
    }
    static Int assertThat(int i) {
	    return new Int(i);
    }
}