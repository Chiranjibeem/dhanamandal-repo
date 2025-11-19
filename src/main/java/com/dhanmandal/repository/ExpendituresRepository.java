package com.dhanmandal.repository;

import com.dhanmandal.entity.Collections;
import com.dhanmandal.entity.Expenditures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpendituresRepository extends JpaRepository<Expenditures, String> {

    List<Expenditures> findByDescriptionsContainingIgnoreCase(String descriptions);
}
