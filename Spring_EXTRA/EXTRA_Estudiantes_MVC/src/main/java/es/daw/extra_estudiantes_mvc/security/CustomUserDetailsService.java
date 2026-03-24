package es.daw.extra_estudiantes_mvc.security;

import es.daw.extra_estudiantes_mvc.entity.User;
import es.daw.extra_estudiantes_mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + login));

        return user;
    }
}
