package com.yourssu.rookieton.dto.request;

import com.yourssu.rookieton.entity.User;
import com.yourssu.rookieton.entity.UserInterest;
import com.yourssu.rookieton.entity.enums.Category;
import com.yourssu.rookieton.entity.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterestRequest {
    private Category category;
    private CategoryType subCategory;
    private List<String> hashtagList;

    public UserInterest toEntity(User user) {
        return UserInterest.builder()
                .user(user)
                .category(this.category)
                .subCategory(this.subCategory)
                .hashtagList(this.hashtagList)
                .build();
    }
}
