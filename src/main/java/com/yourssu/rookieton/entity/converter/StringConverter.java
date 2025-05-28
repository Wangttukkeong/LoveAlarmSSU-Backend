package com.yourssu.rookieton.entity.converter;

import com.yourssu.rookieton.security.DataCipher;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class StringConverter implements AttributeConverter<String, String> {
    private final DataCipher dataCipher;

    @Override
    public String convertToDatabaseColumn(String raw) {
        try {
            if (raw == null || raw.isEmpty()) {
                return null;
            }
            return dataCipher.encrypt(raw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String encrypted) {
        try {
            if (encrypted == null || encrypted.isEmpty()) {
                return null;
            }
            return dataCipher.decrypt(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
