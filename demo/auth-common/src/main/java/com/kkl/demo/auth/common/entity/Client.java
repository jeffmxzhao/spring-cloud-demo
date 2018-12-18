package com.kkl.demo.auth.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client implements IJWTInfo{
    private Long id;
    private String name;
}
