package org.itheima.reggie.Dto;
import lombok.Data;
import org.itheima.reggie.entity.Dish;
import org.itheima.reggie.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
