package ru.artem.papyan.metrics.repository;

import ru.artem.papyan.metrics.entity.MetricUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricUnitRepository extends JpaRepository<MetricUnit, Long> {
    
    @Query("SELECT mu FROM MetricUnit mu " +
           "WHERE mu.metric.deviceId = :deviceId " +
           "AND mu.createdAt = (SELECT MAX(mu2.createdAt) FROM MetricUnit mu2 WHERE mu2.metric.id = mu.metric.id)")
    List<MetricUnit> findLatestMetricsByDeviceId(@Param("deviceId") Long deviceId);
}
