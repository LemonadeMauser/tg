package com.belov.pavel.repository;

import com.belov.pavel.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataRepo extends JpaRepository<RawData, Long> {
}
