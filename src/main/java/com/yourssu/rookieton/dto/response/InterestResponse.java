package com.yourssu.rookieton.dto.response;

import com.yourssu.rookieton.entity.enums.Category;
import com.yourssu.rookieton.entity.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class InterestResponse {
    @Schema(description = "카테고리", example = "SPORTS")
    private Category category;

    @Schema(description = "서브 카테고리", example = "KBO")
    private CategoryType subCategory;

    @Schema(description = "해시태그 목록", example = "[\"축구\", \"농구\"]")
    private List<String> hashtagList;

    public InterestResponse(Category category, CategoryType subCategory, List<String> hashtagList) {
        this.category = category;
        this.subCategory = subCategory;
        this.hashtagList = hashtagList;
    }
}