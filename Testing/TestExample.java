import java.util.*;

public class TestExample {

    private static Integer a;
    private Integer b;

//    @BeforeClass
//    public static void setUp() {
//        a = 1;
//    }
//
//    @Before
//    public void set() {
//        b = 1;
//        System.out.println("Before!!!");
//    }
//
//    @Test
//    public void test() {
//        Assertion.assertThat(a).isEqualTo(b);
//
//    }
//
//    @AfterClass
//    public static void tearDown() {
//        System.out.println("Tear down!!!");
//    }
//    @Test
//    public void test2() {
//        if (!a.equals(b)) {
//            System.out.println("Test222!!!");
//
//        }
//    }
//    @AfterClass
//    public static void aftercl(){
//        System.out.println("AfterClass!!!!!");
//        //throw new RuntimeException();
//    }
//
    @Property
    public int add(@ListLength(min = 0,max = 2) List<@IntRange(min=5, max=7) Integer> li, @StringSet(strings = {"abc","vbd"}) String b){
        throw new RuntimeException();
    }

    public static void main(String[] args) throws Exception {
        //TestExample.setUp();
//        TestExample t = new TestExample();
//        t.set();
//        t.test();
 //       t.add(2,3);
//        HashMap<String, Throwable> mmp = Unit.testClass("TestExample");
//        return;
        //HashMap<String, Object[]> map;
        HashMap<String, Object[]> testExample = Unit.quickCheckClass("TestExample");
        return;
    }
}
