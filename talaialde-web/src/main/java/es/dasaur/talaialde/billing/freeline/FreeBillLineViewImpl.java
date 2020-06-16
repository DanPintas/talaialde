package es.dasaur.talaialde.billing.freeline;

import java.math.BigDecimal;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.dasaur.annotations.View;
import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.vaadin.mvp.VaadinAbstractView;
import es.dasaur.vaadin.utils.NotificationUtils;

@View
public class FreeBillLineViewImpl extends VaadinAbstractView<FreeBillLinePresenter>
        implements FreeBillLineView {

    private static final long serialVersionUID = 8347090806955125286L;

    private BeanFieldGroup<FreeLine> binder;

    private ComboBox client;
    private TextField value;

    @Override
    public void init() {

        binder = new BeanFieldGroup<>(FreeLine.class);

        client = new ComboBox(getMessage(Messages.PROP_CLIENT));
        client.setWidth("100%");
        client.setRequired(true);
        client.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
        client.setFilteringMode(FilteringMode.CONTAINS);
        binder.bind(client, FreeLine.PROP_CLIENT);

        DateField date = new DateField(getMessage(Messages.PROP_DATE));
        date.setWidth("100%");
        date.setRequired(true);
        date.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
        binder.bind(date, FreeLine.PROP_DATE);

        value = new TextField(getMessage(Messages.PROP_VALUE));
        value.setWidth("100%");
        value.setRequired(true);
        value.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
        value.setNullRepresentation("");
        value.setConverter(BigDecimal.class);
        value.setConversionError(getMessage(Messages.ERROR_CONVERSION_DOUBLE));
        binder.bind(value, Line.PROP_VALUE);

        TextField product = new TextField("Concepto");
        product.setWidth("100%");
        product.setRequired(true);
        product.setRequiredError(getMessage(Messages.PROMPT_REQUIRED));
        product.setNullRepresentation("");
        binder.bind(product, Line.PROP_PRODUCT);

        TextArea specifics = new TextArea(getMessage(Messages.PROP_SPECIFICS));
        specifics.setWidth("100%");
        specifics.setNullRepresentation("");
        binder.bind(specifics, Line.PROP_SPECIFICS);

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
        viewWrapper.setSizeFull();
        viewWrapper.setComponentAlignment(view, Alignment.TOP_CENTER);

        setCompositionRoot(viewWrapper);
        setSizeFull();
        
        refresh();
    }

    private void refresh() {
        Client selectedClient = (Client) client.getValue();
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
        binder.setItemDataSource(new FreeLine());
        binder.getFields().forEach(
                f -> ((AbstractField<?>) f).setValidationVisible(false));
    }

    private void addClient(Client item) {
        client.addItem(item);
        client.setItemCaption(item, item.getName());
    }

    @Override
    public void setLine(FreeLine line) {
        binder.setItemDataSource(line);
    }

}