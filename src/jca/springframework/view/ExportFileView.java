package jca.springframework.view;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExportFileView extends View{
    protected String fileName;
    protected String contentType;
    protected final byte[] contentData;

    public ExportFileView(String fileName, String contentType, byte[] data){
        super(null);
        setFileName(fileName);
        setContentType(contentType);
        this.contentData = data;
    }

    @Override
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(contentType);
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        writeFileContent(resp);
        resp.flushBuffer();
    }
    protected void writeFileContent(HttpServletResponse resp) throws IOException{
        resp.getOutputStream().write(contentData);
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContentData() {
        return contentData;
    }
}
