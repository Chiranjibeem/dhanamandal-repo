package com.dhanmandal.repository;

import com.dhanmandal.entity.Collections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionsRepository extends JpaRepository<Collections, String> {

    List<Collections> findByNameContainingIgnoreCase(String name);
}
