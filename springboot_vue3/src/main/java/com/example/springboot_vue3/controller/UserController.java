package com.example.springboot_vue3.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot_vue3.common.Constants;
import com.example.springboot_vue3.common.Result;
import com.example.springboot_vue3.controller.dto.UpdatePassword;
import com.example.springboot_vue3.entity.User;
import com.example.springboot_vue3.service.IUserService;
import com.example.springboot_vue3.utils.Md5Util;
import com.example.springboot_vue3.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-01-28
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody(required = false) User user) {
        String md5String = Md5Util.getMD5String(user.getPassword());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", md5String);
        User one = userService.getOne(queryWrapper);
        if (one != null) {
            //获取token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            one.setAuthorization(token);
            return Result.success(one);
        } else {
            return Result.error(Constants.CODE_400, "用户名或密码错误！");
        }
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody(required = false) User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User one = userService.getOne(queryWrapper);
        if (one == null) {
            String md5String = Md5Util.getMD5String(user.getPassword());
            user.setPassword(md5String);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            userService.save(user);
            return Result.success();
        } else {
            return Result.error(Constants.CODE_401, "用户已存在！");
        }


    }

    //获取个人信息
    @GetMapping("/userinfo")
    public Result userInfo() {
        User currentUser = TokenUtils.getCurrentUser();
        return Result.success(currentUser);
    }

    //更新个人信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        //获取用户信息
        User currentUser = TokenUtils.getCurrentUser();
        user.setUpdateTime(LocalDateTime.now());
        if (currentUser != null) {
            user.setId(currentUser.getId());
            userService.updateById(user);
            return Result.success();
        }
        return Result.error(Constants.CODE_400, "参数错误！");
    }

    //更新头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("avatarUrl") String avatarUrl) {
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null) {
            currentUser.setUserPic(avatarUrl);
            userService.updateById(currentUser);
            return Result.success();
        }
        return Result.error(Constants.CODE_400, "参数错误！");
    }

    //修改密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody UpdatePassword updatePassword) {
        //获取当前用户信息
        User currentUser = TokenUtils.getCurrentUser();
        //新密码
        String newPwd = updatePassword.getNewPwd();
        //确认密码
        String rePwd = updatePassword.getRePwd();
        //md5新密码
        String md5NewPwd = Md5Util.getMD5String(newPwd);
        //md5旧密码
        String md5OldPwd = Md5Util.getMD5String(updatePassword.getOlePwd());
        if (currentUser != null ) {
            if (!currentUser.getPassword().equals(md5OldPwd)){
                return Result.error(Constants.CODE_400, "旧密码不一致！");
            }
            if (!newPwd.equals(rePwd)){
                return Result.error(Constants.CODE_400, "新密码不一致！");
            }
            currentUser.setPassword(md5NewPwd);
            currentUser.setUpdateTime(LocalDateTime.now());
            boolean b = userService.updateById(currentUser);
            //修改成功后重新获取token
            if (b) {
                String authorization = TokenUtils.genToken(currentUser.getId().toString(), currentUser.getPassword());
                return Result.success(authorization);
            }
            return Result.error(Constants.CODE_400, "修改失败！");
        }
        return Result.error(Constants.CODE_401, "用户不存在！");
    }


}

