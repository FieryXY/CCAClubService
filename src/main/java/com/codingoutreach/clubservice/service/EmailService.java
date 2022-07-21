package com.codingoutreach.clubservice.service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailService implements Predicate<String> {

    @Override
    public boolean test(String s) {

        // TODO: Regex to validate email
        return true;
    }
}
