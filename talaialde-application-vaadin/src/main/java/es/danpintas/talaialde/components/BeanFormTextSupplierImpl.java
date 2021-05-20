package es.danpintas.talaialde.components;

import es.danpintas.talaialde.constants.Messages;
import es.danpintas.vaadin.components.BeanForm.BeanFormTextSupplier;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanFormTextSupplierImpl
    implements BeanFormTextSupplier {

  @Autowired
  private Locale locale;

  private String getMessage(String code) {
    return Messages.getMessage(code, new String[] {}, locale);
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
