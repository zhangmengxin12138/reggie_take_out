package org.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.itheima.reggie.Dto.DishDto;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.Category;
import org.itheima.reggie.entity.Dish;
import org.itheima.reggie.service.CategoryService;
import org.itheima.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Utilities;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
/*
* ..
* 新增菜品*/
@PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
     dishService.saveWithFlavoer(dishDto);
     return R.success("保存成功");
    }


    /*
    *
    * 分页查询*/

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> page1 = new Page<>(page,pageSize);
        Page<DishDto> dtopage = new Page<>();
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like( name!=null,Dish::getName,name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(page1, lambdaQueryWrapper);
        BeanUtils.copyProperties(page1,dtopage,"records");
        List<Dish> records = page1.getRecords();
        List<DishDto> dishDtos = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            if (byId!=null) {
                String name1 = byId.getName();
                dishDto.setCategoryName(name1);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dtopage.setRecords(dishDtos);

        return R.success(dtopage);
    }
    /*
     *
     * 根据id查询信息*/
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto byidWithFlavoer = dishService.getByidWithFlavoer(id);
return R.success(byidWithFlavoer);
    }



    /*
    *
    * 根据菜品分类id查询菜品*/

    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        objectLambdaQueryWrapper.like(dish.getName()!=null,Dish::getName,dish.getName());
        objectLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByAsc(Dish::getUpdateTime);
        objectLambdaQueryWrapper.eq(Dish::getStatus,1);

        List<Dish> list = dishService.list(objectLambdaQueryWrapper);
        return R.success(list);
    }

/*修改菜品*/
    @PutMapping
    public R<String> upadate(@RequestBody DishDto dishDto){
        dishService.updateWithFlavoer(dishDto);
        return R.success("修改的成功");
    }

}
