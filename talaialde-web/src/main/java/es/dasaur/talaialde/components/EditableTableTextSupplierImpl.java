package es.dasaur.talaialde.components;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.dasaur.talaialde.constants.Messages;
import es.dasaur.vaadin.components.EditableTable.EditableTableTextSupplier;

@Component
public class EditableTableTextSupplierImpl
        implements EditableTableTextSupplier {
    
    @Autowired
    private Locale locale;
    
    private String getMessage(String code) {
        return Messages.getMessage(code, new String[]{}, locale);
    }

    @Override
    public String searchText() {
        return getMessage(Messages.PROMPT_SEARCH);
    }

    @Override
    public String editText() {
        return getMessage(Messages.PROMPT_EDIT);
    }

    @Override
    public String cancelText() {
        return getMessage(Messages.PROMPT_CANCEL);
    }

    @Override
    public String saveText() {
        return getMessage(Messages.PROMPT_SAVE);
    }

}
