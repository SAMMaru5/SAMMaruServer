package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
    List<User> findByRole(UserAuthority userAuthority);
    List<User> findByGeneration(Integer generationNum);
}
