package ru.artem.papyan.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artem.papyan.device.management.entity.DeviceEntity;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    Optional<DeviceEntity> findByLegacySystemId(Long legacyId);
}