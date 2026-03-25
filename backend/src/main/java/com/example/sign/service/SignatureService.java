package com.example.sign.service;

import com.example.sign.dto.SignatureDetailResponse;
import com.example.sign.dto.SignatureSubmitRequest;
import com.example.sign.dto.SignatureSubmitResponse;
import com.example.sign.entity.SignatureRecord;
import com.example.sign.repository.SignatureRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class SignatureService {

    private final SignatureRecordRepository repository;

    public SignatureService(SignatureRecordRepository repository) {
        this.repository = repository;
    }

    public SignatureSubmitResponse submit(SignatureSubmitRequest request) {
        SignatureRecord record = new SignatureRecord();
        record.setStoreCode(request.getStoreCode().trim());
        record.setCustomerName(request.getCustomerName().trim());
        record.setPhone(request.getPhone().trim());
        record.setAgreementVersion(request.getAgreementVersion().trim());
        record.setSignatureDataUrl(request.getSignatureDataUrl().trim());

        SignatureRecord saved = repository.save(record);
        return new SignatureSubmitResponse(saved.getId(), saved.getCreatedAt(), "提交成功");
    }

    public SignatureDetailResponse findById(Long id) {
        SignatureRecord record = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("记录不存在: " + id));
        return new SignatureDetailResponse(
                record.getId(),
                record.getStoreCode(),
                record.getCustomerName(),
                record.getPhone(),
                record.getAgreementVersion(),
                record.getSignatureDataUrl(),
                record.getCreatedAt());
    }
}
