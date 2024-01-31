package com.example.springboot_vue3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_vue3.entity.Category;
import com.example.springboot_vue3.mapper.CategoryMapper;
import com.example.springboot_vue3.service.ICategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2024-01-28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
