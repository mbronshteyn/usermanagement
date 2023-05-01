package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(String userId);

    int deleteDistinctByUserId(String userId);


}
