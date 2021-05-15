package es.dasaur.talaialde.components;

import com.vaadin.v7.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.v7.ui.Table;
import com.vaadin.v7.ui.TextField;
import es.dasaur.vaadin.components.SearchableBeanItemContainer;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.dasaur.vaadin.components.BeanForm;
import es.dasaur.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.dasaur.vaadin.components.ConfirmWindow;
import es.dasaur.vaadin.utils.NotificationUtils;

public class MasterDetail<T> extends CustomComponent {

    public interface MasterDetailTextSupplier {
        String newText();
        String editText();
        String deleteText();
        String acceptText();
        String cancelText();
        String deleteDescText();
        String yesText();
        String noText();
        String commitErrorText();
        String commitOkText();
        String deleteOkText();
    }

    private static final long serialVersionUID = 7699459863439459478L;
    
    private Supplier<List<T>> loader;
    private UnaryOperator<T> saver;
    private Consumer<T> remover;

    private SearchableBeanItemContainer<T> container;
    private Table master;
    private BeanForm<T> detail;
    
    private TextField tfSearch;

    private Button btRefresh;
    private Button btNew;
    private Button btEdit;
    private Button btDelete;

    private Button btAccept;
    private Button btCancel;

    private boolean editing;

    private String commitOkText;
    private String commitErrorText;
    private String deleteOkText;

    private HorizontalLayout detailLowerActions;

    private HorizontalLayout detailUpperActions;

    @SuppressWarnings("unchecked")
    public MasterDetail(Class<T> beanType, 
            Supplier<List<T>> loader,
            UnaryOperator<T> saver,
            Consumer<T> remover,
            MasterDetailTextSupplier textSupplier,
            BeanFormTextSupplier formTextSupplier) {
        this.loader = loader;
        this.saver = saver;
        this.remover = remover;
        
        this.commitOkText = textSupplier.commitOkText();
        this.commitErrorText = textSupplier.commitErrorText();
        this.deleteOkText = textSupplier.deleteOkText();
        
        container = new SearchableBeanItemContainer<>(beanType);
        master = new Table();
        master.setContainerDataSource(container);
        master.setEditable(false);
        master.setSizeFull();
        master.setSelectable(true);
        master.addValueChangeListener(e -> setDetail((T) master.getValue()));
        
        tfSearch = new TextField();
        tfSearch.setWidth("100%");
        tfSearch.setIcon(FontAwesome.SEARCH);
        tfSearch.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        tfSearch.addTextChangeListener(e -> container.setSearchPatrol(e.getText()));
        
        btRefresh = new Button(FontAwesome.REFRESH);
        btRefresh.addClickListener(e -> refresh());
        
        btNew = new Button(textSupplier.newText(), FontAwesome.PLUS_CIRCLE);
        btNew.addClickListener(e -> edit(null));
        
        btEdit = new Button(textSupplier.editText(), FontAwesome.EDIT);
        btEdit.addClickListener(e -> edit((T) master.getValue()));
        btEdit.setEnabled(false);
        
        btDelete = new Button(textSupplier.deleteText(), FontAwesome.TIMES);
        btDelete.addStyleName(ValoTheme.BUTTON_DANGER);
        btDelete.addClickListener(e -> confirmDelete(
                textSupplier.deleteText(), textSupplier.deleteDescText(),
                textSupplier.yesText(), textSupplier.noText()));
        btDelete.setEnabled(false);
        
        detailUpperActions = new HorizontalLayout(
                btRefresh,btNew, btEdit, btDelete);
        detailUpperActions.setSpacing(true);
        
        detail = new BeanForm<>(beanType, formTextSupplier);
        detail.setReadOnly(true);
        Panel panelDetail = new Panel(detail);
        panelDetail.setSizeFull();
        
        btAccept = new Button(textSupplier.acceptText(), FontAwesome.CHECK_CIRCLE);
        btAccept.addClickListener(event -> acceptEdition());
        btAccept.setEnabled(false);
        
        btCancel = new Button(textSupplier.cancelText(), FontAwesome.TIMES_CIRCLE);
        btCancel.addClickListener(event -> cancelEdition());
        btCancel.setEnabled(false);
        
        detailLowerActions = new HorizontalLayout(btAccept, btCancel);
        detailLowerActions.setSpacing(true);
        
        VerticalLayout vlMaster = new VerticalLayout(tfSearch, master);
        vlMaster.setMargin(false);
        vlMaster.setSizeFull();
        vlMaster.setSpacing(true);
        vlMaster.setExpandRatio(master, 1);
        
        VerticalLayout vlDetail = new VerticalLayout(detailUpperActions, 
                panelDetail, detailLowerActions);
        vlDetail.setMargin(false);
        vlDetail.setSizeFull();
        vlDetail.setSpacing(true);
        vlDetail.setExpandRatio(panelDetail, 1f);
        
        HorizontalLayout component = new HorizontalLayout(vlMaster, vlDetail);
        component.setSizeFull();
        component.setMargin(true);
        component.setSpacing(true);
        component.setExpandRatio(vlMaster, 3);
        component.setExpandRatio(vlDetail, 2);
        component.setSpacing(true);
        setCompositionRoot(component);
        setSizeFull();
    }
    
