package org.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.itheima.reggie.Dto.SetmealDto;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.Category;
import org.itheima.reggie.entity.Setmeal;
import org.itheima.reggie.service.CategoryService;
import org.itheima.reggie.service.SetmealDishService;
import org.itheima.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Utilities;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealConter {

    @Autowired
    private SetmealService service;
    @Autowired
    private SetmealDishService dishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        service.saveWithDish(setmealDto);
        return R.success("新增成功");
    }


    /*
     *
     * 查询套餐*/

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> objectPage = new Page<>(page, pageSize);
        Page<SetmealDto> objectPage1 = new Page<>();

        LambdaQueryWrapper<Setmeal> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.like(name != null, Setmeal::getName, name);

        objectLambdaQueryWrapper.orderByAsc(Setmeal::getUpdateTime);
        service.page(objectPage, objectLambdaQueryWrapper);

        BeanUtils.copyProperties(objectPage, objectPage1, "records");
        List<Setmeal> records = objectPage.getRecords();
        List<SetmealDto> setmealDtos = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            if (byId != null) {
                String name1 = byId.getName();
                setmealDto.setCategoryName(name1);

            }
            return setmealDto;
        }).collect(Collectors.toList());
        objectPage1.setRecords(setmealDtos);

        return R.success(objectPage1);
    }

    /*
     * 删除套餐*/
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        service.removeWithDish(ids);
        return R.success("删除成功");


    }
}
