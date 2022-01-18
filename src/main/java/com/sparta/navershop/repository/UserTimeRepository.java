package com.sparta.navershop.repository;

import com.sparta.navershop.models.User;
import com.sparta.navershop.models.UserTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTimeRepository extends JpaRepository<UserTime, Long> {
    UserTime findByUser(User user);
}
