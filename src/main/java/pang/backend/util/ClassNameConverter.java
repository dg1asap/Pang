package pang.backend.util;

/**
 * Klasa narzędziowa zwraca prostą nazwę klasy
 */
public class ClassNameConverter {
    /**
     * zwraca prostą nazwę klasy
     * @param object objekt
     * @return zwraca prostą nazwę klasy obiektu
     */
    public static String getSimpleClassNameOf(Object object) {
        String fullName = object.getClass().getName();
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }
}
