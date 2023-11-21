package how.to.unknownkoderspringsecurity.controller;

import how.to.unknownkoderspringsecurity.model.Role;
import how.to.unknownkoderspringsecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String helloAdminController(){
        return "Admin level access";
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable String roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role newRole) {
        Role savedRole = roleRepository.save(newRole);
        return ResponseEntity.ok(savedRole);
    }

    @PutMapping("/roles/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable String roleId, @RequestBody Role updatedRole) {
        if (!roleRepository.existsById(roleId)) {
            return ResponseEntity.notFound().build();
        }

        updatedRole.setRoleId(roleId);
        Role savedRole = roleRepository.save(updatedRole);
        return ResponseEntity.ok(savedRole);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable String roleId) {
        if (!roleRepository.existsById(roleId)) {
            return ResponseEntity.notFound().build();
        }

        roleRepository.deleteById(roleId);
        return ResponseEntity.noContent().build();
    }
}