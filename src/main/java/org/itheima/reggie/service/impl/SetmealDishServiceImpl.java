package org.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itheima.reggie.Dto.SetmealDto;
import org.itheima.reggie.entity.Setmeal;
import org.itheima.reggie.entity.SetmealDish;
import org.itheima.reggie.mapper.SermealDishMapper;
import org.itheima.reggie.mapper.SetmealMapper;
import org.itheima.reggie.service.SetmealDishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SermealDishMapper, SetmealDish> implements SetmealDishService {


}
