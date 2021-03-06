package ru.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zheleznov.impl.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByConfirmedCode(UUID toString);

    List<User> findAllByRole(User.Role role);
}
