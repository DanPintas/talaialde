//package es.dasaur.vaadin.utils;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//
//import com.vaadin.addon.tableexport.CsvExport;
//import com.vaadin.addon.tableexport.ExcelExport;
//import com.vaadin.ui.Table;
//
///**
// * Útiles para exportación de datos
// * 
// * @author dsalas
// *
// */
//public class ExportUtils {
//    
//    private static final String XLS_EXTENSION = ".xls";
//    private static final String CSV_EXTENSION = ".csv";
//    
//    public enum ExportFormat{ XLS, CSV }
//
//    private ExportUtils() {
//        // utils class
//    }
//    
//    public static void exportAsExcel(Table table, String fileName, 
//            String sheetName) {
//        exportTable(table, fileName, sheetName, ExportFormat.XLS);
//    }
//    
//    public static void exportTable(Table table, String fileName, 
//            String sheetName, ExportFormat format) {
//        
//        Exporter exporter;
//        String ext;
//        switch(format){
//        case CSV:
//            exporter = new CsvExporter(table, sheetName);
//            ext = CSV_EXTENSION;
//            break;
//        case XLS:
//        default:
//            exporter = new ExcelExporter(table, sheetName);
//            ext = XLS_EXTENSION;
//            break;
//        }
//        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
//        String file = String.format("%s-%s%s",fileName, df.format(new Date()), ext);
//        exporter.setExportFileName(file);
//        exporter.export();
//    }
//    
//    public interface Exporter {
//        void setExportFileName(String exportFileName);
//        void export();
//    }
//
//    private static class ExcelExporter extends ExcelExport 
//            implements Exporter {
//
//        private static final long serialVersionUID = -336968463160924558L;
//
//        public ExcelExporter(Table table, String sheetName) {
//            super(table, sheetName);
//            CellStyle style;
//            Font font;
//
//            // Estilo de la fila de títulos: celdas A1, B1, etc.
//            style = this.getColumnHeaderStyle();
//            style.setFillForegroundColor(HSSFColor.RED.index);
//            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//            font = getWorkbook().createFont();
//            font.setFontHeightInPoints((short) 10);
//            font.setFontName(HSSFFont.FONT_ARIAL);
//            font.setColor(HSSFColor.WHITE.index);
//            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//            style.setFont(font);
//            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//            style.setLeftBorderColor(HSSFColor.WHITE.index);
//            style.setRightBorderColor(HSSFColor.WHITE.index);
//            style.setTopBorderColor(HSSFColor.WHITE.index);
//            style.setBottomBorderColor(HSSFColor.WHITE.index);
//
//            // Estilo de Doubles, aplicado también al resto de elementos.
//            style = this.getDoubleDataStyle();
//            style.setFillForegroundColor(HSSFColor.WHITE.index);
//            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//            font = getWorkbook().createFont();
//            font.setFontHeightInPoints((short) 10);
//            font.setFontName(HSSFFont.FONT_ARIAL);
//            font.setColor(HSSFColor.BLACK.index);
//            style.setFont(font);
//            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//            style.setLeftBorderColor(HSSFColor.BLACK.index);
//            style.setRightBorderColor(HSSFColor.BLACK.index);
//            style.setTopBorderColor(HSSFColor.BLACK.index);
//            style.setBottomBorderColor(HSSFColor.BLACK.index);
//
//            final CellStyle newStyle = getWorkbook().createCellStyle();
//            newStyle.cloneStyleFrom(style);
//            this.setRowHeaderStyle(newStyle);
//
//            excludeCollapsedColumns();
//            setDisplayTotals(false);
//        }
//
//    }
//    
//    private static class CsvExporter extends CsvExport 
//            implements Exporter {
//
//        private static final long serialVersionUID = -336968463160924578L;
//
//        public CsvExporter(Table table, String sheetName) {
//            super(table, sheetName);
//        }
//
//    }
//
//}
