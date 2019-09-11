package ru.pasharik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationService {
    final static Logger log = LoggerFactory.getLogger(ValidationService.class);

    public String getFileName(String[] args) {
        if (args.length < 1) {
            log.error("Please specify input file name");
            System.exit(0);
        }
        return args[0];
    }
}
