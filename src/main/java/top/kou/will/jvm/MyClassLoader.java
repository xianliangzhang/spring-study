package top.kou.will.jvm;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Created by ZXL on 2017/6/19.
 */
public class MyClassLoader extends ClassLoader {
    private String root = "d:\\";

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadClassData(name);
        if (bytes == null) {
            throw new ClassNotFoundException(name);
        } else {
            return defineClass(name, bytes, 0, bytes.length);
        }
    }

    private byte[] loadClassData(String className) {
        String fileName = root + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        try {
            InputStream ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Class clazz = new MyClassLoader().findClass("god.MyGod");
            System.out.println(clazz);

            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                System.out.println(constructor.getModifiers());
                System.out.println(constructor.getGenericParameterTypes());
                System.out.println(constructor.getName());
            }


            Object object = clazz.getConstructor(String.class, Integer.class).newInstance("xxx", 3);
            System.out.println(object.getClass().getClassLoader());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
