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
    private final OrderHeaderRepository orderHeaderRepository;

    public UserDto findAddressByOrderId(Long id) {
        UserDto dto = new UserDto();
        OrderHeader orderHeader = orderHeaderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order header " + id + " not found"));
        User entity = userRepository.findById(orderHeader.getPurchaser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setStreet(entity.getStreet());
        dto.setHouseNumber(entity.getHouseNumber());

        return dto;
    }

    public void create(UserDto userForm) {
        if(!userForm.getPassword().equals(userForm.getRepeatPassword())) {
            throw new PasswordNotMatchException();
        }
        userRepository.save(mapUserDtoToUserEntity(userForm));
    }

    private User mapUserDtoToUserEntity(UserDto dto) {
        User entity = new User();
        Role userRole = roleRepository.findByTitle("USER");

        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setStreet(dto.getStreet());
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setRole(userRole);

        return entity;
    }
}
