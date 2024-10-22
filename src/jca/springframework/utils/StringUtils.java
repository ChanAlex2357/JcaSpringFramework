package jca.springframework.utils;

import java.lang.reflect.Field;

import jca.springframework.constants.FrameworkConstante;

public class StringUtils {
    public static String getSubstring(String fullString, String prefix) {
        // Vérifier si la chaîne fullString commence bien par le préfixe
        if (fullString.startsWith(prefix)) {
            // Retourner la partie de la chaîne après le préfixe
            return fullString.substring(prefix.length());
        }
        // Si le préfixe n'est pas trouvé, retourner la chaîne originale
        return fullString;
    }


    public static boolean isConventionnalFilename(Field attribute){
        return isConventionnalFilename(attribute.getName());
    }
    public static boolean isConventionnalFilename(String attributeName) {
        return attributeName.startsWith(FrameworkConstante.FILE_FILENAME_CONVENTION);
    }


    public static boolean isConventionnalBytes(Field attribute){
        return isConventionnalBytes(attribute.getName());
    } 
    public static boolean isConventionnalBytes(String attributeName){
        return attributeName.startsWith(FrameworkConstante.FILE_BYTES_CONVENTION);
    }


    public static String extractPartName(String attributeName){
        if (isConventionnalFilename(attributeName)) {
            return getSubstring(attributeName,FrameworkConstante.FILE_FILENAME_CONVENTION);
        }
        else if (isConventionnalBytes(attributeName)) {
            return getSubstring(attributeName, FrameworkConstante.FILE_BYTES_CONVENTION);
        }
        return null;

    }
}
