package com.usian.Controller;

import com.usian.Service.ContentService;
import com.usian.pojo.TbContent;
import com.usian.utils.AdNode;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("service/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("selectTbContentAllByCategoryId")
    public PageResult selectTbContentAllByCatezgoryId(@RequestParam Long categoryId){
        return contentService.selectTbContentAllByCategoryId(categoryId);
    }

    /**
     * 根据分类添加内容
     */
       @RequestMapping("/insertTbContent")
    public Integer insertTbContent(@RequestBody TbContent tbContent){
        return contentService.insertTbContent(tbContent);
    }


    @RequestMapping("/deleteContentByIds")
    public Integer deleteContentByIds(@RequestParam Long ids){
        return contentService.deleteContentByIds(ids);
    }


    /**
     * 查询首页大广告位
     */
    @RequestMapping("/selectFrontendContentByAD")
    public List<AdNode> selectFrontendContentByAD(){
        return contentService.selectFrontendContentByAD();
    }
}





























