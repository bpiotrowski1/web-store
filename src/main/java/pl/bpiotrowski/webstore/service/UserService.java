package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.UserDto;
import pl.bpiotrowski.webstore.entity.Role;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.repository.RoleRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void create(UserDto userForm) {
        userRepository.save(mapUserDtoToUserEntity(userForm));
    }

    private User mapUserDtoToUserEntity(UserDto dto) {
        User entity = new User();
        Role userRole = roleRepository.findByTitle("ADMIN");

        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setRole(userRole);

        return entity;
    }
}
