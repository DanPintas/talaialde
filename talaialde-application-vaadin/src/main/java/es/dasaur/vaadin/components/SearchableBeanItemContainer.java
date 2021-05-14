package es.dasaur.vaadin.components;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;

public class SearchableBeanItemContainer<T> 
        extends BeanItemContainer<T> {

	private static final long serialVersionUID = -4231201829858858936L;

	private transient Object[] searchableProperties;

	private String searchPatrol;

	private final Filter searchFilter = new SearchFilter();

	public SearchableBeanItemContainer(Class<? super T> type) {
		this(type, (Object[]) null);
	}

	public SearchableBeanItemContainer(Class<? super T> type,
			Object... searchableProperties) {
		super(type);
		this.setSearchPatrol(null);
		this.setSearchableProperties(searchableProperties);
	}

	public String getSearchPatrol() {
		return searchPatrol;
	}

	public void setSearchPatrol(String searchPatrol) {
		this.searchPatrol = searchPatrol;
		updateSearchFilter();
	}

	public Object[] getSearchableProperties() {
		return searchableProperties;
	}

	public void setSearchableProperties(Object... searchableProperties) {
		this.searchableProperties = searchableProperties;
	}

	private void updateSearchFilter() {
		this.removeContainerFilter(searchFilter);
		this.addContainerFilter(searchFilter);
	}
	
	private class SearchFilter implements Filter {
        private static final long serialVersionUID = 7212280815935771870L;

        @Override
        public boolean passesFilter(Object itemId, Item item) {
            return StringUtils.isEmpty(getSearchPatrol()) || 
                    searchableProperties == null || 
                    Arrays.asList(searchableProperties).stream()
                            .filter(p -> p != null)
                            .anyMatch(p -> checkProperty(item, p));
        }
        
        private boolean checkProperty(Item item, Object searchablePropertyId) {
            
            boolean passesFilter = false;
            
            Property<?> property = 
                    item.getItemProperty(searchablePropertyId);
            if (property != null) {
                Object propertyValue = property.getValue();
                String propStringValue = propertyValue != null ? 
                        propertyValue.toString() : "";
                if (propStringValue.toLowerCase().contains(
                        getSearchPatrol().toLowerCase())) {
                    passesFilter = true;
                }
            }
            return passesFilter;
        }
        
        @Override
        public boolean appliesToProperty(Object propertyId) {
            return searchableProperties != null 
                    && Arrays.asList(searchableProperties).contains(propertyId);
        }
	}
}