package com.yourssu.rookieton.entity;

import com.yourssu.rookieton.entity.converter.DateConverter;
import com.yourssu.rookieton.entity.converter.StringConverter;
import com.yourssu.rookieton.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "TB_USER")
public class User {
    @Id
    private UUID id;

    @Column(nullable = false)
    @Convert(converter = StringConverter.class)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "VARCHAR(191) DEFAULT ''")
    private String emoji;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    @Convert(converter = DateConverter.class)
    private LocalDate birthdate;

    @Column
    private Integer height;

    @Column
    private String department;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInterest> interestList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserLocation userLocation;

    public void updateProfile(String nickname, String phoneNumber, Gender gender, LocalDate birthdate, Integer height, String department, String emoji) {
        this.nickname = (nickname != null ? nickname : this.nickname);
        this.phoneNumber = (phoneNumber != null ? phoneNumber : this.phoneNumber);
        this.gender = (gender != null ? gender : this.gender);
        this.birthdate = (birthdate != null ? birthdate : this.birthdate);
        this.height = height;
        this.department = department;
        this.emoji = (emoji != null ? emoji : this.emoji);
    }

    public void updateAll(User user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.birthdate = user.getBirthdate();
        this.height = user.getHeight();
        this.department = user.getDepartment();
        this.emoji = user.getEmoji();
    }
}
