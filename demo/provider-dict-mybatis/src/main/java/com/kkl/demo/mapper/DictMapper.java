package com.kkl.demo.mapper;

import com.github.pagehelper.Page;
import com.kkl.demo.entity.md.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictMapper {
    Dict get(@Param("id") Long id);
    List<Dict> getList();
    Page<Dict> getListByPage();
}
