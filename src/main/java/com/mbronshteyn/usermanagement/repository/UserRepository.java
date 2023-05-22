package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByBatchNumber(String batchNumber);

    int deleteDistinctByBatchNumber(String batchNumber);

    List<UserEntity> findAll(Sort sort);
}
