package es.dasaur.vaadin.components;

import com.vaadin.v7.ui.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;

public class RelationTable<C, R> extends CustomComponent {
    
    private static final long serialVersionUID = -6470941096228722638L;
    
    @FunctionalInterface
    public interface ToStringFunction<T> {
        String getString(T item);
    }
    
    private static final String ROW = "row";

    private transient ToStringFunction<C> colNameGenerator;
    private transient ToStringFunction<R> rowNameGenerator;
    
    private Table table;
    
    private transient Map<C, List<R>> relations;

    public RelationTable(List<C> cols, List<R> rows, 
            ToStringFunction<C> colNameGenerator,
            ToStringFunction<R> rowNameGenerator) {
        
        this.colNameGenerator = colNameGenerator;
        this.rowNameGenerator = rowNameGenerator;
        relations = new HashMap<>();
        cols.forEach(c -> relations.put(c, new ArrayList<>()));
        
        table = new Table();
        
        table.addGeneratedColumn(ROW, this::getRowName);
        table.setColumnHeader(ROW, "");
        cols.forEach(this::generateColumn);
        rows.forEach(table::addItem);
        
        table.setSizeFull();
        setCompositionRoot(table);
    }
    
    @SuppressWarnings("unchecked")
    private Object getRowName(Table source, Object itemId, Object propertyId){
        return rowNameGenerator.getString((R) itemId);
    }
    
    private void generateColumn(C c){
        table.addGeneratedColumn(c, this::getCheckBox);
        table.setColumnHeader(c, colNameGenerator.getString(c));
    }
    
    @SuppressWarnings("unchecked")
    private Object getCheckBox(Table source, Object itemId, Object propertyId){
        C c = (C) propertyId;
        R r = (R) itemId;
        CheckBox cb = new CheckBox();
        cb.setValue(relations.get(c).contains(r));
        cb.addValueChangeListener(event -> changeRelation(
                r, c, cb.getValue()));
        cb.setReadOnly(isReadOnly());
        return cb;
    }
    
    private void changeRelation(R row, C col, Boolean add) {
        List<R> rows = relations.get(col);
        if(add) {
            rows.add(row);
        } else {
            rows.remove(row);
        }
    }
    
    public Map<C, List<R>> getRelations() {
        return relations;
    }

    public void setRelations(Map<C, List<R>> relations){
        this.relations = relations;
        table.markAsDirty();
    }

}
