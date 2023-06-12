package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByBatchNumber(String batchNumber);

    int deleteDistinctByBatchNumber(String batchNumber);

    List<UserEntity> findAll(Sort sort);
}
