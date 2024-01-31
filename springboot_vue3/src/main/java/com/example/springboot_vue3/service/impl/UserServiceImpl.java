package com.example.springboot_vue3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_vue3.entity.User;
import com.example.springboot_vue3.mapper.UserMapper;
import com.example.springboot_vue3.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2024-01-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
