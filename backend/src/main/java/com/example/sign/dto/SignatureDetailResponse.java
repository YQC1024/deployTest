package com.example.sign.dto;

import java.time.LocalDateTime;

public class SignatureDetailResponse {

    private Long id;
    private String storeCode;
    private String customerName;
    private String phone;
    private String agreementVersion;
    private String signatureDataUrl;
    private LocalDateTime createdAt;

    public SignatureDetailResponse(
            Long id,
            String storeCode,
            String customerName,
            String phone,
            String agreementVersion,
            String signatureDataUrl,
            LocalDateTime createdAt) {
        this.id = id;
        this.storeCode = storeCode;
        this.customerName = customerName;
        this.phone = phone;
        this.agreementVersion = agreementVersion;
        this.signatureDataUrl = signatureDataUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAgreementVersion() {
        return agreementVersion;
    }

    public String getSignatureDataUrl() {
        return signatureDataUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
