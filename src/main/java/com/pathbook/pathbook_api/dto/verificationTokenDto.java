package com.pathbook.pathbook_api.dto;

public class verificationTokenDto {
    private String email;
    private String verificationToken;

    public verificationTokenDto(String email, String verificationToken) {
        this.email = email;
        this.verificationToken = verificationToken;
    }

    private String getEmail() {
        return email;
    }

    private String getVerificationToken() {
        return verificationToken;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}
