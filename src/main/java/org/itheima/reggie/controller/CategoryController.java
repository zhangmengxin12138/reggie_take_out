package org.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itheima.reggie.Dto.DishDto;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.Category;
import org.itheima.reggie.entity.Employee;
import org.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*
     * 新增分类*/
    @PostMapping
    public R<String> save(@RequestBody Category category) {
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
        Page page2 = categoryService.page(page1, lambdaQueryWrapper);
        return R.success(page2);
    }

    /*
     * 删除菜品分类*/
    @DeleteMapping
    public R<String> delete(long ids) {
        categoryService.remove(ids);
        return R.success("删除成功");
    }

/*
修改菜品分类*/

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /*
     *
     * 根据条件查询数据*/
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }


}
