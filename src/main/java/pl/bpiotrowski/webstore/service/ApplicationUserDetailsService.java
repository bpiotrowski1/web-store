package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.entity.Role;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.repository.RoleRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(entity.getUsername()).password(entity.getPassword()).roles(entity.getRole().getTitle())
                .build();
    }

}
