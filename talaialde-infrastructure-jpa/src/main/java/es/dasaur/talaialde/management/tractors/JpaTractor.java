package es.dasaur.talaialde.management.tractors;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tractor")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Date getVehicleInspectionExpiry() {
        return vehicleInspectionExpiry;
    }

    public void setVehicleInspectionExpiry(Date vehicleInspectionExpiry) {
        this.vehicleInspectionExpiry = vehicleInspectionExpiry;
    }

    public String getUsualDriver() {
        return usualDriver;
    }

    public void setUsualDriver(String usualDriver) {
        this.usualDriver = usualDriver;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((plate == null) ? 0 : plate.hashCode());
        result = prime * result
                + ((usualDriver == null) ? 0 : usualDriver.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JpaTractor other = (JpaTractor) obj;
        if (deleted == null) {
            if (other.deleted != null) {
                return false;
            }
        } else if (!deleted.equals(other.deleted)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (plate == null) {
            if (other.plate != null) {
                return false;
            }
        } else if (!plate.equals(other.plate)) {
            return false;
        }
        if (usualDriver == null) {
            if (other.usualDriver != null) {
                return false;
            }
        } else if (!usualDriver.equals(other.usualDriver)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tractor [id=").append(id).append(", deleted=")
                .append(deleted).append(", plate=").append(plate)
                .append(", vehicleInspectionExpiry=")
                .append(vehicleInspectionExpiry).append(", usualDriver=")
                .append(usualDriver).append("]");
        return builder.toString();
    }
    
}
