package ru.artem.papyan.metrics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "metric_units")
public class MetricUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "metric_id", nullable = false)
    private Metric metric;

    @Column(name = "metric_value", nullable = false)
    private String metricValue;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}