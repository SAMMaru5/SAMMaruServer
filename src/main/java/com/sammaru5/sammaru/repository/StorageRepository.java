package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
}
