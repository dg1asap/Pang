package pang.backend.util;

public class ClassNameConverter {
    public static String getSimpleClassNameOf(Object object) {
        String fullName = object.getClass().getName();
        System.out.println(fullName.substring(fullName.lastIndexOf('.') + 1));
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }
}
