import java.lang.reflect.Field;

public class Main3 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        System.out.println("Hello World!");
        String classFirst = "F";
        String classLast = "oo";
        String field = "bar";
        Class clazz = Main3.class.getClassLoader().loadClass(classFirst + classLast);
        Object o = clazz.newInstance();
        Field f = clazz.getDeclaredField(field);
        System.out.println(f.get(o));
    }
}
