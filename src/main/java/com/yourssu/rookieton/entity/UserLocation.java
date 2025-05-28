package com.yourssu.rookieton.entity;

import com.yourssu.rookieton.entity.converter.CoordinateConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_USER_LOCATION")
public class UserLocation {
    @Id
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Convert(converter = CoordinateConverter.class)
    private double latitude;

    @Column(nullable = false)
    @Convert(converter = CoordinateConverter.class)
    private double longitude;
}
