package jca.springframework.utils;

import java.lang.reflect.Field;
import java.util.Base64;

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

    public static boolean isConventionnalPartAttribute(Field attribute){
        return isConventionnalFilename(attribute) || isConventionnalBytes(attribute);
    }
    public static boolean isConventionnalPartAttribute(String attribute){
        return isConventionnalFilename(attribute) || isConventionnalBytes(attribute);
    }

    public static String encode(byte[] stringbytes){
        return Base64.getEncoder().encodeToString(stringbytes);
    }

    public static String getControllerUrl(String fullurl) {
        String url = "";
        try {
            String[] parts = fullurl.split("/");
            url += parts[4];
            for (int i = 5; i < parts.length; i++) {
                url+= "/"+parts[i];
            }
        } catch (Exception e) {
            url += "index";
        }
        return url;
    }
}
