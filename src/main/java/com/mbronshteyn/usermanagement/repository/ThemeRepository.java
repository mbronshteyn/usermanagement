package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
    @Query(value = "select * from themes m where m.name = ?1", nativeQuery = true)
    ThemeEntity findByName(String name);
}
