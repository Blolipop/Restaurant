package com.order.Restarant.Repo.order;

import com.order.Restarant.model.order.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    // 只需繼承JpaRepository即可擁有基本操作
}