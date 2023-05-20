package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByBatchNumber(String userId);

    int deleteDistinctByBatchNumber(String userId);

    List<UserEntity> findAll(Sort sort);
}
