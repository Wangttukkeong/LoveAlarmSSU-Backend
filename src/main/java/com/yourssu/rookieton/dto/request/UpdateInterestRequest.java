package com.yourssu.rookieton.dto.request;

import com.yourssu.rookieton.entity.enums.Category;
import com.yourssu.rookieton.entity.enums.CategoryType;
import lombok.Data;

import java.util.List;

@Data
public class UpdateInterestRequest {
    private Category category;
    private CategoryType subCategory;
    private List<String> hashtagList;
}
