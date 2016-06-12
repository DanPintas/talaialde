package es.dasaur.talaialde.components;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.dasaur.talaialde.constants.Messages;
import es.dasaur.vaadin.components.BeanForm.BeanFormTextSupplier;

@Component
public class BeanFormTextSupplierImpl
        implements BeanFormTextSupplier {
    
    @Autowired
    private Locale locale;
    
    private String getMessage(String code) {
        return Messages.getMessage(code, new String[]{}, locale);
    }
    
    @Override
    public String requiredFieldText() {
        return getMessage(Messages.PROMPT_REQUIRED);
    }

    @Override
    public String intConversionErrorText() {
        return getMessage(Messages.ERROR_CONVERSION_INTEGER);
    }

    @Override
    public String doubleConversionErrorText() {
        return getMessage(Messages.ERROR_CONVERSION_DOUBLE);
    }

    @Override
    public String dateConversionErrorText() {
        return getMessage(Messages.ERROR_CONVERSION_DATE);
    }
    
}
