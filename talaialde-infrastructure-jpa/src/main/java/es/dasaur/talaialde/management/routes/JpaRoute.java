package es.dasaur.talaialde.management.routes;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }
    
    public BigDecimal getKm() {
        return km;
    }
    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public BigDecimal getHourFee() {
        return hourFee;
    }

    public void setHourFee(BigDecimal hourFee) {
        this.hourFee = hourFee;
    }

    public BigDecimal getTonFee() {
        return tonFee;
    }

    public void setTonFee(BigDecimal tonFee) {
        this.tonFee = tonFee;
    }

    public BigDecimal getTripFee() {
        return tripFee;
    }

    public void setTripFee(BigDecimal tripFee) {
        this.tripFee = tripFee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
        result = prime * result + ((destiny == null) ? 0 : destiny.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
        JpaRoute other = (JpaRoute) obj;
        if (deleted == null) {
            if (other.deleted != null) {
                return false;
            }
        } else if (!deleted.equals(other.deleted)) {
            return false;
        }
        if (destiny == null) {
            if (other.destiny != null) {
                return false;
            }
        } else if (!destiny.equals(other.destiny)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (origin == null) {
            if (other.origin != null) {
                return false;
            }
        } else if (!origin.equals(other.origin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Route [id=").append(id).append(", deleted=")
                .append(deleted).append(", origin=").append(origin)
                .append(", destiny=").append(destiny).append(", km=").append(km)
                .append(", hourFee=").append(hourFee).append(", tonFee=")
                .append(tonFee).append(", tripFee=").append(tripFee)
                .append("]");
        return builder.toString();
    }

}
