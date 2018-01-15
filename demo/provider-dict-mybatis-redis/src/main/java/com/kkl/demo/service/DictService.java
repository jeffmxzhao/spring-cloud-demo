package com.kkl.demo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kkl.demo.entity.md.Dict;
import com.kkl.demo.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DictService {
    @Autowired
    private DictMapper dictMapper;

    public Dict get(Long id){
        return dictMapper.get(id);
    }

    public List<Dict> getList(){
        return dictMapper.getList();
    }

    public Page<Dict> getListByPage(int pageNo){
        PageHelper.startPage(pageNo, 10);
        return dictMapper.getListByPage();
    }
}
