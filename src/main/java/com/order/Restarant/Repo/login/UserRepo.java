package com.order.Restarant.Repo.login;
import com.order.Restarant.model.login.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer>{

    Users findByUsername(String username);
}
