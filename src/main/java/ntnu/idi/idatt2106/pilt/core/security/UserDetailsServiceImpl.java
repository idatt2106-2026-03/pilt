package ntnu.idi.idatt2106.pilt.core.security;

import lombok.RequiredArgsConstructor;
import ntnu.idi.idatt2106.pilt.features.user.model.User;
import ntnu.idi.idatt2106.pilt.features.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    final long userId;
    try {
      userId = Long.parseLong(identifier);
    } catch (NumberFormatException ex) {
      throw new UsernameNotFoundException("Invalid user id format");
    }

    return loadUserById(userId);
  }

  public UserDetails loadUserById(long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new UserPrincipal(user);
  }
}
