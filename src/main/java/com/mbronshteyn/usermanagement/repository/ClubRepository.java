package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.ClubEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClubRepository extends CrudRepository<ClubEntity, Long> {
    ClubEntity findByName(String name);

    Set<ClubEntity> findClubEntitiesByNameIn(Set<String> names);
}
