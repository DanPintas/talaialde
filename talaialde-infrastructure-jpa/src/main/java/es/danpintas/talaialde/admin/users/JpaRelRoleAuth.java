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
import lombok.Data;

@Entity
@Table(name = "rel_role_auth", uniqueConstraints = 
        @UniqueConstraint(columnNames = {"id_role", "id_auth"}))
@Data
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
    
}
