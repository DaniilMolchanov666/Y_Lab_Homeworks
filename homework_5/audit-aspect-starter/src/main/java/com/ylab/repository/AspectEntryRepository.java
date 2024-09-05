package com.ylab.repository;

import com.ylab.model.AspectEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AspectEntryRepository extends JpaRepository<AspectEntry, Integer> {
}
