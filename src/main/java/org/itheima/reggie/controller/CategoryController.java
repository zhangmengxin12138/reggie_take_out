package org.itheima.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.Category;
import org.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /*
    * 新增分类*/
    @PostMapping
    public R<String> save(@RequestBody Category category){
       categoryService.save(category);
       return R.success("新增分类成功");
    }
}
