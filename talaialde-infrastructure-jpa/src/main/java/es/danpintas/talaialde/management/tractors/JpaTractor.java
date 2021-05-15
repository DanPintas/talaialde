package es.danpintas.talaialde.management.tractors;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tractor")
@Data
public class JpaTractor {
    
    public static final String PROP_PLATE = "plate";
    public static final String PROP_VI_EXPIRY = "vehicleInspectionExpiry";
    public static final String PROP_USUAL_DRIVER = "usualDriver";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Boolean deleted = false;
    
    @NotNull
    @Size(max=20)
    private String plate;
    
    private Date vehicleInspectionExpiry;
    
    @Size(max=255)
    private String usualDriver;
    
}
