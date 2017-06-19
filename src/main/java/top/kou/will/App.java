package top.kou.will;

public class App{


    public static void main( String[] args ) throws Exception {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());

    }
}
