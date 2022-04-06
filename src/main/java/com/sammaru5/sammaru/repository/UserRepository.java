package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
    List<UserEntity> findByRole(UserAuthority userAuthority);
}
