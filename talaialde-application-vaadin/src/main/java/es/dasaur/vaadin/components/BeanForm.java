package es.dasaur.vaadin.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.v7.data.fieldgroup.BeanFieldGroup;
import com.vaadin.v7.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.v7.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.v7.ui.AbstractField;
import com.vaadin.v7.ui.AbstractTextField;
import com.vaadin.v7.ui.ComboBox;
import com.vaadin.v7.ui.Field;
import com.vaadin.v7.ui.TextArea;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.dasaur.talaialde.annotations.LongText;

public class BeanForm<T> extends CustomComponent {
    
    private static final List<Class<?>> INTEGER_TYPES = Arrays.asList(
            Integer.class, int.class, 
            Long.class, long.class, 
            BigInteger.class);
    
    private static final List<Class<?>> DOUBLE_TYPES = Arrays.asList(
            Float.class, float.class, 
            Double.class, double.class, 
            BigDecimal.class);
    
    public interface BeanFormTextSupplier {
        String requiredFieldText();
        String intConversionErrorText();
        String doubleConversionErrorText();
        String dateConversionErrorText();
    }

    private class FormFieldGroupFieldFactory 
            extends DefaultFieldGroupFieldFactory {
    
        private static final long serialVersionUID = -2538801093257933614L;
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public <F extends Field> F createField(Class<?> type, Class<F> fieldType) {
            F field = super.createField(type, fieldType);
            if(type.isAnnotationPresent(Entity.class)){
                field = (F) new ComboBox(type.getSimpleName());
            }
            if(field instanceof AbstractTextField){
                
                AbstractTextField tf = (AbstractTextField) field;
                tf.setNullRepresentation("");
                if(INTEGER_TYPES.contains(type)){
                    tf.setConversionError(intConversionErrorText);
                }
                if(DOUBLE_TYPES.contains(type)){
                    tf.setConversionError(doubleConversionErrorText);
                }
                if(type.equals(Date.class)){
                    tf.setConversionError(dateConversionErrorText);
                }
            }
            return field;
        }
        
    }

    private static final Logger LOG = LoggerFactory.getLogger(BeanForm.class);
    
    private static final long serialVersionUID = -6101530870470746366L;
    
    private transient T bean;
    private Class<T> beanType;
    private BeanFieldGroup<T> binder;
    
    private Map<String, Supplier<Map<?, String>>> linkMap;
    
    private List<GridLayout> columns;
    private transient List<Object> visibleFields;
    private List<Component> fieldList;
    
    private List<String> captionList;

    private String requiredFieldText;
    private String intConversionErrorText;
    private String doubleConversionErrorText;
    private String dateConversionErrorText;

    private boolean readOnly;

    public BeanForm(Class<T> beanType, BeanFormTextSupplier textSupplier) {
        this(beanType, textSupplier, 1);
    }
    
    public BeanForm(Class<T> beanType, BeanFormTextSupplier textSupplier, int nColumns) {
        this.beanType = beanType;
        
        requiredFieldText = textSupplier.requiredFieldText();
        intConversionErrorText = textSupplier.intConversionErrorText();
        doubleConversionErrorText = textSupplier.doubleConversionErrorText();
        dateConversionErrorText = textSupplier.dateConversionErrorText();
        
        columns = new ArrayList<>();
        fieldList = new ArrayList<>();
        
        HorizontalLayout form = new HorizontalLayout();
        form.setMargin(true);
        form.setSpacing(true);
        form.setWidth("100%");
        
        IntStream.range(0, nColumns).forEach(i -> {
            GridLayout column = new GridLayout(2, 1);
            //column.setColumnExpandRatio(1, 1);
            column.setMargin(false);
            column.setWidth("100%");
            column.setSpacing(true);
            form.addComponent(column);
            columns.add(column);
        });
        
        binder = new BeanFieldGroup<>(beanType);
        binder.setFieldFactory(new FormFieldGroupFieldFactory());
        setBean(bean);
        setVisibleFields(binder.getUnboundPropertyIds());
        
        setCompositionRoot(form);
    }

    private T getNewBean() {
        T newBean = null;
        try {
            newBean = beanType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.warn("Error fatal", e);
        }
        return newBean;
    }
    
    public BeanFieldGroup<T> getBinder () {
        return binder;
    }

    public void commit() throws CommitException {
        setFieldValidation(true);
        binder.commit();
    }

    public T getBean() {
        setFieldValidation(true);
        bean = binder.getItemDataSource().getBean();
        return bean;
    }

    public void setBean(T bean) {
        this.bean = Optional.ofNullable(bean).orElse(getNewBean());
        binder.setItemDataSource(this.bean);
        setFieldValidation(false);
    }

