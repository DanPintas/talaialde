package es.danpintas.talaialde.management.clients;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import es.danpintas.talaialde.annotations.LongText;

@Entity
@Table(name = "client")
@Data
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
    
}
