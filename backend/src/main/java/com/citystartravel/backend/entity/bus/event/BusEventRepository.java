package com.citystartravel.backend.entity.bus.event;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusEventRepository extends JpaRepository<BusEvent, Long> {

    Optional<BusEvent> findById(Long busEventId);

    List<BusEvent> findByIdIn(List<Long> busEventId);

    List<BusEvent> findByIdIn(List<Long> busEventId, Sort sort);

    List<BusEvent> findByBusId(Long busId, Sort sort);

    @Override
    BusEvent getOne(Long aLong);
}
