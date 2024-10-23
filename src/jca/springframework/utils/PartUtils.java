package jca.springframework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import jca.springframework.mapping.FileMapping;
import jakarta.servlet.http.Part;
import jca.springframework.constants.FrameworkConstante;

public class PartUtils {
    public static byte[] getFileBytes(Part part) throws IOException {
        // On récupère la taille du fichier
        long fileSize = part.getSize();

        // Créer un tableau de bytes pour stocker le contenu du fichier
        byte[] fileBytes = new byte[(int) fileSize];

        // Lire le contenu du fichier à partir de l'InputStream
        try (InputStream inputStream = part.getInputStream()) {
            inputStream.read(fileBytes);
        }

        return fileBytes;
    }

    public static String extractPartName(Field attrribute){
        String attributeName = attrribute.getName();
        if (StringUtils.isConventionnalFilename(attributeName)) {
            return StringUtils.getSubstring(attributeName,FrameworkConstante.FILE_FILENAME_CONVENTION);
        }
        else if (StringUtils.isConventionnalBytes(attributeName)) {
            return StringUtils.getSubstring(attributeName, FrameworkConstante.FILE_BYTES_CONVENTION);
        }
        return null;

    }

    public static Object extractPartValueByAttrbute(Part part, Field attribute) throws IOException{
        if (StringUtils.isConventionnalFilename(attribute)) {
            return part.getName();
        }
        else if(StringUtils.isConventionnalBytes(attribute))
        {
            byte[] bytes = PartUtils.getFileBytes(part);
            return bytes;
        }
        else {
            return null;
        }
    }

    public static boolean isPartAttribute(Field attriibute){
        return attriibute.getType().equals(FileMapping.class);
    }
}
