package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.UserDto;
import pl.bpiotrowski.webstore.entity.OrderHeader;
import pl.bpiotrowski.webstore.entity.Role;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.exception.PasswordNotMatchException;
import pl.bpiotrowski.webstore.repository.OrderHeaderRepository;
import pl.bpiotrowski.webstore.repository.RoleRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void create(UserDto userForm) {
        if(!userForm.getPassword().equals(userForm.getRepeatPassword())) {
            throw new PasswordNotMatchException();
        }
        userRepository.save(mapUserDtoToUserEntity(userForm));
    }

    public void changeEmail(String email, String repeatEmail, Long id) {
        if(email.equals(repeatEmail)) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
            user.setEmail(email);
            userRepository.save(user);
        }
    }

    private User mapUserDtoToUserEntity(UserDto dto) {
        User entity = new User();
        Role userRole = roleRepository.findByTitle("USER");

        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setRole(userRole);

        return entity;
    }
}
