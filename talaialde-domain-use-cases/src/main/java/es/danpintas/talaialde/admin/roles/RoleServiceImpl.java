package es.danpintas.talaialde.admin.roles;

import es.danpintas.talaialde.admin.users.JpaAuth;
import es.danpintas.talaialde.admin.users.JpaAuthRepository;
import es.danpintas.talaialde.admin.users.JpaRelRoleAuthRepository;
import es.danpintas.talaialde.admin.users.JpaRoleRepository;
import es.danpintas.talaialde.admin.users.JpaRelRoleAuth;
import es.danpintas.talaialde.admin.users.JpaRole;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

  @Autowired
  private JpaRoleRepository roleRepo;

  @Autowired
  private JpaAuthRepository authRepo;

  @Autowired
  private JpaRelRoleAuthRepository relRepo;

  @Override
  public List<JpaRole> getRoles() {
    return roleRepo.findByOrderById();
  }

  @Override
  public List<JpaAuth> getAuths() {
    return authRepo.findByOrderById();
  }

  @Override
  public Map<JpaRole, List<JpaAuth>> getRelations() {
    Map<JpaRole, List<JpaAuth>> relaciones = new HashMap<>();
    getRoles().forEach(r -> relaciones.put(r, new ArrayList<>()));
    relRepo.findAll().forEach(
        r -> relaciones.get(r.getRole()).add(r.getAuth()));
    return relaciones;
  }

  @Override
  @Transactional
  public void saveRelations(Map<JpaRole, List<JpaAuth>> relaciones) {
    List<JpaRelRoleAuth> listaRel = getLRelations(relaciones);
    List<JpaRelRoleAuth> relExistentes = relRepo.findAll();

    List<JpaRelRoleAuth> relacionesAEliminar = relExistentes.stream()
        .filter(r -> doesNotInclude(listaRel, r))
        .collect(Collectors.toList());
    relRepo.deleteAll(relacionesAEliminar);

    List<JpaRelRoleAuth> relacionesACrear = listaRel.stream()
        .filter(r -> doesNotInclude(relExistentes, r))
        .collect(Collectors.toList());
    relRepo.saveAll(relacionesACrear);
  }

  private boolean doesNotInclude(List<JpaRelRoleAuth> list,
      JpaRelRoleAuth r) {
    return !list.stream().anyMatch(e -> isRelationEqual(r, e));
  }

  private boolean isRelationEqual(JpaRelRoleAuth r, JpaRelRoleAuth r2) {
    return r.getRole().equals(r2.getRole()) &&
        r.getAuth().equals(r2.getAuth());
  }

  private List<JpaRelRoleAuth> getLRelations(
      Map<JpaRole, List<JpaAuth>> relations) {
    return relations.entrySet().stream().map(this::getLRelationsByRole)
        .flatMap(l -> l.stream()).collect(Collectors.toList());
  }

  private List<JpaRelRoleAuth> getLRelationsByRole(
      Entry<JpaRole, List<JpaAuth>> entry) {
    return entry.getValue().stream()
        .map(a -> getRelation(entry.getKey(), a))
        .collect(Collectors.toList());
  }

  private JpaRelRoleAuth getRelation(JpaRole r, JpaAuth a) {
    JpaRelRoleAuth rel = new JpaRelRoleAuth();
    rel.setRole(r);
    rel.setAuth(a);
    return rel;
  }


}
