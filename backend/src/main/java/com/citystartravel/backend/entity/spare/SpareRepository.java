package com.citystartravel.backend.entity.spare;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpareRepository extends JpaRepository<Spare, Long> {

    Optional<Spare> findById(Long spareTypeId);

    List<Spare> findByIdIn(List<Long> busIds);

    List<Spare> findByIdIn(List<Long> busIds, Sort sort);

    List<Spare> findBySpareTypeIdAndAvailable(Long spareTypeId, boolean available);

    List<Spare> findAllBySpareTypeAndAvailable(List<Long> spareTypeIds, boolean available);

    List<Spare> findAllByBusId(Long busId, Sort sort);
}
