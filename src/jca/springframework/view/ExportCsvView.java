package jca.springframework.view;

public class ExportCsvView extends ExportFileView{

    public ExportCsvView(String fileName, byte[] data) {
        super(fileName, "text/csv; charset=UTF-8", data);
    }

}
