package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itheima.reggie.common.CoustomExcption;
import org.itheima.reggie.entity.Category;
import org.itheima.reggie.entity.Dish;
import org.itheima.reggie.entity.Setmeal;
import org.itheima.reggie.mapper.CategoryMapper;
import org.itheima.reggie.service.CategoryService;
import org.itheima.reggie.service.DishService;
import org.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(lambdaQueryWrapper);
        if (count > 0) {
            throw new CoustomExcption("关联了菜品不能删除");
        }
        LambdaQueryWrapper<Setmeal> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(objectLambdaQueryWrapper);
        if (count1 > 0) {
            throw new CoustomExcption("关联了套餐不能删除");
        }
        super.removeById(id);
    }
}
