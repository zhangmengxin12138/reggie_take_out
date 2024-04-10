package org.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.itheima.reggie.Dto.SetmealDto;
import org.itheima.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {


    public void saveWithDish(SetmealDto setmealDto);
/*
*
* 删除套餐
* 和菜品关联*/
    public void removeWithDish(List<Long> ids);
}
