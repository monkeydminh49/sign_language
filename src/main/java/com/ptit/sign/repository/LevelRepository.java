package com.ptit.sign.repository;

import com.ptit.sign.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByName(String name);
}
