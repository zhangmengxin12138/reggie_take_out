package org.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.Category;
import org.itheima.reggie.entity.Employee;
import org.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

/*
* 分页查询*/
    @GetMapping("/page")
public R<Page> page(int page, int pageSize) {
    Page<Category> page1 = new Page(page, pageSize);
    LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper();
    lambdaQueryWrapper.orderByAsc(Category::getSort);
    Page page2 =categoryService.page(page1, lambdaQueryWrapper);
    return R.success(page2);
}
/*
* 删除菜品分类*/
@DeleteMapping
    public R<String> delete(long ids){
    categoryService.removeById(ids);
    return R.success("删除成功");
}

}
