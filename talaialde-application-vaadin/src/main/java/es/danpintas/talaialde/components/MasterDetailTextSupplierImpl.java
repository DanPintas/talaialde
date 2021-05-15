package es.danpintas.talaialde.components;

import es.danpintas.talaialde.constants.Messages;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.danpintas.talaialde.components.MasterDetail.MasterDetailTextSupplier;

@Component
public class MasterDetailTextSupplierImpl 
        implements MasterDetailTextSupplier {

    @Autowired
    private Locale locale;
    
    private String getMessage(String code) {
        return Messages.getMessage(code, new String[]{}, locale);
    }

    @Override
    public String newText() {
        return getMessage(Messages.PROMPT_NEW);
    }
    
    @Override
    public String editText() {
        return getMessage(Messages.PROMPT_EDIT);
    }
    
    @Override
    public String deleteText() {
        return getMessage(Messages.PROMPT_DELETE);
    }

    @Override
    public String acceptText() {
        return getMessage(Messages.PROMPT_ACCEPT);
    }
    
    @Override
    public String cancelText() {
        return getMessage(Messages.PROMPT_CANCEL);
    }

    @Override
    public String deleteDescText() {
        return getMessage(Messages.PROMPT_DELETE_DESC);
    }

    @Override
    public String yesText() {
        return getMessage(Messages.PROMPT_YES);
    }

    @Override
    public String noText() {
        return getMessage(Messages.PROMPT_NO);
    }

    @Override
    public String commitOkText() {
        return getMessage(Messages.PROMPT_SAVED_DATA);
    }

    @Override
    public String commitErrorText() {
        return getMessage(Messages.ERROR_FORM);
    }

    @Override
    public String deleteOkText() {
        return getMessage(Messages.PROMPT_DELETED_DATA);
    }

}