    public void setVisibleFields(Object... visibleFields){
        setVisibleFields(Arrays.asList(visibleFields));
    }
    
    public void setVisibleFields(Collection<Object> visibleFields) {
        this.visibleFields = new ArrayList<>(visibleFields);
        refresh();
    }

    private void refresh() {
        fieldList.stream().filter(c -> c instanceof Field<?>)
                .map(c -> (Field<?>) c).forEach(binder::unbind);
        fieldList = new ArrayList<>();
        columns.forEach(row -> row.removeAllComponents());
        
        int elementsPerRow = visibleFields.size() / columns.size() + 
                (visibleFields.size() % columns.size() == 0 ? 0 : 1);
        IntStream.range(0, visibleFields.size())
                .forEach(i -> addField(i, i / elementsPerRow));
        setFieldValidation(false);
        setReadOnly(readOnly);
        columns.forEach(c -> c.setColumnExpandRatio(1, 1));
    }

    private void addField(int i, int nColumns) {
        Object propertyId = visibleFields.get(i);
        Class<?> propClass = binder.getItemDataSource().getItemProperty(propertyId)
                .getType();
        Field<?> field = !propClass.isEnum() ? 
                binder.buildAndBind(propertyId.toString(), propertyId) : 
                binder.buildAndBind(propertyId.toString(), propertyId, ComboBox.class);
        try {
            java.lang.reflect.Field refField = 
                    beanType.getDeclaredField(propertyId.toString());
            if (refField.isAnnotationPresent(NotNull.class) && 
                    refField.getType() != Boolean.class) {
                field.setRequired(true);
                field.setRequiredError(requiredFieldText);
            } 
            if(refField.isAnnotationPresent(LongText.class)){
                binder.unbind(field);
                field = binder.buildAndBind(
                        propertyId.toString(), propertyId, TextArea.class);
            }
            
        } catch (NoSuchFieldException | SecurityException e) {
            LOG.error("No se puede enlazar la propiedad" + propertyId, e);
        }
        
        GridLayout row = columns.get(nColumns);
        
        Label label = new Label(captionList == null ?
                field.getCaption() : captionList.get(i));
        label.setWidth(10 * label.getValue().length(), Unit.PIXELS);
        row.addComponent(label);
        row.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
        field.setCaption(null);
        field.setWidth("100%");
        HorizontalLayout hlField = new HorizontalLayout(field);
        if(field.getType() == ComboBox.class){
            hlField.setHeight("37px");
        }
        hlField.setWidth("100%");
        hlField.setComponentAlignment(field, Alignment.MIDDLE_LEFT);
        row.addComponent(hlField);
        fieldList.add(field);
    }

    private void setFieldValidation(boolean b) {
        fieldList.forEach(
                field -> ((AbstractField<?>) field).setValidationVisible(b));
    }
    
    public void setFieldCaptions(String... captions) {
        if(captions.length != fieldList.size()) {
            throw new IllegalArgumentException(
                    "The number of captions must match the number of fields");
        }
        this.captionList = Arrays.asList(captions);
        refresh();
    }
    
    public void bindField(Field<?> field, Object propertyId) {
        binder.bind(field, propertyId);
    }
    
    public void addComponent(Component comp){
        fieldList.add(comp);
    }
    
    public void addAndBindField(Field<?> field, Object propertyId) {
        addComponent(field);
        bindField(field, propertyId);
    }
    
    @Override
    public void setReadOnly(boolean b) {
        readOnly = b;
        fieldList.forEach(f -> f.setEnabled(!readOnly));
    }
    
    public void setLinkMap(Map<String, Supplier<Map<?, String>>> linkMap) {
        this.linkMap = linkMap;
    }

    public void refreshLinks() {
        if(linkMap != null ){
            linkMap.entrySet().forEach(this::refreshLink);
        }
    }
    
    private void refreshLink(Entry<String, Supplier<Map<?, String>>> link) {
        ComboBox cb = (ComboBox) binder.getField(link.getKey());
        boolean isReadOnly = cb.isReadOnly();
        cb.setReadOnly(false);
        Object value = cb.getValue();
        cb.removeAllItems();
        link.getValue().get().entrySet().forEach(e -> addLinkedElement(cb, e));
        cb.select(value);
        cb.setReadOnly(isReadOnly);
    }

    private void addLinkedElement(ComboBox cb, Entry<?, String> linkedElement) {
        cb.addItem(linkedElement.getKey());
        cb.setItemCaption(linkedElement.getKey(), linkedElement.getValue());
    }
    
}
