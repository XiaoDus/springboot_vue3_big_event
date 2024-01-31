package com.example.springboot_vue3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 小肚
 * @since 2024-01-28
 */
@Getter
@Setter
  @ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
//      @JsonIgnore //不会把改参数返回给前端，但前端请求时携带该参数无法接收
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //不会把改参数返回给前端，且前端请求时携带该参数可以接收
      private String password;

      @NotEmpty //值不能为空切不能为null
      @Pattern(regexp = "^\\S{1,10}$")
      @ApiModelProperty("昵称")
      private String nickname;

      @NotEmpty
      @Email //满足email格式
      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("头像")
      private String userPic;

      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;

      @ApiModelProperty("修改时间")
      private LocalDateTime updateTime;

      @TableField(exist = false) //声明不在数据库中
      private String Authorization;


}
