package es.danpintas.talaialde.billing.bill;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.v7.data.util.BeanItemContainer;
import com.vaadin.v7.shared.ui.combobox.FilteringMode;
import com.vaadin.v7.shared.ui.datefield.Resolution;
import com.vaadin.v7.ui.ComboBox;
import com.vaadin.v7.ui.DateField;
import com.vaadin.v7.ui.Table;
import com.vaadin.v7.ui.TextField;
import es.danpintas.annotations.View;
import es.danpintas.talaialde.billing.line.JpaLine;
import es.danpintas.talaialde.constants.ConstantesRecursos;
import es.danpintas.talaialde.constants.Formats;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import es.danpintas.vaadin.components.ConfirmWindow;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import es.danpintas.vaadin.utils.NotificationUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRException;

@View
public class BillViewImpl
    extends VaadinAbstractView<BillPresenter>
    implements BillView {

  private static final long serialVersionUID = 8347090806955125286L;

  private static final String FEE = "fee";

  private static final String ACTIONS = "actions";

  private static final String EUROS = " €";

  private BeanItemContainer<JpaLine> container;

  private static final List<Object> VISIBLE_COLUMNS = Arrays.asList(
      JpaLine.PROP_CHECKED,
      JpaLine.PROP_DATE,
      JpaLine.PROP_CLIENT,
      JpaLine.PROP_ROUTE,
      JpaLine.PROP_TRACTOR,
      JpaLine.PROP_PRODUCT,
      JpaLine.PROP_AMOUNT,
      FEE,
      JpaLine.PROP_VALUE,
      ACTIONS);

  private ComboBox client;

  private ComboBox route;

  private ComboBox tractor;

  private DateField startDate;

  private DateField endDate;

  private Table table;

  private TextField billN;

  private DateField date;

  private TextField vat;

  private TextField total;

  private BigDecimal linesSum;

  private Button btPdf;

  @Override
  public void init() {

    client = new ComboBox(getMessage(Messages.PROP_CLIENT));
    client.setFilteringMode(FilteringMode.CONTAINS);
    client.setWidth("100%");

    route = new ComboBox(getMessage(Messages.PROP_ROUTE));
    route.setFilteringMode(FilteringMode.CONTAINS);
    route.setWidth("100%");

    tractor = new ComboBox(getMessage(Messages.PROP_TRACTOR));
    tractor.setFilteringMode(FilteringMode.CONTAINS);
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

    container = new BeanItemContainer<>(JpaLine.class);
    table = new Table();
    table.setContainerDataSource(container);
    table.setSortEnabled(false);
    table.setSizeFull();

    VISIBLE_COLUMNS.forEach(c -> table.addGeneratedColumn(
        c, (t, i, p) -> generateColumn(i, p)));
    setTableColumns();
    table.setColumnHeaders(
        "",
        getMessage(Messages.PROP_DATE),
        getMessage(Messages.PROP_CLIENT),
        getMessage(Messages.PROP_ROUTE),
        getMessage(Messages.PROP_TRACTOR),
        getMessage(Messages.PROP_PRODUCT),
        getMessage(Messages.PROP_AMOUNT),
        getMessage(Messages.PROP_FEE),
        getMessage(Messages.PROP_VALUE),
        "");
    table.setFooterVisible(true);

    Button btRefresh = new Button(FontAwesome.REFRESH);
    btRefresh.addClickListener(e -> refresh());

    Button btSearch = new Button(FontAwesome.SEARCH);
    btSearch.addClickListener(e -> search());

    billN = new TextField(getMessage(Messages.PROP_BILL_NUMBER));
    billN.setWidth("100%");
    billN.setNullRepresentation("");

    date = new DateField(getMessage(Messages.PROP_DATE));
    date.setResolution(Resolution.DAY);
    date.setDateFormat(Formats.DATE_FORMAT);
    date.setConversionError(getMessage(Messages.ERROR_CONVERSION_DATE));
    date.setWidth("100%");
    date.setValue(new Date());

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

    HorizontalLayout actions = new HorizontalLayout(billN, date, vat, total, btPdf);
    actions.setWidth("100%");
    actions.setSpacing(true);
    actions.setExpandRatio(billN, 2);
    actions.setExpandRatio(date, 1);
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
    cal.add(Calendar.MONTH, -1);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    return cal.getTime();
  }

  private Date getLastDayOfLastMonth() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    return cal.getTime();
  }

  private void setTableColumns() {
    table.setVisibleColumns(VISIBLE_COLUMNS.toArray());
    table.setColumnWidth(JpaLine.PROP_CHECKED, 40);
    table.setColumnWidth(JpaLine.PROP_CHECKED, 100);
    table.setColumnAlignment(FEE, Table.Align.RIGHT);
    table.setColumnAlignment(JpaLine.PROP_VALUE, Table.Align.RIGHT);
  }

  private Object generateColumn(Object itemId, Object propertyId) {
    Object value;
    JpaLine line = (JpaLine) itemId;
    switch (propertyId.toString()) {
      case JpaLine.PROP_CHECKED:
        value = createCheck(line);
        break;
      case JpaLine.PROP_DATE:
        value = Formats.DATE.format(line.getLineDate());
        break;
      case JpaLine.PROP_CLIENT:
        value = line.getClient().getName();
        break;
      case JpaLine.PROP_ROUTE:
        value = String.format("%s - %s",
            line.getRoute().getOrigin(), line.getRoute().getDestiny());
        break;
      case JpaLine.PROP_TRACTOR:
        value = line.getTractor().getPlate();
        break;
      case JpaLine.PROP_PRODUCT:
        value = line.getProduct();
        break;
      case JpaLine.PROP_AMOUNT:
        value = line.getAmount() + " " + getMessage(
            "PROP_" + line.getLineType().toString());
        break;
      case FEE:
        value = getFee(line) == null ? null :
            Formats.MONEY.format(getFee(line).doubleValue()) + EUROS;
        break;
      case JpaLine.PROP_VALUE:
        value = Formats.MONEY.format(line.getValue().doubleValue()) + EUROS;
        break;
      case ACTIONS:
        value = getActions(line);
        break;
      default:
        value = null;
    }
    return value;
  }

  private Object createCheck(JpaLine line) {
    CheckBox check = new CheckBox();
    check.setValue(true);
    check.addValueChangeListener(e -> setChecked(line, check.getValue()));
    return check;
  }

  private void setChecked(JpaLine line, Boolean checked) {
    line.setChecked(checked);
    calculateLinesSum(container.getItemIds());
    calculateBillTotal();
  }

  private BigDecimal getFee(JpaLine line) {
    BigDecimal fee;
    switch (line.getLineType()) {
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

  private Object getActions(JpaLine line) {
    Button edit = new Button(FontAwesome.EDIT);
    edit.addClickListener(e -> openEditor(line));

    Button remove = new Button(FontAwesome.TRASH);
    remove.addClickListener(e -> confirmDelete(line));

    HorizontalLayout actions = new HorizontalLayout(edit, remove);
    return actions;
  }

  private void openEditor(JpaLine line) {
    presenter().openEditor(line);
  }

  private void confirmDelete(JpaLine line) {
    ConfirmWindow.show(
        getMessage(Messages.PROMPT_DELETE),
        getMessage(Messages.PROMPT_DELETE_DESC),
        getMessage(Messages.PROMPT_YES),
        getMessage(Messages.PROMPT_NO),
        () -> delete(line));
  }

  private void delete(JpaLine line) {
    presenter().deleteLine(line);
    search();
  }

  private void refresh() {
    JpaClient selectedClient = (JpaClient) client.getValue();
    client.removeAllItems();
    presenter().getClients().forEach(this::addClient);
    client.select(selectedClient);

    JpaRoute selectedRoute = (JpaRoute) route.getValue();
    route.removeAllItems();
    presenter().getRoutes().forEach(this::addRoute);
    route.select(selectedRoute);

    JpaTractor selectedTractor = (JpaTractor) tractor.getValue();
    tractor.removeAllItems();
    presenter().getTractors().forEach(this::addTractor);
    tractor.select(selectedTractor);

    search();
  }

  private void search() {
    if (!startDate.isValid() || !endDate.isValid()) {
      NotificationUtils.showWarning(getMessage(Messages.ERROR_CONVERSION_DATE));
    } else if (startDate.getValue() != null && endDate.getValue() != null) {
      List<JpaLine> lines = presenter().getLines((JpaClient) client.getValue(),
          (JpaRoute) route.getValue(), (JpaTractor) tractor.getValue(),
          startDate.getValue(), endDate.getValue());
      lines.forEach(l -> l.setValue(l.getAmount().multiply(
          Optional.ofNullable(getFee(l)).orElse(BigDecimal.ZERO))));
      refreshContainer(lines);
      calculateLinesSum(lines);
      calculateBillTotal();
    } else {
      NotificationUtils.showWarning(getMessage(Messages.ERROR_REQUIRED_DATE));
    }
  }

  private void refreshContainer(List<JpaLine> lines) {
    container = new BeanItemContainer<>(JpaLine.class);
    container.addAll(lines);
    table.setContainerDataSource(container);
    setTableColumns();
  }

  private void calculateLinesSum(List<JpaLine> lines) {
    linesSum = lines.stream().filter(JpaLine::isChecked).map(JpaLine::getValue)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    table.setColumnFooter(JpaLine.PROP_VALUE, Formats.MONEY.format(
        linesSum.doubleValue()) + EUROS);
  }

  private void calculateBillTotal() {
    total.setReadOnly(false);
    BigDecimal vatValue = (BigDecimal) vat.getConvertedValue();
    BigDecimal vatFraction = vatValue.divide(BigDecimal.valueOf(100));
    BigDecimal vatMultiplier = BigDecimal.ONE.add(vatFraction);
    BigDecimal totalValue = linesSum.multiply(vatMultiplier);
    total.setValue(Formats.MONEY.format(totalValue.doubleValue()) + EUROS);
    total.setReadOnly(true);
  }

  @SuppressWarnings("deprecation")
  private void exportPdf() {
    String rutaLogo = getClass().getClassLoader()
        .getResource(ConstantesRecursos.RUTA_LOGO)
        .toString();
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        presenter().getPdf(
            (JpaClient) client.getValue(),
            (JpaRoute) route.getValue(),
            (JpaTractor) tractor.getValue(),
            startDate.getValue(),
            endDate.getValue(),
            container.getItemIds().stream()
                .filter(JpaLine::isChecked)
                .collect(Collectors.toList()),
            vat.getValue(),
            total.getValue(),
            rutaLogo,
            billN.getValue(),
            date.getValue()))) {
      if (btPdf.getExtensions().isEmpty()) {
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

  private void addClient(JpaClient item) {
    client.addItem(item);
    client.setItemCaption(item, item.getName());
  }

  private void addRoute(JpaRoute item) {
    route.addItem(item);
    route.setItemCaption(item, String.format(
        "%s - %s", item.getOrigin(), item.getDestiny()));
  }

  private void addTractor(JpaTractor item) {
    tractor.addItem(item);
    tractor.setItemCaption(item, item.getPlate());
  }

}
