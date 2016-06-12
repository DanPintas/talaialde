package es.dasaur.vaadin.components;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class EditableTable<T> 
        extends CustomComponent {

    public interface EditableTableTextSupplier {
        String searchText();

        String editText();

        String cancelText();

        String saveText();
    }

    private static final long serialVersionUID = 9217091693666115964L;
    
    private static final Logger LOG =
            LoggerFactory.getLogger(EditableTable.class);
    
    private Button btEdit;
    
    private SearchableBeanItemContainer<T> container;
    
    private Table table;
    
    private transient Runnable updater;
    
    private HorizontalLayout lowerActions;

    private Button btSave;
    private Button btCancel;
    
    private transient List<T> elementsPreModification;
    
    private transient Set<T> modifiedElements;
    private transient Set<T> errorElements;

    private Class<T> dataType;

    public EditableTable(Class<T> dataType, EditableTableTextSupplier iEditableTable) {
        super();
        this.dataType = dataType;

        container = new SearchableBeanItemContainer<>(dataType);
        modifiedElements = new HashSet<>();
        errorElements = new HashSet<>();
        
        TextField tfBuscar = new TextField();
        tfBuscar.setIcon(FontAwesome.SEARCH);
        tfBuscar.setDescription(iEditableTable.searchText());
        tfBuscar.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        tfBuscar.addTextChangeListener(
                event -> container.setSearchPatrol(event.getText()));
        
        btEdit = new Button(FontAwesome.EDIT);
        btEdit.setDescription(iEditableTable.editText());
        btEdit.addClickListener(event -> setEditableMode(true));
        
        Button btExcel = new Button(FontAwesome.FILE_EXCEL_O);
        btExcel.setDescription(iEditableTable.editText());
        
        HorizontalLayout upperActions = new HorizontalLayout(btEdit, btExcel);
        upperActions.setSpacing(true);
        
        table = new Table(null, container);
        table.setSizeFull();
        
        btSave = new Button(iEditableTable.saveText(),FontAwesome.SAVE);
        btSave.setEnabled(false);
        btSave.addClickListener(event -> updater.run());
        
        btCancel = new Button(iEditableTable.cancelText(), FontAwesome.TIMES);
        btCancel.setEnabled(false);
        btCancel.addClickListener(event -> cancel());
        
        lowerActions = new HorizontalLayout(btSave, btCancel);
        lowerActions.setSpacing(true);
        
        VerticalLayout tableLayout = new VerticalLayout(upperActions, table, lowerActions);
        tableLayout.setSizeFull();
        tableLayout.setSpacing(true);
        tableLayout.setComponentAlignment(upperActions, Alignment.TOP_RIGHT);
        tableLayout.setComponentAlignment(lowerActions, Alignment.BOTTOM_RIGHT);
        tableLayout.setExpandRatio(table, 1.0f);
        
        VerticalLayout component = new VerticalLayout(tfBuscar, tableLayout);
        component.setSizeFull();
        component.setMargin(true);
        component.setSpacing(true);
        component.setExpandRatio(tableLayout, 1.0f);
        
        setCompositionRoot(component);
        setSizeFull();
    }
    
    public void select(Object itemId) {
        table.select(itemId);
    }
    
    private void cancel() {
        setEditableMode(false);
        setItems(elementsPreModification);
    }
    
    public void setEditable(boolean editable) {
        btEdit.setVisible(editable);
        lowerActions.setVisible(editable);
    }

    public final void setEditableMode(boolean editable) {
        btEdit.setEnabled(!editable);
        table.setEditable(editable);
        btCancel.setEnabled(editable);
        btSave.setEnabled(editable);
        if(editable) {
            fetchElementsPreModification();
        }
    }

    private void fetchElementsPreModification() {
        elementsPreModification = container.getItemIds().stream()
                .map(this::createCopy).collect(Collectors.toList());
    }
    
    private T createCopy(T element) {
        T copy = null;
        try {
            copy = dataType.newInstance();
            BeanUtils.copyProperties(element, copy);
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Error al copiar bean", e);
        }
        return copy;
    }

    public void setItems(List<T> listItems) {
        container.removeAllItems();
        container.addAll(listItems);
    }
    
    public final void setVisibleColumns(Object... visibleColumns) {
        table.setVisibleColumns(visibleColumns);
        container.setSearchableProperties(visibleColumns);
    }
    
    public void setSelectable(boolean selectable) {
        table.setSelectable(selectable);
    }
    
    public void addValueChangeListener(ValueChangeListener listener) {
        table.addValueChangeListener(listener);
    }
    
    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) table.getValue();
    }

    public final void addGeneratedColumn(Object id, ColumnGenerator generatedColumn) {
        table.addGeneratedColumn(id, generatedColumn);
    }

    public final void setSearchableProperties(Object... searchableProperties) {
        container.setSearchableProperties(searchableProperties);
    }
    
    public final void setColumnHeaders(String... columnHeaders) {
        table.setColumnHeaders(columnHeaders);
    }
    
    protected final void markAsModified(T element) {
        modifiedElements.add(element);
    }
    
    protected final void markAsUnmodified(T element) {
        modifiedElements.remove(element);
    }
    
    protected final void markAsError(T element) {
        errorElements.add(element);
    }
    
    protected final void markAsNonError(T element) {
        errorElements.remove(element);
    }
    
    protected final Set<T> getModifiedElements() {
        if(!errorElements.isEmpty()){
            throw new EditionErrorException();
        }
        return modifiedElements;
    }
    
}
