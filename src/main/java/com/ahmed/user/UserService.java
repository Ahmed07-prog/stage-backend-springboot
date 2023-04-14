package com.ahmed.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
           User user = userRepository.findUserByEmail(email);
            user.setResetPasswordToken(token);
            userRepository.save(user);
    }
   
  public User getByResetPasswordToken(String token) {
      return userRepository.findByResetPasswordToken(token);
  }   
  public void updatePassword(User user, String newPassword) {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String encodedPassword = passwordEncoder.encode(newPassword);
      user.setPassword(encodedPassword);
      user.setResetPasswordToken(null);
      userRepository.save(user);
  }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAllUsers();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getPhone(), user.getPhone(),user.getRole()))
                .collect(Collectors.toList());
    }


}
