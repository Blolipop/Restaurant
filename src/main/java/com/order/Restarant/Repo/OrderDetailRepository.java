package com.order.Restarant.Repo;

import com.order.Restarant.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    // 只需繼承JpaRepository即可擁有基本操作
}