package com.order.Restarant.Service;

import com.order.Restarant.Repo.DishRepository;
import com.order.Restarant.Repo.DishTypeRepository;
import com.order.Restarant.model.DishEntity;
import com.order.Restarant.model.DishTypeEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishTypeRepository dishTypeRepository;

    public List<DishEntity> getAllDishes() {
        return  dishRepository.findAll();
    }

    public Object getAllDishTypes() {
        return  dishTypeRepository.findAll();
    }

    public DishTypeEntity getDishTypeByName(String name) {
        System.out.println(name);
        return dishTypeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("找不到類型: " + name));
    }

    public DishTypeEntity createDishType(DishTypeEntity dishType) {
         return dishTypeRepository.save(dishType);
    }

    public DishEntity createDish(DishEntity dish) {
        return dishRepository.save(dish);
    }

    public Object getDishById(Long dishId) {
        Optional<DishEntity> dish = dishRepository.findById(dishId);
        return dish.orElse(null);

    }

    public void updateDish(Long dishId, DishEntity updatedDish) {
        Optional<DishEntity> optionalDish = dishRepository.findById(dishId);

        if (optionalDish.isPresent()) {
            DishEntity existingDish = optionalDish.get();

            // ✅ 從 updatedDish 拿 dishTypeId
            Long dishTypeId = updatedDish.getDishType().getId();
            System.out.println(dishTypeId);
            // ✅ 用這個 id 去 DB 撈出真正的 DishTypeEntity
            DishTypeEntity dishType = dishTypeRepository.findById(dishTypeId)
                    .orElseThrow(() -> new EntityNotFoundException("DishType not found with ID: " + dishTypeId));


            // 更新需要的欄位
            existingDish.setName(updatedDish.getName());
            existingDish.setPrice(updatedDish.getPrice());
            existingDish.setDescription(updatedDish.getDescription());
            existingDish.setDishType(updatedDish.getDishType());
            // 其他欄位也可以照樣更新

            // 儲存更新後的實體
            dishRepository.save(existingDish);
        } else {
            throw new EntityNotFoundException("Dish not found with ID: " + dishId);
        }
    }

    public void deleteDish(Long dishId) {
            Optional<DishEntity> optionalDish = dishRepository.findById(dishId);

            if (optionalDish.isPresent()) {
                dishRepository.delete(optionalDish.get());
            } else {
                throw new EntityNotFoundException("Dish not found with ID: " + dishId);
            }


    }
}
