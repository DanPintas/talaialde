package es.danpintas.talaialde.billing.line;

import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "line")
@Data
public class JpaLine {

  public enum LineType {HOUR, TON, TRIP}

  public static final String PROP_CLIENT = "client";

  public static final String PROP_ROUTE = "route";

  public static final String PROP_TRACTOR = "tractor";

  public static final String PROP_DATE = "lineDate";

  public static final String PROP_AMOUNT = "amount";

  public static final String PROP_LINE_TYPE = "lineType";

  public static final String PROP_PRODUCT = "product";

  public static final String PROP_SPECIFICS = "specifics";

  public static final String PROP_VALUE = "value";

  public static final String PROP_CHECKED = "checked";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "id_client")
  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  private JpaClient client;

  @JoinColumn(name = "id_route")
  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  private JpaRoute route;

  @JoinColumn(name = "id_tractor")
  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  private JpaTractor tractor;

  @NotNull
  private Date lineDate = new Date();

  @NotNull
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @NotNull
  private LineType lineType;

  @NotNull
  @Size(max = 255)
  private String product;

  @Size(max = 4000)
  private String specifics;

  @NotNull
  private BigDecimal value;

  private transient boolean checked;

}
