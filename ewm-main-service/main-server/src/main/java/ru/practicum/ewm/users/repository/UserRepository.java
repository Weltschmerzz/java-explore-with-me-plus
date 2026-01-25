package ru.practicum.ewm.users.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.users.model.UserEntity;


import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    UserEntity getUserEntityById(Long id);

    List<UserEntity> findAllByIdIn(Collection<Long> ids, Pageable pageable);

    List<UserEntity> findAllBy(Pageable pageable);

}
