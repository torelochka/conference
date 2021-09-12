package ru.zheleznov.api.services;

public interface MailService {
    void sendEmailForConfirm(String email, String code);
}
