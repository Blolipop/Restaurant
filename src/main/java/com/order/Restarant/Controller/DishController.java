package com.order.Restarant.Controller;


import com.order.Restarant.Service.DishService;
import com.order.Restarant.model.DishEntity;
import com.order.Restarant.model.DishTypeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
@RequiredArgsConstructor
@Slf4j
public class DishController {
    //初始化餐點　新增菜單　新增餐點  編輯菜餚
    private final DishService dishService;

    //初始化菜單
    @GetMapping
    public String getDishes(Model model){
        List<DishEntity> dishes  = dishService.getAllDishes();
        model.addAttribute("dishes",dishes);
        return "dishes";
    }

    @GetMapping("/create")//初始化菜單種類
    public String createOrder(Model model){
        model.addAttribute("dish", new DishEntity());
        model.addAttribute("dishTypes", dishService.getAllDishTypes());
        return "create-dish";
    }
    // 執行新增餐點
    @PostMapping("/create")
    public String createDish(@ModelAttribute("dish") DishEntity dish) {
        //自動綁定到前端到後端dish身上

        //getbyname是一個危險的作法 建議直接掛ID
        //先看新增的是什麼 飲料/主餐/單點
        DishTypeEntity dishType = dishService.getDishTypeByName(dish.getDishType().getName());
        if (dishType != null) {

            dish.setDishType(dishType);//有耦合
        } else { //新建一個dishtype
            dishType = new DishTypeEntity();
            dishType.setName(dish.getDishType().getName());//並掛入名字
            //下面這兩段有點怪
            dishType = dishService.createDishType(dishType);//需要把資料存入repo
            dish.setDishType(dishType);//把資料傳進DishEntity.setdishtype
        }
        dishService.createDish(dish);
        return "redirect:/dishes";
    }
    // 編輯餐點表單 透過選擇某個餐點指定內容更改
    @GetMapping("/edit/{dishId}")
    public String editDishForm(@PathVariable Long dishId, Model model) {
        model.addAttribute("dish", dishService.getDishById(dishId));
        model.addAttribute("dishTypes", dishService.getAllDishTypes());
        return "edit-dish";
    }
    // 執行編輯餐點
    @PostMapping("/edit/{dishId}")
    public String editDish(@PathVariable Long dishId, @ModelAttribute("dish") DishEntity updatedDish) {
        dishService.updateDish(dishId, updatedDish);
        return "redirect:/dishes";
    }

    @PostMapping("/delete/{dishId}")
    public String deleteDish(@PathVariable Long dishId) {
        dishService.deleteDish(dishId);
        return "redirect:/dishes";
    }
}



