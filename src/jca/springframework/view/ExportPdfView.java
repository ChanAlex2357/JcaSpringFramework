package jca.springframework.view;

public class ExportPdfView extends ExportFileView{

    public ExportPdfView(String fileName, byte[] data) {
        super(fileName, "application/pdf", data);
    }

}
