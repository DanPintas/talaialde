package es.danpintas.talaialde.constants;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class Formats {

  public static final String MONTH_FORMAT = "MM/yyyy";

  public static final String DATE_FORMAT = "dd/MM/yyyy";

  public static final DateFormat MONTH = new SimpleDateFormat(MONTH_FORMAT);

  public static final DateFormat DATE = new SimpleDateFormat(DATE_FORMAT);

  public static final NumberFormat NUMBER = new DecimalFormat("#,##0.##");

  public static final NumberFormat MONEY = new DecimalFormat("#,##0.00");

  private Formats() {
    // constants
  }

}
