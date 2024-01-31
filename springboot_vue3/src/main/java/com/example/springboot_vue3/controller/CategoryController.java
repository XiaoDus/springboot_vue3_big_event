package com.example.springboot_vue3.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot_vue3.common.Constants;
import com.example.springboot_vue3.common.Result;
import com.example.springboot_vue3.entity.Category;
import com.example.springboot_vue3.entity.User;
import com.example.springboot_vue3.service.ICategoryService;
import com.example.springboot_vue3.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-01-28
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


    //获取当前已登录用户创建的所有文章分类
    @GetMapping
    public Result category(){
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (currentUser != null) {
            queryWrapper.eq("create_user", currentUser.getId());
            List<Category> categories = categoryService.list(queryWrapper);
            return Result.success(categories);
        }
        return Result.error(Constants.CODE_401,"用户不存在，请重新登录！");

    }

    //新增文章分类
    @PostMapping
    public Result category(@RequestBody(required = false) Category category){
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null) {
            category.setCreateUser(currentUser.getId());
            category.setCreateTime(LocalDateTime.now());
            category.setUpdateTime(LocalDateTime.now());
            categoryService.save(category);
            return Result.success();
        }
        return Result.error(Constants.CODE_401,"用户不存在，请重新登录！");

    }

    //根据ID获取文章分类详情
    @GetMapping("/detail")
    public Result detail(@RequestParam("id") Integer id){
        return Result.success(categoryService.getById(id));
    }

    //根据ID删除文章分类
    @DeleteMapping
    public Result deleteCategory(@RequestParam("id") Integer id){
        return Result.success(categoryService.removeById(id));
    }

}