    public void setEditable(boolean canEdit) {
        detailUpperActions.setVisible(canEdit);
        detailLowerActions.setVisible(canEdit);
    }

    public void refresh() {
        Object[] visibleColumns = master.getVisibleColumns();
        Object value = master.getValue();
        container = new SearchableBeanItemContainer<>(container.getBeanType());
        container.setSearchableProperties(master.getVisibleColumns());
        container.addAll(loader.get());
        master.setContainerDataSource(container);
        master.setVisibleColumns(visibleColumns);
        master.select(value);
        
        detail.refreshLinks();
    }

    private void setDetail(T value) {
        detail.setBean(value);
        detail.setReadOnly(!editing);
        btEdit.setEnabled(value != null);
        btDelete.setEnabled(value != null);
    }

    private void edit(T bean) {
        setEditing(true);
        detail.setBean(bean);
        if(bean == null){
            master.select(null);
            UI.getCurrent().push();
        }
    }

    private void setEditing(boolean editing) {
        this.editing = editing;
        master.setSelectable(!editing);
        btRefresh.setEnabled(!editing);
        btNew.setEnabled(!editing);
        btEdit.setEnabled(!editing && master.getValue() != null);
        btDelete.setEnabled(!editing && master.getValue() != null);
        btAccept.setEnabled(editing);
        btCancel.setEnabled(editing);
    }
    
    private void acceptEdition() {
        try {
            detail.commit();
            T savedItem = saver.apply(detail.getBean());
            detail.setReadOnly(true);
            setEditing(false);
            refresh();
            container.getItemIds().forEach(i -> {
                if (i.equals(savedItem)){
                    master.select(i);
                }
            });
            NotificationUtils.showMessage(commitOkText);
        } catch (CommitException e) {
            NotificationUtils.showWarning(commitErrorText);
        }
    }

    @SuppressWarnings("unchecked")
    private void cancelEdition() {
        setEditing(false);
        detail.setBean((T)master.getValue());
        detail.setReadOnly(true);
    }

    private void confirmDelete(String deleteTitle, String deleteDesc, 
            String yes, String no) {
        ConfirmWindow.show(deleteTitle, deleteDesc, yes, no, () -> delete());
    }

    @SuppressWarnings("unchecked")
    private void delete() {
        remover.accept((T) master.getValue());
        refresh();
        NotificationUtils.showMessage(deleteOkText);
    }
    
    public void setLinkMap(Map<String, Supplier<Map<?, String>>> linkMap){
        detail.setLinkMap(linkMap);
        detail.refreshLinks();
    }

    public void setDeleteEnabled(boolean b) {
        btDelete.setVisible(b);
    }

    public void setVisibleColumns(Object... visibleColumns) {
        master.setVisibleColumns(visibleColumns);
        container.setSearchableProperties(visibleColumns);
    }

    public void setColumnHeaders(String... columnHeaders) {
        master.setColumnHeaders(columnHeaders);
    }

    public void setVisibleFields(Object... visibleFields) {
        detail.setVisibleFields(visibleFields);
    }
    
    public void setFieldCaptions(String... fieldCaptions) {
        detail.setFieldCaptions(fieldCaptions);
    }

    public void addGeneratedColumn(Object id, Table.ColumnGenerator generatedColumn){
        master.addGeneratedColumn(id, generatedColumn);
    }

}
