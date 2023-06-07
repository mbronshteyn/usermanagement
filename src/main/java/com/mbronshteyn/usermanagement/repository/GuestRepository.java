package com.mbronshteyn.usermanagement.repository;

import com.mbronshteyn.usermanagement.entity.GuestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<GuestEntity, Long> {

    GuestEntity findByBatchNumber(String batchNumber);

    int deleteDistinctByBatchNumber(String batchNumber);

    List<GuestEntity> findAll(Sort sort);
}
