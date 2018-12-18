package com.kkl.demo.auth.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class User implements IJWTInfo{
    private Long id;
    private String name;
}
