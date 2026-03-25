package com.example.sign.controller;

import com.example.sign.dto.SignatureDetailResponse;
import com.example.sign.dto.SignatureSubmitRequest;
import com.example.sign.dto.SignatureSubmitResponse;
import com.example.sign.service.SignatureService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignatureController {

    private final SignatureService signatureService;

    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @PostMapping("/signatures")
    public SignatureSubmitResponse submit(@Valid @RequestBody SignatureSubmitRequest request) {
        return signatureService.submit(request);
    }

    @GetMapping("/signatures/{id}")
    public SignatureDetailResponse detail(@PathVariable Long id) {
        return signatureService.findById(id);
    }
}
