package com.vreijsenj.escalation.car;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class EngineState {

    private Double temperature = 0.0;
    private Severity severity = Severity.CLEAR;

    public enum Severity {
        CLEAR, WARNING, CRITICAL
    }
}
