package com.example.OnlineStore.reposetories;

import com.example.OnlineStore.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewesRepo extends JpaRepository<Reviews,Long> {
}
