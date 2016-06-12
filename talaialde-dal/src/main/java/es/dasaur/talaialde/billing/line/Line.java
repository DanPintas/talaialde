package es.dasaur.talaialde.billing.line;

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

import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;

@Entity
@Table
public class Line {
    
    public enum LineType { HOUR, TON, TRIP }

    public static final String PROP_CLIENT = "client";
    public static final String PROP_ROUTE = "route";
    public static final String PROP_TRACTOR = "tractor";
    public static final String PROP_DATE = "lineDate";
    public static final String PROP_AMOUNT = "amount";
    public static final String PROP_LINE_TYPE = "lineType";
    public static final String PROP_PRODUCT = "product";
    public static final String PROP_SPECIFICS = "specifics";
    public static final String PROP_VALUE = "value";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn(name="id_client")
    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    private Client client;
    
    @JoinColumn(name="id_route")
    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    private Route route;
    
    @JoinColumn(name="id_tractor")
    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    private Tractor tractor;
    
    @NotNull
    private Date lineDate = new Date();
    
    @NotNull
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private LineType lineType;
    
    @NotNull
    @Size(max=255)
    private String product;
    
    @Size(max=4000)
    private String specifics;
    
    @NotNull
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Tractor getTractor() {
        return tractor;
    }

    public void setTractor(Tractor tractor) {
        this.tractor = tractor;
    }

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date date) {
        this.lineDate = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result + ((lineDate == null) ? 0 : lineDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((lineType == null) ? 0 : lineType.hashCode());
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + ((route == null) ? 0 : route.hashCode());
        result = prime * result
                + ((specifics == null) ? 0 : specifics.hashCode());
        result = prime * result + ((tractor == null) ? 0 : tractor.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Line other = (Line) obj;
        if (amount == null) {
            if (other.amount != null) {
                return false;
            }
        } else if (!amount.equals(other.amount)) {
            return false;
        }
        if (client == null) {
            if (other.client != null) {
                return false;
            }
        } else if (!client.equals(other.client)) {
            return false;
        }
        if (lineDate == null) {
            if (other.lineDate != null) {
                return false;
            }
        } else if (!lineDate.equals(other.lineDate)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (lineType != other.lineType) {
            return false;
        }
        if (product == null) {
            if (other.product != null) {
                return false;
            }
        } else if (!product.equals(other.product)) {
            return false;
        }
        if (route == null) {
            if (other.route != null) {
                return false;
            }
        } else if (!route.equals(other.route)) {
            return false;
        }
        if (specifics == null) {
            if (other.specifics != null) {
                return false;
            }
        } else if (!specifics.equals(other.specifics)) {
            return false;
        }
        if (tractor == null) {
            if (other.tractor != null) {
                return false;
            }
        } else if (!tractor.equals(other.tractor)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Line [id=").append(id).append(", client=")
                .append(client).append(", route=").append(route)
                .append(", tractor=").append(tractor).append(", lineDate=")
                .append(lineDate).append(", amount=").append(amount)
                .append(", lineType=").append(lineType).append(", product=")
                .append(product).append(", specifics=").append(specifics)
                .append(", value=").append(value).append("]");
        return builder.toString();
    }

}
