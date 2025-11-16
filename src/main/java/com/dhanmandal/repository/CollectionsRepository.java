package com.dhanmandal.repository;

import com.dhanmandal.entity.Collections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionsRepository extends JpaRepository<Collections, String> {
}
