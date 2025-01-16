package com.example.santa.log.service;

import com.example.santa.log.vo.MongoLog;
import com.example.santa.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class MongoLogService implements MongoLogServiceInterface {

    private final LogRepository logRepository;

    public MongoLogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void saveLog(Long userId, String role, String action, String details) {
        MongoLog log = new MongoLog(userId, role, action, details);
        logRepository.save(log);
    }
}

