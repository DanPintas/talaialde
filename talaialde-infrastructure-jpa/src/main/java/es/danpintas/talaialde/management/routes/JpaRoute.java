package es.danpintas.talaialde.management.routes;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "route")
@Data
public class JpaRoute {
    
    public static final String PROP_ORIGIN = "origin";
    public static final String PROP_DESTINY = "destiny";
    public static final String PROP_KM = "km";
    public static final String PROP_HOUR_FEE = "hourFee";
    public static final String PROP_TON_FEE = "tonFee";
    public static final String PROP_TRIP_FEE = "tripFee";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Boolean deleted = false;
    
    @NotNull
    @Size(max=255)
    private String origin;
    
    @NotNull
    @Size(max=255)
    private String destiny;
    
    @Min(0)
    private BigDecimal km;
    
    @Min(0)
    private BigDecimal hourFee;
    
    @Min(0)
    private BigDecimal tonFee;
    
    @Min(0)
    private BigDecimal tripFee;

}
