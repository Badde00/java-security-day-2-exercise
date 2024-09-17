package com.booleanuk.api.repository;

import com.booleanuk.api.model.Borrowable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowableRepository extends JpaRepository<Borrowable, Integer> {
}
