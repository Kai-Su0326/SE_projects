package jrails;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JRouter {

    Map<String,String> map = new HashMap<>();

    public void addRoute(String verb, String path, Class clazz, String method) {
        map.put(verb + "," + path, clazz.getName() + "#" + method);
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        return map.get(verb + "," + path);
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        String clsmeth = map.get(verb + "," + path);
        if (clsmeth == null)
            throw new UnsupportedOperationException("No such Route!---JRouter");

        String[] clazzandmethod = clsmeth.split("#");
        String clazz = clazzandmethod[0];
        String method = clazzandmethod[1];
        try {
            Html ret;
            Class<?> cls = Class.forName(clazz);
            Object obj = cls.getConstructor().newInstance();
            Method meth = cls.getMethod(method, Map.class);
            int modifier = meth.getModifiers();
            List<String> list = Arrays.asList(Modifier.toString(modifier).split(" "));
            if(list.contains("static")){
                ret = (Html) meth.invoke(null, params);
            } else {
                ret = (Html) meth.invoke(obj, params);
            }
            return ret;

        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }

    }
}

