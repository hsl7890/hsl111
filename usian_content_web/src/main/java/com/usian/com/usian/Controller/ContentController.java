package com.usian.com.usian.Controller;


import com.usian.Feign.ContentFeign;
import com.usian.pojo.TbContent;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("content")
public class ContentController {
    @Autowired
    private ContentFeign contentFeign;

       @RequestMapping("selectTbContentAllByCategoryId")
    public Result selectTbContentAllByCategoryId(Long categoryId){
        PageResult pageResult = contentFeign.selectTbContentAllByCategoryId(categoryId);
        if(pageResult != null){
            return Result.ok(pageResult);
        }
        return Result.error("查询失败");
    }


    @RequestMapping("/insertTbContent")
    public Result insertTbContent(TbContent tbContent) {
        Integer num = contentFeign.insertTbContent(tbContent);
        if (num != null) {
            return Result.ok();
        }
        return Result.error("添加失败");
    }


    @RequestMapping("/deleteContentByIds")
    public Result deleteContentByIds(Long ids) {
       Integer num = contentFeign.deleteContentByIds(ids);
        if (num != null) {
            return Result.ok();
        }
        return Result.error("删除失败");
    }



}
