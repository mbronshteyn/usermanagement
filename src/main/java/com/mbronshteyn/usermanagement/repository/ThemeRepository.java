package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
    @Query(value = "select * from themes th where th.name = :name", nativeQuery = true)
    ThemeEntity findByName(@Param("name") String name);
}
