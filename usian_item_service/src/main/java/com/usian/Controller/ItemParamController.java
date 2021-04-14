package com.usian.Controller;

import com.usian.Service.ItemParamService;
import com.usian.pojo.TbItemParam;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("service/itemParam")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("selectItemParamByItemCatId/{itemCatId}")
    public TbItemParam selectItemParamByItemCatId(@PathVariable Long itemCatId){
        return itemParamService.selectItemParamByItemCatId(itemCatId);
    }


    @RequestMapping("selectItemParamAll")
    public PageResult selectItemParamAll(){
        return itemParamService.selectItemParamAll();
    }

    @RequestMapping("insertItemParam")
    public Integer insertItemParam(@RequestBody TbItemParam tbItemParam){
        return itemParamService.insertItemParam(tbItemParam);
    }
    @RequestMapping("deleteItemParamById")
    public Integer deleteItemParamById(@RequestParam Long id){
        return itemParamService.deleteItemParamById(id);
    }
}
