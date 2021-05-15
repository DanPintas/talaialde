package es.danpintas.talaialde.billing.freeline;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.danpintas.talaialde.management.clients.JpaClient;

@Entity
@Table(name = "freeline")
public class JpaFreeLine {

    public static final String PROP_CLIENT = "client";
    public static final String PROP_DATE = "lineDate";
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

    @NotNull
    private Date lineDate = new Date();

    @NotNull
    @Size(max = 255)
    private String product;

    @Size(max = 4000)
    private String specifics;

    @NotNull
    private BigDecimal value;

    private transient boolean checked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JpaClient getClient() {
        return client;
    }

    public void setClient(JpaClient client) {
        this.client = client;
    }

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date date) {
        this.lineDate = date;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result
                + ((lineDate == null) ? 0 : lineDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result
                + ((specifics == null) ? 0 : specifics.hashCode());
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
        JpaFreeLine other = (JpaFreeLine) obj;
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
        if (product == null) {
            if (other.product != null) {
                return false;
            }
        } else if (!product.equals(other.product)) {
            return false;
        }
        if (specifics == null) {
            if (other.specifics != null) {
                return false;
            }
        } else if (!specifics.equals(other.specifics)) {
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
                .append(client).append(", lineDate=").append(lineDate)
                .append(", product=").append(product).append(", specifics=")
                .append(specifics).append(", value=").append(value).append("]");
        return builder.toString();
    }

}
