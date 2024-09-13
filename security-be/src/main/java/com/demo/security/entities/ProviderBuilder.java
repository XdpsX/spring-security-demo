package com.demo.security.entities;

public class ProviderBuilder {
    public static Provider fromString(String provider) {
        try {
            return Provider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid provider " + provider);
        }
    }
}
