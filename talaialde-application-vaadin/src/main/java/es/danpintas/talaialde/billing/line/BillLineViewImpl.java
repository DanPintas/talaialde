package es.danpintas.talaialde.billing.line;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.v7.data.fieldgroup.BeanFieldGroup;
import com.vaadin.v7.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.v7.shared.ui.combobox.FilteringMode;
import com.vaadin.v7.ui.AbstractField;
import com.vaadin.v7.ui.ComboBox;
import com.vaadin.v7.ui.DateField;
import com.vaadin.v7.ui.TextArea;
import com.vaadin.v7.ui.TextField;
import es.danpintas.annotations.View;
import es.danpintas.talaialde.billing.line.JpaLine.LineType;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import es.danpintas.vaadin.utils.NotificationUtils;
import java.math.BigDecimal;

@View
public class BillLineViewImpl extends VaadinAbstractView<BillLinePresenter>
    implements BillLineView {

  private static final long serialVersionUID = 8347090806955125286L;

  private BeanFieldGroup<JpaLine> binder;

  private ComboBox client;

  private ComboBox route;

  private ComboBox tractor;

  private ComboBox lineType;

  private TextField value;

  private TextField amount;

  @Override
  public void init() {

    binder = new BeanFieldGroup<>(JpaLine.class);

    client = new ComboBox(getMessage(Messages.PROP_CLIENT));
    client.setWidth("100%");
    client.setRequired(true);
    client.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    client.setFilteringMode(FilteringMode.CONTAINS);
    binder.bind(client, JpaLine.PROP_CLIENT);

    route = new ComboBox(getMessage(Messages.PROP_ROUTE));
    route.setWidth("100%");
    route.setRequired(true);
    route.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    route.addValueChangeListener(e -> refreshRoute());
    route.setFilteringMode(FilteringMode.CONTAINS);
    binder.bind(route, JpaLine.PROP_ROUTE);

    tractor = new ComboBox(getMessage(Messages.PROP_TRACTOR));
    tractor.setWidth("100%");
    tractor.setRequired(true);
    tractor.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    tractor.setFilteringMode(FilteringMode.CONTAINS);
    binder.bind(tractor, JpaLine.PROP_TRACTOR);

    DateField date = new DateField(getMessage(Messages.PROP_DATE));
    date.setWidth("100%");
    date.setRequired(true);
    date.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    binder.bind(date, JpaLine.PROP_DATE);

    amount = new TextField(getMessage(Messages.PROP_AMOUNT));
    amount.setWidth("100%");
    amount.setRequired(true);
    amount.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    amount.setNullRepresentation("");
    amount.addValueChangeListener(e -> calculateValue());
    amount.setConverter(BigDecimal.class);
    amount.setConversionError(getMessage(Messages.ERROR_CONVERSION_DOUBLE));
    binder.bind(amount, JpaLine.PROP_AMOUNT);

    lineType = new ComboBox(getMessage(Messages.PROP_LINE_TYPE));
    lineType.setWidth("100%");
    lineType.setRequired(true);
    lineType.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    lineType.addValueChangeListener(e -> calculateValue());
    binder.bind(lineType, JpaLine.PROP_LINE_TYPE);

    value = new TextField(getMessage(Messages.PROP_VALUE));
    value.setWidth("100%");
    value.setRequired(true);
    value.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    value.setNullRepresentation("");
    value.setConverter(BigDecimal.class);
    value.setConversionError(getMessage(Messages.ERROR_CONVERSION_DOUBLE));
    binder.bind(value, JpaLine.PROP_VALUE);

    TextField product = new TextField(getMessage(Messages.PROP_PRODUCT));
    product.setWidth("100%");
    product.setRequired(true);
    product.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
    product.setNullRepresentation("");
    binder.bind(product, JpaLine.PROP_PRODUCT);

    TextArea specifics = new TextArea(getMessage(Messages.PROP_SPECIFICS));
    specifics.setWidth("100%");
    specifics.setNullRepresentation("");
    binder.bind(specifics, JpaLine.PROP_SPECIFICS);

    clear();

    FormLayout col1 = new FormLayout(client, date);
    col1.setMargin(false);
    col1.setWidth("100%");
    FormLayout col2 = new FormLayout(route, amount);
    col2.setMargin(false);
    col2.setWidth("100%");
    FormLayout col3 = new FormLayout(tractor, lineType);
    col3.setMargin(false);
    col3.setWidth("100%");
    HorizontalLayout requiredFields = new HorizontalLayout(col1, col2, col3);
    requiredFields.setSpacing(true);
    requiredFields.setWidth("100%");
    requiredFields.setExpandRatio(col1, 1);
    requiredFields.setExpandRatio(col2, 2);
    requiredFields.setExpandRatio(col3, 1);

    VerticalLayout fields = new VerticalLayout(requiredFields, value,
        product, specifics);
    fields.setWidth("100%");
    fields.setMargin(true);
    fields.setSpacing(true);
    Panel panelFields = new Panel(fields);
    panelFields.setSizeFull();

    Button btRefresh = new Button(getMessage(Messages.PROMPT_REFRESH),
        FontAwesome.REFRESH);
    btRefresh.addClickListener(e -> refresh());

    Button btSave = new Button(getMessage(Messages.PROMPT_SAVE),
        FontAwesome.SAVE);
    btSave.addClickListener(e -> save());

    VerticalLayout view = new VerticalLayout(btRefresh, panelFields,
        btSave);
    view.setSizeFull();
    view.setMargin(true);
    view.setSpacing(true);
    view.setExpandRatio(panelFields, 1);
    view.setComponentAlignment(btRefresh, Alignment.MIDDLE_RIGHT);
    view.setComponentAlignment(btSave, Alignment.MIDDLE_RIGHT);
    view.addStyleName("form");

    VerticalLayout viewWrapper = new VerticalLayout(view);
    viewWrapper.setMargin(false);
    viewWrapper.setSizeFull();
    viewWrapper.setComponentAlignment(view, Alignment.TOP_CENTER);

    setCompositionRoot(viewWrapper);
    setSizeFull();

    refresh();
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
  }

  private void refreshRoute() {
    LineType type = (LineType) lineType.getValue();
    lineType.removeAllItems();
    JpaRoute currentRoute = (JpaRoute) route.getValue();
    if (currentRoute != null) {
      if (currentRoute.getHourFee() != null) {
        addLineType(LineType.HOUR);
      }
      if (currentRoute.getTonFee() != null) {
        addLineType(LineType.TON);
      }
      if (currentRoute.getTripFee() != null) {
        addLineType(LineType.TRIP);
      }
    }
    lineType.select(type);
  }

  private void calculateValue() {
    BigDecimal a = (BigDecimal) amount.getConvertedValue();
    JpaRoute r = (JpaRoute) route.getValue();
    LineType t = (LineType) lineType.getValue();
    BigDecimal val = a == null || t == null ? null
        : calculateValueInternal(a, r, t);
    value.setReadOnly(false);
    value.setConvertedValue(val);
    value.setReadOnly(true);
  }

  private BigDecimal calculateValueInternal(BigDecimal a, JpaRoute r,
      LineType t) {
    BigDecimal fee;
    switch (t) {
      case HOUR:
        fee = r.getHourFee();
        break;
      case TON:
        fee = r.getTonFee();
        break;
      case TRIP:
        fee = r.getTripFee();
        break;
      default:
        fee = null;
        break;
    }
    return a.multiply(fee);
  }

  private void save() {
    try {
      binder.getFields().forEach(
          f -> ((AbstractField<?>) f).setValidationVisible(true));
      binder.commit();
      presenter().saveLine(binder.getItemDataSource().getBean());
      NotificationUtils
          .showMessage(getMessage(Messages.PROMPT_SAVED_DATA));
      clear();
    } catch (CommitException e) {
      NotificationUtils.showWarning(getMessage(Messages.ERROR_FORM));
    }
  }

  private void clear() {
    binder.setItemDataSource(new JpaLine());
    binder.getFields().forEach(
        f -> ((AbstractField<?>) f).setValidationVisible(false));

    value.setReadOnly(true);
  }

  private void addClient(JpaClient item) {
    client.addItem(item);
    client.setItemCaption(item, item.getName());
  }

  private void addRoute(JpaRoute item) {
    route.addItem(item);
    route.setItemCaption(item,
        String.format("%s - %s", item.getOrigin(), item.getDestiny()));
  }

  private void addTractor(JpaTractor item) {
    tractor.addItem(item);
    tractor.setItemCaption(item, item.getPlate());
  }

  private void addLineType(LineType item) {
    lineType.addItem(item);
    lineType.setItemCaption(item, getMessage("PROP_" + item.toString()));
  }

  @Override
  public void setLine(JpaLine line) {
    binder.setItemDataSource(line);
  }

}
