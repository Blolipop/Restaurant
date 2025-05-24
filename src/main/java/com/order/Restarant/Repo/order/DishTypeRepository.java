package com.order.Restarant.Repo.order;

import com.order.Restarant.model.order.DishTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishTypeRepository extends JpaRepository<DishTypeEntity, Long> {
    Optional<DishTypeEntity> findByName(String name);
}
