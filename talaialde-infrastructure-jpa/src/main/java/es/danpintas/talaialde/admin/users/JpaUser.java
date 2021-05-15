package es.danpintas.talaialde.admin.users;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
public class JpaUser {

  public static final String PROP_USERNAME = "username";

  public static final String PROP_PASSWORD = "password";

  public static final String PROP_ACTIVE = "active";

  public static final String PROP_NAME = "name";

  public static final String PROP_SURNAME = "surname";

  public static final String PROP_EMAIL = "email";

  public static final String PROP_ROLE = "role";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(max = 255)
  private String username;

  @NotNull
  @Size(max = 255)
  private String password;

  @NotNull
  private Boolean active = true;

  @NotNull
  @Size(max = 255)
  private String name;

  @NotNull
  @Size(max = 255)
  private String surname;

  @NotNull
  @Size(max = 255)
  @Email
  private String email;

  @JoinColumn(name = "id_role")
  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull
  private JpaRole role;

}
