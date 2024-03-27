package org.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.itheima.reggie.Dto.DishDto;
import org.itheima.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    public void saveWithFlavoer(DishDto dishDto);
}