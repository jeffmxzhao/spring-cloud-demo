package com.kkl.demo.entity.md;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class User implements Serializable {
    @Getter
    @Setter
    @ApiModelProperty(name = "用户ID", notes = "用户ID", required = true)
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty(name = "用户姓名", notes = "用户姓名", required = true)
    private String name;

    @Getter
    @Setter
    @ApiModelProperty(name = "用户年龄", notes = "用户年龄")
    private Integer age;

    @Getter
    @Setter
    @ApiModelProperty(name = "用户性别", notes = "用户性别")
    private String gender;
}
