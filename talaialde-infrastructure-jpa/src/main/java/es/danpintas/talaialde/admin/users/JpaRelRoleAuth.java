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

@Entity
@Table(name = "rel_role_auth", uniqueConstraints = 
        @UniqueConstraint(columnNames = {"id_role", "id_auth"}))
public class JpaRelRoleAuth {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn(name="id_role")
    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    private JpaRole role;

    @JoinColumn(name="id_auth")
    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    private JpaAuth auth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JpaRole getRole() {
        return role;
    }

    public void setRole(JpaRole role) {
        this.role = role;
    }

    public JpaAuth getAuth() {
        return auth;
    }

    public void setAuth(JpaAuth auth) {
        this.auth = auth;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((auth == null) ? 0 : auth.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        JpaRelRoleAuth other = (JpaRelRoleAuth) obj;
        if (auth == null) {
            if (other.auth != null)
                return false;
        } else if (!auth.equals(other.auth))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RelRoleAuth [id=").append(id).append(", role=")
                .append(role).append(", auth=").append(auth).append("]");
        return builder.toString();
    }
    
}
