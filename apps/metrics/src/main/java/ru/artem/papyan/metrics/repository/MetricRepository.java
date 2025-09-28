package ru.artem.papyan.metrics.repository;

import ru.artem.papyan.metrics.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    Optional<Metric> findByDeviceIdAndName(Long deviceId, String name);
}
