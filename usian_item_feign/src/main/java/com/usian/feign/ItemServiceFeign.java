package com.usian.feign;

import com.usian.pojo.*;
import com.usian.utils.CatResult;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("usian-item-service")
public interface ItemServiceFeign {

    @RequestMapping("service/item/selectItemInfo")
    TbItem selectItemInfo(@RequestParam Long itemId);

    @RequestMapping("service/item/selectTbItemAllByPage")
    PageResult selectTbItemAllByPage(@RequestParam Integer page, @RequestParam Integer rows);

    @RequestMapping("service/itemCategory/selectItemCategoryByParentId")
    List<TbItemCat> selectItemCategoryByParentId(@RequestParam Integer id);

    @RequestMapping("service/itemParam/selectItemParamByItemCatId/{itemCatId}")
    TbItemParam selectItemParamByItemCatId(@PathVariable Long itemCatId);

    @RequestMapping("service/item/insertTbItem")
    Integer insertTbItem(@RequestBody TbItem tbItem, @RequestParam String desc, @RequestParam String itemParams);

    @RequestMapping("service/item/preUpdateItem")
    Map<String, Object> preUpdateItem(@RequestParam Long itemId);


    @RequestMapping("service/item/updateTbItem")
    Integer updateTbItem(@RequestBody TbItem tbItem, @RequestParam String desc, @RequestParam String itemParams);


    @RequestMapping("service/item/deleteItemById")
    Integer deleteItemById(@RequestParam Long itemId);


    @RequestMapping("service/itemParam/selectItemParamAll")
    PageResult selectItemParamAll();


    @RequestMapping("service/itemParam/insertItemParam")
    Integer insertItemParam(TbItemParam tbItemParam);

    @RequestMapping("service/itemParam/deleteItemParamById")
    Integer deleteItemParamById(@RequestParam Long id);

    @RequestMapping("service/itemCategory/selectItemCategoryAll")
    CatResult selectItemCategoryAll();

    @RequestMapping("service/item/selectItemDescByItemId")
    TbItemDesc selectItemDescByItemId(@RequestParam Long itemId);

    @RequestMapping("service/item/selectTbItemParamItemByItemId")
    TbItemParamItem selectTbItemParamItemByItemId(@RequestParam Long itemId);
}
