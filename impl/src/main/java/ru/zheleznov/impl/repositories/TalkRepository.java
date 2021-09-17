package ru.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zheleznov.impl.models.Talk;
import ru.zheleznov.impl.models.User;

import java.util.List;

@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {
    List<Talk> findAllBySpeakers(User dbUser);
}
