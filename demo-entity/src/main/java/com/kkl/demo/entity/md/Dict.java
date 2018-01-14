package com.kkl.demo.entity.md;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Dict {
    private Long id;
    private String label;
    private String value;
    private String type;
    private String description;
    private Integer sort;
    private Integer aloneManagement;
    private String remarks;
}
