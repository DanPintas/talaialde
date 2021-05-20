package es.danpintas.vaadin.utils;

import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.Component;

/**
 * Clase con útiles de estilos CSS.
 *
 * @author dsalas
 */
public class CssUtils {

  private CssUtils() {
    // clase de útiles
  }

  /**
   * Permite añadir estilos personalizados de CSS a un componente. <br> No se pueden sobreescribir estilos fijados por métodos Vaadin;
   * normalmente, esto se aplicará a dimensiones. <br> Las llamadas posteriores a este método eliminan las reglas añadidas previamente; es
   * responsabilidad del usuario mantenerlas y añadirlas en llamadas posteriores.
   *
   * @param comp Componente al que inyectar CSS.
   * @param rules Reglas CSS a inyectar, como único String o serie de estos.
   */
  public static final void injectCss(Component comp, String... rules) {
    Styles styles = Page.getCurrent().getStyles();
    String styleName = String.format("runtime-css-%s", comp.hashCode());
    comp.addStyleName(styleName);
    StringBuilder css = new StringBuilder("." + styleName + " { ");
    for (String rule : rules) {
      css.append(rule + " ");
    }
    css.append("}");
    styles.add(css.toString());
  }

  /**
   * Genera un estilo CSS a partir de un par propiedad-valor
   *
   * @param property Nombre de la propiedad CSS
   * @param value Valor de la propiedad CSS
   * @return String con la regla CSS
   */
  public static final String getCssStyle(String property, String value) {
    return String.format("%s: %s;", property, value);
  }

}
