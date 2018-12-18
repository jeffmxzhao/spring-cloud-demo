package com.kkl.demo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kkl.demo.provider.user.config.RedisConstant;
import com.kkl.demo.entity.md.Dict;
import com.kkl.demo.mapper.DictMapper;
import com.kkl.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DictService {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DictMapper dictMapper;

    public Dict get(Long id){
        Dict dict;
        if (redisUtils.exists(RedisConstant.RedisDBType.REDIS_DB14, id.toString())) {
            dict = (Dict) redisUtils.get(RedisConstant.RedisDBType.REDIS_DB14, id.toString(), Dict.class);
        } else {
            dict = dictMapper.get(id);
            redisUtils.set(RedisConstant.RedisDBType.REDIS_DB14, id.toString(), dict, 0L);
        }
        return dict;
    }

    public List<Dict> getList(){
        return dictMapper.getList();
    }

    public Page<Dict> getListByPage(int pageNo){
        PageHelper.startPage(pageNo, 10);
        return dictMapper.getListByPage();
    }
}
