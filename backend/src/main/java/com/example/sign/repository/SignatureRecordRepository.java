package com.example.sign.repository;

import com.example.sign.entity.SignatureRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRecordRepository extends JpaRepository<SignatureRecord, Long> {
}
