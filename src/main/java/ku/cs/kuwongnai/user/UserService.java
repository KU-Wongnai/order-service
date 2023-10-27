package ku.cs.kuwongnai.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User create(UserRequest user) {
    User record = new User();
    record.setId(user.getId());
    record.setName(user.getName());
    record.setEmail(user.getEmail());
    record.setEmailVerifiedAt(user.getEmailVerifiedAt());
    record.setAvatar(user.getAvatar());

    return userRepository.save(record);
  }

  public User update(UserRequest user) {
    User record = userRepository.findById(user.getId()).orElse(null);

    record.setName(user.getName());
    record.setEmail(user.getEmail());
    record.setEmailVerifiedAt(user.getEmailVerifiedAt());
    record.setAvatar(user.getAvatar());

    return userRepository.save(record);
  }
}
