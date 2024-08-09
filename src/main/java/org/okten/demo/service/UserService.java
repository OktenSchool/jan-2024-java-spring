package org.okten.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.demo.dto.auth.SignUpRequestDto;
import org.okten.demo.dto.auth.SignUpResponseDto;
import org.okten.demo.entity.User;
import org.okten.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '%s' not found".formatted(username)));
    }

    @Transactional
    public SignUpResponseDto createUser(SignUpRequestDto signUpRequestDto) {
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        User user = User.builder()
                .username(signUpRequestDto.getUsername())
                .password(password)
                .role(signUpRequestDto.getRole())
                .build();
        User savedUser = userRepository.save(user);

        return SignUpResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .registeredAt(savedUser.getRegisteredAt())
                .build();
    }
}
