package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.RoleDto;
import pl.bpiotrowski.webstore.entity.Role;
import pl.bpiotrowski.webstore.repository.RoleRepository;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public void create(RoleDto dto) {
        roleRepository.save(mapRoleDtoToRoleEntity(dto));
    }

    private Role mapRoleDtoToRoleEntity(RoleDto dto) {
        Role entity = new Role();
        entity.setTitle(dto.getTitle());
        return entity;
    }
}
