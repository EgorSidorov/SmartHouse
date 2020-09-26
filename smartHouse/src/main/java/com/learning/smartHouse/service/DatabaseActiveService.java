package com.learning.smartHouse.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Component
public class DatabaseActiveService {
    public AtomicBoolean active = new AtomicBoolean(true);

    public AtomicBoolean getActive() {
        return active;
    }
}
