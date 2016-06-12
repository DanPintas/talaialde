package es.dasaur.talaialde.admin.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.admin.users.Auth;
import es.dasaur.talaialde.admin.users.AuthRepository;
import es.dasaur.talaialde.admin.users.RelRoleAuth;
import es.dasaur.talaialde.admin.users.RelRoleAuthRepository;
import es.dasaur.talaialde.admin.users.Role;
import es.dasaur.talaialde.admin.users.RoleRepository;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AuthRepository authRepo;

    @Autowired
    private RelRoleAuthRepository relRepo;

    @Override
    public List<Role> getRoles() {
        return roleRepo.findByOrderById();
    }

    @Override
    public List<Auth> getAuths() {
        return authRepo.findByOrderById();
    }

    @Override
    public Map<Role, List<Auth>> getRelations() {
        Map<Role, List<Auth>> relaciones = new HashMap<>();
        getRoles().forEach(r -> relaciones.put(r, new ArrayList<>()));
        relRepo.findAll().forEach(
                r -> relaciones.get(r.getRole()).add(r.getAuth()));
        return relaciones;
    }

    @Override
    @Transactional(readOnly=false)
    public void saveRelations(Map<Role, List<Auth>> relaciones) {
        List<RelRoleAuth> listaRel = getLRelations(relaciones);
        List<RelRoleAuth> relExistentes = relRepo.findAll();
        
        List<RelRoleAuth> relacionesAEliminar = relExistentes.stream()
                .filter(r -> doesNotInclude(listaRel, r))
                .collect(Collectors.toList());
        relRepo.delete(relacionesAEliminar);
        
        List<RelRoleAuth> relacionesACrear = listaRel.stream()
                .filter(r -> doesNotInclude(relExistentes, r))
                .collect(Collectors.toList());
        relRepo.save(relacionesACrear);
    }

    private boolean doesNotInclude(List<RelRoleAuth> list, 
            RelRoleAuth r) {
        return !list.stream().anyMatch(e -> isRelationEqual(r, e));
    }

    private boolean isRelationEqual(RelRoleAuth r, RelRoleAuth r2) {
        return r.getRole().equals(r2.getRole()) && 
                r.getAuth().equals(r2.getAuth());
    }

    private List<RelRoleAuth> getLRelations(
            Map<Role, List<Auth>> relations) {
        return relations.entrySet().stream().map(this::getLRelationsByRole)
                .flatMap(l -> l.stream()).collect(Collectors.toList());
    }
    
    private List<RelRoleAuth> getLRelationsByRole(
            Entry<Role, List<Auth>> entry) {
        return entry.getValue().stream()
                .map(a -> getRelation(entry.getKey(), a))
                .collect(Collectors.toList());
    }

    private RelRoleAuth getRelation(Role r, Auth a) {
        RelRoleAuth rel = new RelRoleAuth();
        rel.setRole(r);
        rel.setAuth(a);
        return rel;
    }
    

}
