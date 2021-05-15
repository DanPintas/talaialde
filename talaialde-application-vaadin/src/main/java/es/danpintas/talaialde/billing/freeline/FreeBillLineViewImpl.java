package es.danpintas.talaialde.billing.freeline;

import com.vaadin.v7.data.fieldgroup.BeanFieldGroup;
import com.vaadin.v7.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.v7.shared.ui.combobox.FilteringMode;
import com.vaadin.v7.ui.AbstractField;
import com.vaadin.v7.ui.ComboBox;
import com.vaadin.v7.ui.DateField;
import com.vaadin.v7.ui.TextArea;
import com.vaadin.v7.ui.TextField;
import es.danpintas.talaialde.constants.Messages;
import java.math.BigDecimal;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import es.danpintas.annotations.View;
import es.danpintas.talaialde.billing.line.JpaLine;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import es.danpintas.vaadin.utils.NotificationUtils;

@View
public class FreeBillLineViewImpl extends VaadinAbstractView<FreeBillLinePresenter>
        implements FreeBillLineView {

    private static final long serialVersionUID = 8347090806955125286L;

    private BeanFieldGroup<JpaFreeLine> binder;

    private ComboBox client;
    private TextField value;

    @Override
    public void init() {

        binder = new BeanFieldGroup<>(JpaFreeLine.class);

        client = new ComboBox(getMessage(Messages.PROP_CLIENT));
        client.setWidth("100%");
        client.setRequired(true);
        client.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
        client.setFilteringMode(FilteringMode.CONTAINS);
        binder.bind(client, JpaFreeLine.PROP_CLIENT);

        DateField date = new DateField(getMessage(Messages.PROP_DATE));
        date.setWidth("100%");
        date.setRequired(true);
        date.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
        binder.bind(date, JpaFreeLine.PROP_DATE);

        value = new TextField(getMessage(Messages.PROP_VALUE));
        value.setWidth("100%");
        value.setRequired(true);
        value.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
        value.setNullRepresentation("");
        value.setConverter(BigDecimal.class);
        value.setConversionError(getMessage(Messages.ERROR_CONVERSION_DOUBLE));
        binder.bind(value, JpaLine.PROP_VALUE);

        TextField product = new TextField("Concepto");
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

        FormLayout col1 = new FormLayout(client);
        col1.setMargin(false);
        col1.setWidth("100%");
        FormLayout col2 = new FormLayout(date);
        col2.setMargin(false);
        col2.setWidth("100%");
        HorizontalLayout requiredFields = new HorizontalLayout(col1, col2);
        requiredFields.setSpacing(true);
        requiredFields.setWidth("100%");
        requiredFields.setExpandRatio(col1, 2);
        requiredFields.setExpandRatio(col2, 1);

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
        binder.setItemDataSource(new JpaFreeLine());
        binder.getFields().forEach(
                f -> ((AbstractField<?>) f).setValidationVisible(false));
    }

    private void addClient(JpaClient item) {
        client.addItem(item);
        client.setItemCaption(item, item.getName());
    }

    @Override
    public void setLine(JpaFreeLine line) {
        binder.setItemDataSource(line);
    }

}
