package com.example.springboot_vue3.controller.dto;

import lombok.Data;

@Data
public class UpdatePassword {
    private String olePwd; //旧密码
    private String newPwd; //新密码
    private String rePwd; //确认密码

}
