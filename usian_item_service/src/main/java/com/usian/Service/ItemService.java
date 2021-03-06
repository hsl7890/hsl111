package com.usian.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.usian.mapper.*;

import com.usian.pojo.*;
import com.usian.utils.IDUtils;
import com.usian.utils.PageResult;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;


    public TbItem getById(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    public PageResult selectTbItemAllByPage(Integer page, Integer rows) {
        // 创建分页插件 pageHelper
        PageHelper.startPage(page, rows);
        TbItemExample example = new TbItemExample();
        // 根据更新时间倒叙查询
        example.setOrderByClause("updated desc");
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte)1);
        // 查询
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItems);
        return new PageResult(page, tbItemPageInfo.getTotal(), tbItemPageInfo.getList());  // page, tbItemPageInfo.getTotal(), tbItemPageInfo.getList()
    }

    public Integer insertTbItem(TbItem tbItem, String desc, String itemParams) {
        Date date = new Date();
        Long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);// 设置ID
        tbItem.setCreated(date);// 设置创建时间
        tbItem.setUpdated(date);// 设置修改时间
        tbItem.setStatus((byte)1);// 设置删除状态为未删除
        // 保存商品
        int i1 = tbItemMapper.insert(tbItem);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        int i2 = tbItemDescMapper.insert(tbItemDesc);// 保存商品描述
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        int i3 = tbItemParamItemMapper.insert(tbItemParamItem);// 保存商品参数

        amqpTemplate.convertAndSend("item_exchage","item.add",itemId);
        return i1 + i2 + i3;
    }

    public Map<String, Object> preUpdateItem(Long itemId) {
        Map<String, Object> map = new HashMap<>();
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        map.put("item", tbItem);
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        map.put("itemDesc", tbItemDesc.getItemDesc());
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(tbItemParamItems != null && tbItemParamItems.size() > 0){
            map.put("itemParamItem", tbItemParamItems.get(0).getParamData());
        }
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
        map.put("itemCat", tbItemCat.getName());
        return map;
    }


    public Integer updateTbItem(TbItem tbItem, String desc, String itemParams) {
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItem.setStatus((byte)1);
        int itemInteger = tbItemMapper.updateByPrimaryKey(tbItem);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(desc);
        int tbItemDescInteger = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);

        TbItemParamItem tbItemParam = new TbItemParamItem();
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(tbItem.getId());
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExample(example);
        if (tbItemParamItems != null && tbItemParamItems.size() > 0){
            tbItemParam.setId(tbItemParamItems.get(0).getId());
            tbItemParam.setCreated(date);
            tbItemParam.setUpdated(date);
            tbItemParam.setParamData(itemParams);
        }
        int i = tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParam);
        return itemInteger + tbItemDescInteger + i;
    }


    public Integer deleteItemById(Long itemId) {
        TbItem tbItem = new TbItem();
        tbItem.setStatus((byte)0);
        tbItem.setId(itemId);
        int integer = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        return integer;
    }

    public TbItemDesc selectItemDescByItemId(Long itemId) {
        return tbItemDescMapper.selectByPrimaryKey(itemId);
    }



    public TbItemParamItem selectTbItemParamItemByItemId(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(tbItemParamItems != null && tbItemParamItems.size() > 0){
            return tbItemParamItems.get(0);
        }
        return null;
    }
}
