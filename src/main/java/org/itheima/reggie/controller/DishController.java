package org.itheima.reggie.controller;

import org.itheima.reggie.Dto.DishDto;
import org.itheima.reggie.common.R;
import org.itheima.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
/*
* ..
* 新增菜品*/
@PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
     dishService.saveWithFlavoer(dishDto);
     return R.success("保存成功");
    }
}
