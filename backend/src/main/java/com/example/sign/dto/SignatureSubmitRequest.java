package com.example.sign.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignatureSubmitRequest {

    @NotBlank(message = "storeCode is required")
    @Size(max = 64, message = "storeCode length must be <= 64")
    private String storeCode;

    @NotBlank(message = "customerName is required")
    @Size(max = 64, message = "customerName length must be <= 64")
    private String customerName;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^1\\d{10}$", message = "phone must be mainland China mobile format")
    private String phone;

    @NotBlank(message = "agreementVersion is required")
    @Size(max = 128, message = "agreementVersion length must be <= 128")
    private String agreementVersion;

    @NotBlank(message = "signatureDataUrl is required")
    private String signatureDataUrl;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAgreementVersion() {
        return agreementVersion;
    }

    public void setAgreementVersion(String agreementVersion) {
        this.agreementVersion = agreementVersion;
    }

    public String getSignatureDataUrl() {
        return signatureDataUrl;
    }

    public void setSignatureDataUrl(String signatureDataUrl) {
        this.signatureDataUrl = signatureDataUrl;
    }
}
