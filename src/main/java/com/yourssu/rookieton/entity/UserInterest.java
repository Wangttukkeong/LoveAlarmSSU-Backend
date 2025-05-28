package com.yourssu.rookieton.entity;

import com.yourssu.rookieton.entity.enums.Category;
import com.yourssu.rookieton.entity.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_USER_INTEREST", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "sub_category"}))
public class UserInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType subCategory;

    @ElementCollection
    private List<String> hashtagList = new ArrayList<>();
}