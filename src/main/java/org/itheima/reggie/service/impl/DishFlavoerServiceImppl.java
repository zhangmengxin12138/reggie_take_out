package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itheima.reggie.entity.DishFlavor;
import org.itheima.reggie.mapper.DishFlavorMapper;
import org.itheima.reggie.mapper.DishMapper;
import org.itheima.reggie.service.DishFlavoerService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavoerServiceImppl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavoerService{
}
