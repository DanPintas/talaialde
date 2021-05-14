package es.dasaur.talaialde.management.clients;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import es.dasaur.talaialde.annotations.LongText;

@Entity
@Table
public class JpaClient {
    
    public static final String PROP_TIN = "tin";
    public static final String PROP_NAME = "name";
    public static final String PROP_LINE = "line";
    public static final String PROP_LOCALITY = "locality";
    public static final String PROP_REGION = "region";
    public static final String PROP_PHONE_NUMBER = "phoneNumber";
    public static final String PROP_EMAIL = "email";
    public static final String PROP_CONTACT = "contact";
    public static final String PROP_ACCOUNT_NUMBER = "accountNumber";
    public static final String PROP_OBSERVATIONS = "observations";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Boolean deleted = false;
    
    @NotNull
    @Size(max=20)
    private String tin;
    
    @NotNull
    @Size(max=255)
    private String name;
    
    @NotNull
    @Size(max=255)
    private String line;
    
    @NotNull
    @Size(max=255)
    private String locality;
    
    @NotNull
    @Size(max=25)
    private String region;
    
    @Size(max=20)
    private String phoneNumber;
    
    @Email
    @Size(max=100)
    private String email;
    
    @Size(max=255)
    private String contact;
    
    @Size(max=50)
    private String accountNumber;
    
    @Size(max=1000)
    @LongText
    private String observations;

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

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accountNumber == null) ? 0 : accountNumber.hashCode());
        result = prime * result + ((contact == null) ? 0 : contact.hashCode());
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((line == null) ? 0 : line.hashCode());
        result = prime * result
                + ((locality == null) ? 0 : locality.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((observations == null) ? 0 : observations.hashCode());
        result = prime * result
                + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((region == null) ? 0 : region.hashCode());
        result = prime * result + ((tin == null) ? 0 : tin.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JpaClient other = (JpaClient) obj;
        if (accountNumber == null) {
            if (other.accountNumber != null)
                return false;
        } else if (!accountNumber.equals(other.accountNumber))
            return false;
        if (contact == null) {
            if (other.contact != null)
                return false;
        } else if (!contact.equals(other.contact))
            return false;
        if (deleted == null) {
            if (other.deleted != null)
                return false;
        } else if (!deleted.equals(other.deleted))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (line == null) {
            if (other.line != null)
                return false;
        } else if (!line.equals(other.line))
            return false;
        if (locality == null) {
            if (other.locality != null)
                return false;
        } else if (!locality.equals(other.locality))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (observations == null) {
            if (other.observations != null)
                return false;
        } else if (!observations.equals(other.observations))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        if (region == null) {
            if (other.region != null)
                return false;
        } else if (!region.equals(other.region))
            return false;
        if (tin == null) {
            if (other.tin != null)
                return false;
        } else if (!tin.equals(other.tin))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Client [id=").append(id).append(", deleted=")
                .append(deleted).append(", tin=").append(tin).append(", name=")
                .append(name).append(", line=").append(line)
                .append(", locality=").append(locality).append(", region=")
                .append(region).append(", phoneNumber=").append(phoneNumber)
                .append(", email=").append(email).append(", contact=")
                .append(contact).append(", accountNumber=")
                .append(accountNumber).append(", observations=")
                .append(observations).append("]");
        return builder.toString();
    }
    
}
