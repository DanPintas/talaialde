package es.dasaur.talaialde.billing.bill;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import es.dasaur.annotations.View;
import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.constants.ConstantesRecursos;
import es.dasaur.talaialde.constants.Formats;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;
import es.dasaur.vaadin.mvp.VaadinAbstractView;
import es.dasaur.vaadin.utils.NotificationUtils;
import net.sf.jasperreports.engine.JRException;

@View
public class BillViewImpl 
        extends VaadinAbstractView<BillPresenter>
        implements BillView {

    private static final long serialVersionUID = 8347090806955125286L;

    private static final String FEE = "fee";
    private static final String EUROS = " €";

    private BeanItemContainer<Line> container;
    
    List<Object> visibleColumns = Arrays.asList(
            Line.PROP_DATE,
            Line.PROP_CLIENT,
            Line.PROP_ROUTE,
            Line.PROP_TRACTOR,
            Line.PROP_PRODUCT,
            Line.PROP_AMOUNT,
            FEE,
            Line.PROP_VALUE);

    private ComboBox client;
    private ComboBox route;
    private ComboBox tractor;
    private DateField startDate;
    private DateField endDate;

    private Table table;
    
    private TextField billN;

    private TextField vat;

    private TextField total;

    private BigDecimal linesSum;

    private Button btPdf;

    @Override
    public void init() {
        
        client = new ComboBox(getMessage(Messages.PROP_CLIENT));
        client.setWidth("100%");
        
        route = new ComboBox(getMessage(Messages.PROP_ROUTE));
        route.setWidth("100%");
        
        tractor = new ComboBox(getMessage(Messages.PROP_TRACTOR));
        tractor.setWidth("100%");
        
        startDate = new DateField(getMessage(Messages.PROP_DATE));
        startDate.setResolution(Resolution.DAY);
        startDate.setDateFormat(Formats.DATE_FORMAT);
        startDate.setConversionError(getMessage(Messages.ERROR_CONVERSION_DATE));
        startDate.setWidth("100%");
        startDate.setValue(getFirstDayOfLastMonth());

        endDate = new DateField();
        endDate.setResolution(Resolution.DAY);
        endDate.setDateFormat(Formats.DATE_FORMAT);
        endDate.setConversionError(getMessage(Messages.ERROR_CONVERSION_DATE));
        endDate.setWidth("100%");
        endDate.setValue(getLastDayOfLastMonth());

        container = new BeanItemContainer<>(Line.class);
        table = new Table();
        table.setContainerDataSource(container);
        table.setSortEnabled(false);
        table.setSizeFull();
        
        visibleColumns.forEach(c -> table.addGeneratedColumn(
                c, (t, i, p) -> generateColumn(i, p)));
        setTableColumns();
        table.setColumnHeaders(
                getMessage(Messages.PROP_DATE),
                getMessage(Messages.PROP_CLIENT),
                getMessage(Messages.PROP_ROUTE),
                getMessage(Messages.PROP_TRACTOR),
                getMessage(Messages.PROP_PRODUCT),
                getMessage(Messages.PROP_AMOUNT), 
                getMessage(Messages.PROP_FEE),
                getMessage(Messages.PROP_VALUE));
        table.setFooterVisible(true);
        
        Button btRefresh = new Button(FontAwesome.REFRESH);
        btRefresh.addClickListener(e -> refresh());
        
        Button btSearch = new Button(FontAwesome.SEARCH);
        btSearch.addClickListener(e -> search());
        
        billN = new TextField(getMessage(Messages.PROP_BILL_NUMBER));
        billN.setWidth("100%");
        billN.setNullRepresentation("");
        
        vat = new TextField(getMessage(Messages.PROP_VAT));
        vat.setWidth("100%");
        vat.setNullRepresentation("");
        vat.setConverter(BigDecimal.class);
        vat.setConversionError(getMessage(Messages.ERROR_CONVERSION_DOUBLE));
        vat.setConvertedValue(presenter().getDefaultVat());
        
        total = new TextField(getMessage(Messages.PROP_TOTAL));
        total.setWidth("100%");
        total.setNullRepresentation("");
        total.setReadOnly(true);
        
        btPdf = new Button(getMessage(Messages.PROMPT_PDF),
                FontAwesome.FILE_PDF_O);
        btPdf.addClickListener(e -> exportPdf());
        
        HorizontalLayout searchCriteria = new HorizontalLayout(
                client, route, tractor, startDate, endDate, btSearch, btRefresh);
        searchCriteria.setWidth("100%");
        searchCriteria.setSpacing(true);
        searchCriteria.setExpandRatio(client, 1);
        searchCriteria.setExpandRatio(route, 2);
        searchCriteria.setExpandRatio(tractor, 1);
        searchCriteria.setExpandRatio(startDate, 1);
        searchCriteria.setExpandRatio(endDate, 1);
        searchCriteria.forEach(c -> searchCriteria.setComponentAlignment(
                c, Alignment.BOTTOM_CENTER));
        
        HorizontalLayout actions = new HorizontalLayout(billN, vat, total, btPdf);
        actions.setWidth("100%");
        actions.setSpacing(true);
        actions.setExpandRatio(billN, 1);
        actions.setExpandRatio(vat, 1);
        actions.setExpandRatio(total, 1);
        actions.forEach(c -> actions.setComponentAlignment(
                c, Alignment.BOTTOM_CENTER));
        
        VerticalLayout view = new VerticalLayout(searchCriteria, table, actions);
        view.setSizeFull();
        view.setMargin(true);
        view.setSpacing(true);
        view.setExpandRatio(table, 1);
        
        setCompositionRoot(view);
        setSizeFull();
        
        vat.addValueChangeListener(e -> calculateBillTotal());
        
        refresh();
    }

    private Date getFirstDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,  - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    private Date getLastDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,  - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    private void setTableColumns() {
        table.setVisibleColumns(visibleColumns.toArray());
    }
    
    private Object generateColumn(Object itemId, Object propertyId) {
        Object value;
        Line line = (Line) itemId;
        switch(propertyId.toString()){
        case Line.PROP_DATE:
            value = Formats.DATE.format(line.getLineDate());
            break;
        case Line.PROP_CLIENT:
            value = line.getClient().getName();
            break;
        case Line.PROP_ROUTE:
            value = String.format("%s - %s", 
                    line.getRoute().getOrigin(), line.getRoute().getDestiny());
            break;
        case Line.PROP_TRACTOR:
            value = line.getTractor().getPlate();
            break;
        case Line.PROP_PRODUCT:
            value = line.getProduct();
            break;
        case Line.PROP_AMOUNT:
            value = line.getAmount() + " " + getMessage(
                    "PROP_"+line.getLineType().toString());
            break;
        case FEE:
            value = Formats.MONEY.format(getFee(line).doubleValue()) + EUROS;
            break;
        case Line.PROP_VALUE:
            value = Formats.MONEY.format(line.getValue().doubleValue()) + EUROS;
            break;
        default:
            value = null;
        }
        return value;
    }

    private BigDecimal getFee(Line line) {
        BigDecimal fee;
        switch(line.getLineType()){
        case HOUR:
            fee = line.getRoute().getHourFee();
            break;
        case TON:
            fee = line.getRoute().getTonFee();
            break;
        case TRIP:
            fee = line.getRoute().getTripFee();
            break;
        default:
            fee = null;
            break;
        }
        return fee;
    }

    private void refresh() {
        Client selectedClient = (Client) client.getValue();
        client.removeAllItems();
        presenter().getClients().forEach(this::addClient);
        client.select(selectedClient);
        
        Route selectedRoute = (Route) route.getValue();
        route.removeAllItems();
        presenter().getRoutes().forEach(this::addRoute);
        route.select(selectedRoute);
        
        Tractor selectedTractor = (Tractor) tractor.getValue();
        tractor.removeAllItems();
        presenter().getTractors().forEach(this::addTractor);
        tractor.select(selectedTractor);
    }

    private void search() {
        if (!startDate.isValid() || !endDate.isValid()) {
            NotificationUtils.showWarning(getMessage(Messages.ERROR_CONVERSION_DATE));
        } else if (startDate.getValue() != null && endDate.getValue() != null) {
            List<Line> lines = presenter().getLines((Client) client.getValue(), 
                    (Route) route.getValue(), (Tractor) tractor.getValue(), 
                    startDate.getValue(), endDate.getValue());
            lines.forEach(l -> l.setValue(l.getAmount().multiply(getFee(l))));
            refreshContainer(lines);
            calculateLinesSum(lines);
            calculateBillTotal();
        } else {
            NotificationUtils.showWarning(getMessage(Messages.ERROR_REQUIRED_DATE));
        }
    }

    private void refreshContainer(List<Line> lines) {
        container = new BeanItemContainer<>(Line.class);
        container.addAll(lines);
        table.setContainerDataSource(container);
        setTableColumns();
    }

    private void calculateLinesSum(List<Line> lines) {
        linesSum = lines.stream().map(Line::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        table.setColumnFooter(Line.PROP_VALUE, Formats.MONEY.format(
                linesSum.doubleValue()) + EUROS);
    }

    private void calculateBillTotal() {
        total.setReadOnly(false);
        BigDecimal vatValue = (BigDecimal) vat.getConvertedValue();
        BigDecimal vatFraction = vatValue.divide(BigDecimal.valueOf(100));
        BigDecimal vatMultiplier = BigDecimal.ONE.add(vatFraction);
        BigDecimal totalValue = linesSum.multiply(vatMultiplier);
        total.setValue(Formats.MONEY.format(totalValue.doubleValue())+ EUROS);
        total.setReadOnly(true);
    }

    @SuppressWarnings("deprecation")
    private void exportPdf() {
        String rutaLogo = getClass().getClassLoader()
                .getResource(ConstantesRecursos.RUTA_LOGO)
                .toString();
        try(ByteArrayInputStream bais = new ByteArrayInputStream(
                presenter().getPdf(
                        (Client) client.getValue(),
                        (Route) route.getValue(),
                        (Tractor) tractor.getValue(),
                        startDate.getValue(),
                        endDate.getValue(),
                        container.getItemIds(), 
                        vat.getValue(), 
                        total.getValue(), 
                        rutaLogo,
                        billN.getValue()))) {
            if(btPdf.getExtensions().isEmpty()){
                StreamResource res = new StreamResource(() -> bais, "Factura.pdf");
                res.setMIMEType("application/pdf");
                res.getStream().setParameter("Content-Disposition", 
                        "attachment; filename=" + "Factura.pdf");
                res.setCacheTime(1000);
                UI.getCurrent().getPage().open(res, "_blank", false);
            } else {
                btPdf.getExtensions().forEach(btPdf::removeExtension);
            }
        } catch (IOException e) {
            // do nothing
        } catch (JRException e) {
            NotificationUtils.showError(getMessage(Messages.ERROR_PDF_EXPORT));
            e.printStackTrace();
        }
    }

    private void addClient(Client item){
        client.addItem(item);
        client.setItemCaption(item, item.getName());
    }

    private void addRoute(Route item){
        route.addItem(item);
        route.setItemCaption(item, String.format(
                "%s - %s", item.getOrigin(), item.getDestiny()));
    }

    private void addTractor(Tractor item){
        tractor.addItem(item);
        tractor.setItemCaption(item, item.getPlate());
    }

}
