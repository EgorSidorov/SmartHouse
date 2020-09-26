package com.learning.smartHouse.repository;

import com.learning.smartHouse.model.ButtonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ButtonTypeRepository extends JpaRepository<ButtonType,Integer> {
    @Transactional(timeout = 3)
    @Modifying
    @Query("SELECT 1 FROM ButtonType")
    Integer checkLiveStatus();
}
