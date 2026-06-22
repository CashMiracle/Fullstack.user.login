package spring_boot_react_auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring_boot_react_auth.entity.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
