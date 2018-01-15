package com.kkl.demo.entity.md;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class Dict implements Serializable{
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String label;
    @Getter
    @Setter
    private String value;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Integer sort;
    @Getter
    @Setter
    private Integer aloneManagement;
    @Getter
    @Setter
    private String remarks;
}
