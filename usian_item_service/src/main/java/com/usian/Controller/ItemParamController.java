package com.usian.Controller;

import com.usian.Service.ItemParamService;
import com.usian.pojo.TbItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service/itemParam")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("selectItemParamByItemCatId/{itemCatId}")
    public TbItemParam selectItemParamByItemCatId(@PathVariable Long itemCatId){
        return itemParamService.selectItemParamByItemCatId(itemCatId);
    }
}