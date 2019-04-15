import java.net.URL;

public class Main2 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String resourceName = "application.properties";
        URL resource = Main2.class.getClassLoader().getResource(resourceName);
        System.out.println("URL for " + resourceName + ( resource == null ? " is null" : " is not null"));
    }
}
