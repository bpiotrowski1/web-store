package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.UserDto;
import pl.bpiotrowski.webstore.entity.ConfirmationToken;
import pl.bpiotrowski.webstore.entity.Role;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.exception.PasswordNotMatchException;
import pl.bpiotrowski.webstore.repository.RoleRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public void create(UserDto userForm) {
        if(!userForm.getPassword().equals(userForm.getRepeatPassword())) {
            throw new PasswordNotMatchException();
        }
        User user = mapUserDtoToUserEntity(userForm);
        userRepository.save(user);
        confirmationTokenService.sendConfirmTokenToUser(user);
    }

    public void changeEmail(String email, String repeatEmail, Long id) {
        if(email.equals(repeatEmail)) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
            user.setEmail(email);
            userRepository.save(user);
        }
    }

    public void activateUser(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        User user = userRepository.findByEmail(confirmationToken.getUser().getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User " + confirmationToken.getUser().getId() + " not found"));
        user.setActive(true);
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken);
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
