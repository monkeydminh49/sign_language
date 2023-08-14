package com.ptit.demo.repository;

import com.ptit.demo.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByName(String name);
}
