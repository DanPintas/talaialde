package es.dasaur.talaialde.admin.users;

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

import org.hibernate.validator.constraints.Email;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames="username"))
public class JpaUser {
    
    public static final String PROP_USERNAME = "username";
    public static final String PROP_PASSWORD = "password";
    public static final String PROP_ACTIVE = "active";
    public static final String PROP_NAME = "name";
    public static final String PROP_SURNAME = "surname";
    public static final String PROP_EMAIL = "email";
    public static final String PROP_ROLE = "role";

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @NotNull
    @Size(max=255)
    private String username;
    
    @NotNull
    @Size(max=255)
    private String password;
    
    @NotNull
    private Boolean active = true;

    @NotNull
    @Size(max=255)
	private String name;
	
    @NotNull
    @Size(max=255)
	private String surname;
	
    @NotNull
    @Size(max=255)
    @Email
	private String email;
    
    @JoinColumn(name="id_role")
    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    private JpaRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the role
     */
    public JpaRole getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(JpaRole role) {
        this.role = role;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
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
        JpaUser other = (JpaUser) obj;
        if (active == null) {
            if (other.active != null)
                return false;
        } else if (!active.equals(other.active))
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id).append(", username=")
                .append(username).append(", password=").append(password)
                .append(", active=").append(active).append(", name=")
                .append(name).append(", surname=").append(surname)
                .append(", email=").append(email).append(", role=").append(role)
                .append("]");
        return builder.toString();
    }
	
}
