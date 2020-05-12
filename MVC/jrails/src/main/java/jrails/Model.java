package jrails;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Model {
    int uuid = 0;
    Map<String,Object> map;

    private void updatemap(){
        map = new TreeMap<>();
        Field[] fields = this.getClass().getFields();
        for(Field f : fields){
            if(f.isAnnotationPresent(Column.class)){
                try {
                    map.put(f.getName(),f.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private StringBuilder stringGenerate(){
        StringBuilder string = new StringBuilder(Integer.toString(uuid));
        for(String str : map.keySet()){
            if(map.get(str) == null){
                string.append("%&_" + "null");
            } else {
                string.append("%&_").append(map.get(str).toString());
            }
        }
        return string;
    }

    private void setUuid(){
        Date date = new Date();
        int random = (int)((Math.random() + 1)*100);
        this.uuid = (int)date.getTime() + random;
    }

    private StringBuilder serializeDBwithoutThis(String name) throws IOException {
        StringBuilder temp = new StringBuilder();
        try(Scanner file = new Scanner(new FileReader(name))) {
            while (file.hasNext()) {
                String line = file.nextLine();
                String[] s = line.split("%&_");
                if (Integer.parseInt(s[0]) == uuid) { continue; }
                temp.append(line);
                if (file.hasNext()) {
                    temp.append("%#");
                }
            }
        }
        return temp;
    }

    public void save() {
        String name = "db" + this.getClass().getSimpleName() + ".txt";
        File database = new File(name);
        updatemap();
        try {
            database.createNewFile();
            String string = "";
            if(uuid == 0){
                setUuid();
                string = stringGenerate().toString();
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(name,true));
                printWriter.println(string);
                printWriter.close();
            } else {
                string = stringGenerate().toString();
                StringBuilder temp = serializeDBwithoutThis(name);
                PrintWriter writer = new PrintWriter(new FileOutputStream(name,false));
                if(temp.length() != 0) { temp.append("%#"); }
                temp.append(string);
                String[] content = temp.toString().split("%#");
                for(String str : content){
                    writer.println(str);
                }
                writer.close();
            }
            }
         catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int id() {
        return this.uuid;
    }

    private static <T extends Model> T deserialize(Class<T> c, String[] s) throws Exception {
            T ret = c.getConstructor().newInstance();
            Field[] fields = ret.getClass().getFields();
            Comparator<Field> byName = Comparator.comparing(Field::getName);
            Arrays.sort(fields, byName);
            for(int i = 1; i < s.length;i++) {
                if (fields[i - 1].getType().equals(int.class)) {
                    if (s[i].equals("null") || s[i].isEmpty()) {
                        fields[i - 1].set(ret, 0);
                    } else {
                        fields[i - 1].set(ret, Integer.parseInt(s[i]));
                    }
                } else if (fields[i - 1].getType().equals(String.class)) {
                    if (s[i].equals("null")) {
                        fields[i - 1].set(ret, null);
                    } else {
                        fields[i - 1].set(ret, s[i]);
                    }
                } else if (fields[i - 1].getType().equals(boolean.class)) {
                    if (s[i].equals("null") || s[i].isEmpty()) {
                        fields[i - 1].set(ret, false);
                    } else {
                        fields[i - 1].set(ret, Boolean.parseBoolean(s[i]));
                    }
                } else {
                    throw new UnsupportedOperationException("one of the field's type did not match!");
                }
            }
        return ret;
    }

    public static <T extends Model> T find(Class<T> c, int id) {
        String filename = "db" + c.getSimpleName() + ".txt";
        try(Scanner file = new Scanner(new FileReader(filename))){
            while (file.hasNext()){
                String line = file.nextLine();
                String[] s = line.split("%&_");
                if(Integer.parseInt(s[0]) == id){
                    T ret = deserialize(c,s);
                    ret.uuid = id;
                    return ret;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Model> List<T> all(Class<T> c) {
        // Returns a List<element type>
        String filename = "db" + c.getSimpleName() + ".txt";
        List<T> list = new ArrayList<>();
        try(Scanner file = new Scanner(new FileReader(filename))){
            while (file.hasNext()){
                String line = file.nextLine();
                String[] s = line.split("%&_");
                T temp = deserialize(c,s);
                temp.uuid = Integer.parseInt(s[0]);
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void destroy() {
        if(this.uuid == 0){
            throw new UnsupportedOperationException("Nothing to destroy");
        }
        String filename = "db" + this.getClass().getSimpleName() + ".txt";
        try {
            String content = serializeDBwithoutThis(filename).toString();
            if(!content.isEmpty()){
                PrintWriter writer = new PrintWriter(filename);
                String[] lines = content.split("%#");
                for(String str : lines){
                    writer.println(str);
                }
                writer.close();
            } else {
                File file = new File(filename);
                file.delete();
                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reset() {
        File rep = new File(System.getProperty("user.dir"));
        FileFilter dbfilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".txt") && pathname.getName().startsWith("db");
            }
        };
        File[] files = rep.listFiles(dbfilter);
        //System.out.println(files[0].getName());
        if(files == null) { throw new UnsupportedOperationException("No db to be reset"); } //todo:delete!!
        for(File f : files){
            String name = f.getName();
            if(f.delete()) {
                File empty = new File(name);
                try {
                    empty.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new UnsupportedOperationException("file fail to be deleted");
            }
        }
    }
}