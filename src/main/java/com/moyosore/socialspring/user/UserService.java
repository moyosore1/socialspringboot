package com.moyosore.socialspring.user;

import com.moyosore.socialspring.exception.ApiRequestException;
import com.moyosore.socialspring.friends.FriendList;
import com.moyosore.socialspring.friends.FriendListService;
import com.moyosore.socialspring.registration.EmailValidator;
import com.moyosore.socialspring.registration.token.ConfirmationToken;
import com.moyosore.socialspring.registration.token.ConfirmationTokenService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// to find users when they try to authenticate with authentication manager
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ConfirmationTokenService confirmationTokenService;
  private final EmailValidator emailValidator;
  private final FriendListService friendListService;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
  }

  public String signupUser(AppUser user) {
    boolean userExists = userRepository.findByEmail(user.getEmail())
        .isPresent();
    if(!emailValidator.test(user.getEmail())){
      throw new ApiRequestException(user.getEmail() + " is not valid.");
    }
    if (userExists) {
      throw new ApiRequestException("Email has already been taken.");
    }

    // encode password set by client and set it to user's password
    String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);

    userRepository.save(user);



    // Create confirmation token
    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = new ConfirmationToken(
        token,
        LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15),
        user
    );
    confirmationTokenService.saveConfirmationToken(confirmationToken);
    // Create an instance for user's friend list
    FriendList friend = new FriendList(user);
    friendListService.saveFriendObj(friend);

    // send
    return token;
  }

  public AppUser currentUser(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
  }

  public int enableAppUser(String email) {
    return userRepository.enableAppUser(email);
  }
}
