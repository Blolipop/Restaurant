package com.order.Restarant.Repo.order;


import com.order.Restarant.model.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
        // 只需繼承JpaRepository即可擁有基本操作
}
