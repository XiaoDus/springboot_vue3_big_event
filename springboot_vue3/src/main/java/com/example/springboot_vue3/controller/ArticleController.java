package com.example.springboot_vue3.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot_vue3.common.Constants;
import com.example.springboot_vue3.common.Result;
import com.example.springboot_vue3.entity.Article;
import com.example.springboot_vue3.entity.User;
import com.example.springboot_vue3.service.IArticleService;
import com.example.springboot_vue3.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-01-28
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    //新增文章(发布文章)
    @PostMapping
    public Result addArticle(@RequestBody(required = false) Article article) {
        String state = article.getState();
        if (state.equals("已发布") || state.equals("草稿")) {
            User currentUser = TokenUtils.getCurrentUser();
            if (currentUser != null) {
                article.setCreateUser(currentUser.getId());
                article.setCreateTime(LocalDateTime.now());
                article.setUpdateTime(LocalDateTime.now());
                articleService.save(article);
                return Result.success();
            }
            return Result.error(Constants.CODE_401, "用户不存在，请重新登录！");
        }

        return Result.error(Constants.CODE_401, "文章状态: 只能是[已发布] 或者 [草稿]");

    }

    //更新文章信息
    @PutMapping
    public Result updateArticle(@RequestBody(required = false) Article article) {
        String state = article.getState();
        if (StrUtil.isBlank(state) || state.equals("已发布") || state.equals("草稿")) {
            article.setUpdateTime(LocalDateTime.now());
            return Result.success(articleService.updateById(article));
        }
        return Result.error(Constants.CODE_401, "文章状态: 只能是[已发布] 或者 [草稿]");


    }

    //根据ID获取文章详细信息
    @GetMapping("/detail")
    public Result detail(@RequestParam("id") Integer id) {
        return Result.success(articleService.getById(id));
    }

    //根据ID删除文章
    @DeleteMapping
    public Result deleteCategory(@RequestParam("id") Integer id) {
        return Result.success(articleService.removeById(id));
    }

    @GetMapping
    public Result pages(@RequestParam(required = false, defaultValue = "") Integer pageNum,
                        @RequestParam(required = false, defaultValue = "") Integer pageSize,
                        @RequestParam(required = false, defaultValue = "") Integer categoryId,
                        @RequestParam(required = false, defaultValue = "") String state) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (!"".equals(state)) {
            queryWrapper.eq("state", state);
        }
        queryWrapper.orderByDesc("id");
        Page<Article> articlePage = articleService.page(page, queryWrapper);
        return Result.success(articlePage);
    }
}

