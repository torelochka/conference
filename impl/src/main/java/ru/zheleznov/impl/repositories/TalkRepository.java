package ru.zheleznov.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zheleznov.impl.models.Talk;

@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {
}
