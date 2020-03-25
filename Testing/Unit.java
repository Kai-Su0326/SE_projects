import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static java.util.Comparator.comparing;
//REMEMBER CLEAR THE STATIC VARIABLE
public class Unit {
    // Input name is the name of a function
    // 1. find out all methods associated with "name", e.g.: moves()
    // 2. figure out which ones are test methods e.g.: test_moves_pawn; test_moves_king
    // 3. run the test cases
    private static Map<String,List<Method>> map;

    private static Set<String> set = new HashSet<>(new ArrayList<>(
            List.of("Before", "Test", "AfterClass", "After", "BeforeClass")));
    static Method[] get_methods(String name) throws ClassNotFoundException {
        Class c = Class.forName(name);
        return c.getDeclaredMethods();
    }
    static void oneAnn(String name) throws ClassNotFoundException {
        Method[] meths = get_methods(name);
        for(Method meth : meths){
            Annotation[] anns = meth.getAnnotations();
            int count = 0;
            for(Annotation ann : anns){
                if(set.contains(ann.annotationType().getName()))
                    if(++count > 1) {throw new RuntimeException();}
            }
        }
    }
    private static boolean isStatic(Method meth){
        List<String> meth_name = Arrays.asList(meth.toString().split(" "));
        return meth_name.contains("static");
    }

    private static void fill_map(String name) throws ClassNotFoundException {
        map = new HashMap<>();
        Method[] meths = get_methods(name);
        for(Method meth : meths){
            Annotation[] anns = meth.getAnnotations();
            for(Annotation ann : anns) {
                String ann_name = ann.annotationType().getName();
                if(ann_name.equals("BeforeClass") || ann_name.equals("AfterClass")){
                    if(isStatic(meth)){
                        if(map.getOrDefault(ann_name,null) == null){
                            List<Method> li = new ArrayList<>();
                            map.put(ann_name,li);
                        }
                        map.get(ann_name).add(meth);
                    } else {
                        throw new RuntimeException();
                    }
                } else if (set.contains(ann_name)){
                    if(map.getOrDefault(ann_name,null) == null){
                        List<Method> li = new ArrayList<>();
                        map.put(ann_name,li);
                    }
                    map.get(ann_name).add(meth);
                }
            }
        }
        for(String str : map.keySet()){
            map.get(str).sort(comparing(Method::getName));
        }
    }

    public static HashMap<String, Throwable> testClass(String name) throws Exception {
        oneAnn(name);
        fill_map(name);
        HashMap<String, Throwable> ret = new HashMap<>();
        Class c = null;
        c = Class.forName(name);
        Object instance = null;
        instance = c.getConstructor().newInstance();
        for (Method beforeclass : map.getOrDefault("BeforeClass",new LinkedList<>())) {
            try {
                beforeclass.invoke(null);
            } catch (Exception e) {
                System.out.println("BeforeClass method failed: " + beforeclass.getName());
                throw new RuntimeException();
            }
        }
        for (Method testMeth : map.getOrDefault("Test",new LinkedList<>())) {
            for(Method beforeMeth : map.getOrDefault("Before",new LinkedList<>())) {
                try {
                    beforeMeth.invoke(instance);
                } catch (Exception e) {
                    System.out.println("Before method failed: " + beforeMeth.getName());
                    throw new RuntimeException();
                }
            }
            try {
                testMeth.invoke(instance);
                ret.put(testMeth.getName(),null);
            } catch (Exception e) {
                ret.put(testMeth.getName(), e.getCause());
            }
            if(map.containsKey("After")){
                for(Method afterMeth : map.getOrDefault("After",new LinkedList<>())){
                    try {
                        afterMeth.invoke(instance);
                    } catch (Exception e) {
                        System.out.println("After method failed: " + afterMeth.getName());
                        throw new RuntimeException();
                    }
                }
            }
        }
        for(Method afterclass : map.getOrDefault("AfterClass",new LinkedList<>())) {
            try {
                afterclass.invoke(null);
            } catch (Exception e) {
                System.out.println("After class method failed: " + afterclass.getName());
                throw new RuntimeException();
            }
        }
        return ret;
    }

    private static void comboHelper(List<List<Object>> list,List<Object[]> saving, Object[] sub, int depth){
        if(list.size() == depth) {
            saving.add(sub);
            return;
        }
        for(int i = 0;i < list.get(depth).size();i++) {
            Object[] subling = Arrays.copyOf(sub, sub.length);
            subling[depth] = list.get(depth).get(i);
            comboHelper(list, saving, subling,depth + 1);
        }
    }

    private static List<Object> fill_Int(AnnotatedType ann){
        IntRange intrange = (IntRange) ann.getAnnotations()[0];
        int min = intrange.min();
        int max = intrange.max();
        List<Object> int_list = new LinkedList<>();
        for(int i = min;i <= max; i++) { int_list.add(i); }
        return int_list;
    }

