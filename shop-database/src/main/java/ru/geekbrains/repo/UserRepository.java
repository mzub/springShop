package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

}
