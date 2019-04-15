import java.lang.reflect.Field;

public class Main4 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InterruptedException {
        System.out.println("Hello World!");
        String classFirst = "F";
        String classLast = "oo";
        String field = "bar";
        Class clazz = Main4.class.getClassLoader().loadClass(classFirst + classLast);
        Object o = clazz.newInstance();
        Field f = clazz.getDeclaredField(field);
        System.out.println(f.get(o));
        System.out.println("start");
        while (true) {
            Thread.sleep(1000);
        }
    }
}
