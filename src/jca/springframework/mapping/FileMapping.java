package jca.springframework.mapping;

import java.io.IOException;

import jakarta.servlet.http.Part;
import jca.springframework.utils.PartUtils;

public class FileMapping {
    private String filename;
    private byte[] bytes;
    
    public FileMapping(Part part) throws IOException{
        setFilename(part.getName());
        setBytes(PartUtils.getFileBytes(part));
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public byte[] getBytes() {
        return bytes;
    }
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    
}
