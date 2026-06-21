package spring_boot_react_auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring_boot_react_auth.entity.User;

public interface UserRepository extends MongoRepository<User, Integer> {}
