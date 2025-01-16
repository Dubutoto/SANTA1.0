package com.example.santa.log.service;

public interface MongoLogServiceInterface {
    void saveLog(Long userId, String role, String action, String details);
}
