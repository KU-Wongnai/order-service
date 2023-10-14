package ku.cs.kuwongnai.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User create(User user) {
    return userRepository.save(user);
  }

  public User update(User user) {
    User record = userRepository.findById(user.getId()).orElse(null);

    record.setName(user.getName());
    record.setEmail(user.getEmail());
    record.setEmailVerifiedAt(user.getEmailVerifiedAt());
    record.setAvatar(user.getAvatar());

    return userRepository.save(record);
  }
}
