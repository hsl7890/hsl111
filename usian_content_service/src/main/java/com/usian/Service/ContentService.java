package com.usian.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbContentMapper;
import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.pojo.TbContentExample;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    public PageResult selectTbContentAllByCategoryId(Long categoryId) {
        PageHelper.startPage(1, 999999);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        return new PageResult(1, pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 根据分类添加内容
     * @param tbContent
     * @return
     */

    public Integer insertTbContent(TbContent tbContent) {
        Date date = new Date();
        tbContent.setUpdated(date);
        tbContent.setCreated(date);
        return tbContentMapper.insertSelective(tbContent);
    }

    public Integer deleteContentByIds(Long ids) {

        return tbContentMapper.deleteByPrimaryKey(ids);
    }


}
