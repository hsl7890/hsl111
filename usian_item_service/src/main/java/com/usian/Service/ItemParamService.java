package com.usian.Service;

import com.usian.mapper.TbItemParamMapper;
import com.usian.pojo.TbItemParam;
import com.usian.pojo.TbItemParamExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    public TbItemParam selectItemParamByItemCatId(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(tbItemParams != null && tbItemParams.size() > 0){
            return tbItemParams.get(0);
        }
        return null;
    }
}
