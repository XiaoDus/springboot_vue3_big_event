package com.example.springboot_vue3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 小肚
 * @since 2024-01-28
 */
@Getter
@Setter
  @ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("文章标题")
      private String title;

      @ApiModelProperty("文章内容")
      private String content;

      @ApiModelProperty("文章封面")
      private String coverImg;

      @ApiModelProperty("文章状态: 只能是[已发布] 或者 [草稿]")
      private String state;

      @ApiModelProperty("文章分类ID")
      private Integer categoryId;

      @ApiModelProperty("创建人ID")
      private Integer createUser;

      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;

      @ApiModelProperty("修改时间")
      private LocalDateTime updateTime;


}
