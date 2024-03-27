package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.jfr.Threshold;
import org.itheima.reggie.Dto.DishDto;
import org.itheima.reggie.Dto.SetmealDto;
import org.itheima.reggie.entity.Dish;
import org.itheima.reggie.entity.DishFlavor;
import org.itheima.reggie.mapper.DishMapper;
import org.itheima.reggie.service.DishFlavoerService;
import org.itheima.reggie.service.DishService;
import org.itheima.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavoerService dishFlavoerService;

    /*
     *新增菜品，同时保存口味
     * */
    @Override
    @Transactional
    public void saveWithFlavoer(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        dishFlavoerService.saveBatch(flavors);

    }

    /*
     *
     * 根据id查询信息*/
    @Override
    public DishDto getByidWithFlavoer(Long id) {
        Dish byId = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(byId,dishDto);
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id!=null,DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavoerService.list(lambdaQueryWrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }


    /*
    * 根据id修改信息*/
    @Override
    @Transactional
    public void updateWithFlavoer(DishDto dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavoerService.remove(objectLambdaQueryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

dishFlavoerService.saveBatch(flavors);
    }



}
