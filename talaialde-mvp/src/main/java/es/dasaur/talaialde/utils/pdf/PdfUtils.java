package es.dasaur.talaialde.utils.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dasaur.talaialde.billing.freeline.FreeLine;
import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.billing.line.Line.LineType;
import es.dasaur.talaialde.constants.Formats;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class PdfUtils {

    private static final String TEMPLATES_PATH = "templates/";

    private static final String BILL_PATH = 
            TEMPLATES_PATH + "bill.jrxml";
    
    private static final String FREE_BILL_PATH = 
            TEMPLATES_PATH + "free-bill.jrxml";
    
    private static String getMessage(String code, Object... args) {
        return Messages.getMessage(code, args, Locale.getDefault());
    }
    
    public static byte[] getBill(Client client, Route route, Tractor tractor, 
            Date startDate, Date endDate, List<Line> lines, String vat,
            String totalWithCurrency, String logoPath, String billN, Date date) throws JRException {
        JRDataSource dataSource = new JRBeanCollectionDataSource(lines);
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("logo", logoPath);
        parameters.put("df", Formats.DATE);
        parameters.put("nf", Formats.MONEY);
        Map<LineType, String> lineTypeMap = new HashMap<>();
        Arrays.asList(LineType.values()).forEach(t -> lineTypeMap.put(
                t, getMessage("PROP_" + t.toString() + "_A")));
        parameters.put("types", lineTypeMap);
        parameters.put("vat", vat + " %");
        parameters.put("prop_date", getMessage(Messages.PROP_DATE));
        parameters.put("prop_client", getMessage(Messages.PROP_CLIENT));
        parameters.put("prop_route", getMessage(Messages.PROP_ROUTE));
        parameters.put("prop_plate", getMessage(Messages.PROP_PLATE));
        parameters.put("prop_product", getMessage(Messages.PROP_PRODUCT));
        parameters.put("prop_amount", getMessage(Messages.PROP_AMOUNT));
        parameters.put("prop_fee", getMessage(Messages.PROP_FEE));
        parameters.put("prop_value", getMessage(Messages.PROP_VALUE));
        parameters.put("prop_tin", getMessage(Messages.PROP_TIN));
        parameters.put("prop_address", getMessage(Messages.PROP_ADDRESS));
        parameters.put("prop_locality", getMessage(Messages.PROP_LOCALITY));
        parameters.put("prop_region", getMessage(Messages.PROP_REGION));
        parameters.put("prop_tractor", getMessage(Messages.PROP_TRACTOR));
        parameters.put("client", client);
        parameters.put("route", route);
        parameters.put("tractor", tractor);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        parameters.put("date", date);
        parameters.put("billN", billN);
        
        BigDecimal subtotal = lines.stream().map(Line::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        parameters.put("subtotal", 
                Formats.MONEY.format(subtotal.doubleValue()) + " €");
        BigDecimal vatValue = subtotal.multiply(new BigDecimal(vat))
                .divide(BigDecimal.valueOf(100L));
        parameters.put("vat_value", 
                Formats.MONEY.format(vatValue.doubleValue()) + " €");
        parameters.put("total",
                Formats.MONEY.format(subtotal.add(vatValue).doubleValue()) + " €");
        
        
        InputStream is = PdfUtils.class.getClassLoader().getResourceAsStream(BILL_PATH);
        JasperReport report = JasperCompileManager.compileReport(is);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(
                Arrays.asList(print)));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        exporter.exportReport();
        return os.toByteArray();
    }

    public static byte[] getFreeBill(Client client, Date startDate,
            Date endDate, List<FreeLine> lines, String vat,
            String totalWithCurrency, String logoPath, String billN,
            Date date) 
                    throws JRException {
        JRDataSource dataSource = new JRBeanCollectionDataSource(lines);
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("logo", logoPath);
        parameters.put("df", Formats.DATE);
        parameters.put("nf", Formats.MONEY);
        Map<LineType, String> lineTypeMap = new HashMap<>();
        Arrays.asList(LineType.values()).forEach(t -> lineTypeMap.put(
                t, getMessage("PROP_" + t.toString() + "_A")));
        parameters.put("types", lineTypeMap);
        parameters.put("vat", vat + " %");
        parameters.put("prop_date", getMessage(Messages.PROP_DATE));
        parameters.put("prop_client", getMessage(Messages.PROP_CLIENT));
        parameters.put("prop_product", getMessage(Messages.PROP_PRODUCT));
        parameters.put("prop_value", getMessage(Messages.PROP_VALUE));
        parameters.put("prop_tin", getMessage(Messages.PROP_TIN));
        parameters.put("prop_address", getMessage(Messages.PROP_ADDRESS));
        parameters.put("prop_locality", getMessage(Messages.PROP_LOCALITY));
        parameters.put("prop_region", getMessage(Messages.PROP_REGION));
        parameters.put("client", client);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        parameters.put("date", date);
        parameters.put("billN", billN);
        
        BigDecimal subtotal = lines.stream().map(FreeLine::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        parameters.put("subtotal", 
                Formats.MONEY.format(subtotal.doubleValue()) + " €");
        BigDecimal vatValue = subtotal.multiply(new BigDecimal(vat))
                .divide(BigDecimal.valueOf(100L));
        parameters.put("vat_value", 
                Formats.MONEY.format(vatValue.doubleValue()) + " €");
        parameters.put("total",
                Formats.MONEY.format(subtotal.add(vatValue).doubleValue()) + " €");
        
        
        InputStream is = PdfUtils.class.getClassLoader().getResourceAsStream(
                FREE_BILL_PATH);
        JasperReport report = JasperCompileManager.compileReport(is);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(
                Arrays.asList(print)));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        exporter.exportReport();
        return os.toByteArray();
    }

}