    private static List<Object> forall(AnnotatedType ann){
        ForAll forall = (ForAll) ann.getAnnotations()[0];
        Class p = null;
        try { p = Class.forName("Property"); }
        catch (Exception e) { }
        Method[] p_meths = p.getDeclaredMethods();
        Object prop = null;
        try { prop = p.getConstructor().newInstance(); }
        catch (Exception e) {}
        List<Object> faList = new LinkedList<>();
        for(Method pmeth : p_meths){
            if(pmeth.getName().equals(forall.name())) {
                for(int i = 0; i < forall.times(); i++) {
                    Object val = null;
                    try { val = pmeth.invoke(prop); } catch (Exception ignored) {}
                    faList.add(val);
                }
            }
        }
        return faList;
    }

    private static List<Object> string(AnnotatedType ann){
        StringSet stringset = (StringSet) ann.getAnnotations()[0];
        String[] strings = stringset.strings();
        return Arrays.asList(strings);
    }


    private static void givePer(List<Object> samples, int length, List<Object> ret, List<Object> li) {
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

    private static List<Object> flist(List<Object> samples, int min, int max){
        List<Object> ret = new LinkedList<>();
        List<Object> li = new LinkedList<>();
        for(int i = min;i <= max;i++){
            givePer(samples,i,ret,li);
        }
        return ret;
    }

    
    private static List<Object> targ(AnnotatedType ann){
//        if (ann.isAnnotationPresent(ListLength))
        ListLength listlength = (ListLength) ann.getAnnotations()[0];
        AnnotatedParameterizedType temp = (AnnotatedParameterizedType) ann;
        AnnotatedType inside =  temp.getAnnotatedActualTypeArguments()[0];
        Type type = inside.getType();
        List<Object> ret = new LinkedList<>();
        if(type.equals(Integer.class)) {
            // todo fix here
            ret = flist(fill_Int(inside), listlength.min(), listlength.max());
        } else if (type.equals(String.class)) {
            // todo you need a new function for String
            ret = flist(string(inside),listlength.min(),listlength.max());
        } else if (type.equals(Object.class)) {
            // todo fix here
            ret = flist(forall(inside), listlength.min(), listlength.max());
        }
        return ret;
    }

//    private static List<List<Object>> traverseIn(AnnotatedType ann){
//        Type type = ann.getType();
//        ListLength listlength = (ListLength) ann.getAnnotations()[0];
//        if(type.equals(List.class)){
//
//        }
//    }

    public static HashMap<String, Object[]> quickCheckClass(String name) {
        Class c = null;
        try { c = Class.forName(name); } catch (ClassNotFoundException e) { }
        Method[] meths = c.getDeclaredMethods();
        HashMap<String, Object[]> ret = new HashMap<>();
        Map<String, List<Object[]>> input_dock = new HashMap<>();
        List<Method> prop_meths = new LinkedList<>();
        List<List<Object>> list = new LinkedList<>();
        for(Method meth : meths) {
            if(meth.isAnnotationPresent(Property.class)) {
                List<Object[]> saving = new ArrayList<>();
                prop_meths.add(meth);
                AnnotatedType[] anns = meth.getAnnotatedParameterTypes();
                for(AnnotatedType ann : anns){
                    Type type = ann.getType();
                    if(type.equals(Integer.class)) {
                        list.add(fill_Int(ann.getAnnotatedOwnerType()));
                    } else if (type.equals(String.class)) {
                        list.add(string(ann));
                    } else if (type.equals(Object.class)) {
                        list.add(forall(ann));
                    } else if (ann.isAnnotationPresent(ListLength.class)) {
                        list.add(targ(ann));
                    }
                }
                Object[] sub = new Object[anns.length];
                comboHelper(list,saving,sub,0);
                input_dock.put(meth.getName(),saving);
            }
        }
        prop_meths.sort(comparing(Method::getName));
        for(Method meth : prop_meths){
            try {
                Object obj = c.getConstructor().newInstance();
                for (int i = 0; i < input_dock.get(meth.getName()).size() && i < 100; ++i) {
                    Object[] temp = runFunctions(meth, input_dock.get(meth.getName()).get(i), obj);
                    if(temp != null){
                        ret.put(meth.getName(),temp);
                        break;
                    }
                    ret.put(meth.getName(),null);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return ret;
    }

    private static Object[] runFunctions(Method meth, Object[] per, Object obj) {
        try { Boolean b = true;
            if(meth.invoke(obj,per).getClass().equals(b.getClass()) && b.equals(meth.invoke(obj,per))){
                return per;
            }
        }
        catch (Exception e) {
            return per;
        }
        return null;
    }

}