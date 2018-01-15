package com.kkl.demo.controller;

import com.github.pagehelper.Page;
import com.kkl.demo.entity.md.Dict;
import com.kkl.demo.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private DictService dictService;

    @GetMapping("/get/{id}")
    public Dict get(@PathVariable("id") Long id){
        return dictService.get(id);
    }


    @GetMapping("/getList")
    public List<Dict> getList(){
        return dictService.getList();
    }

    @GetMapping("/getListByPage/{pageNo}")
    public Page<Dict> getListByPage(@PathVariable("pageNo") int pageNo){
        Page<Dict> returnPage = dictService.getListByPage(pageNo);
        return returnPage;
    }
}
