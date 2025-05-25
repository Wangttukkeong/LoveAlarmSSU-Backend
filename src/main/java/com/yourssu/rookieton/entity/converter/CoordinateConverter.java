package com.yourssu.rookieton.entity.converter;

import com.yourssu.rookieton.security.DataCipher;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class CoordinateConverter implements AttributeConverter<Double, String> {
    private final DataCipher dataCipher;

    @Override
    public String convertToDatabaseColumn(Double raw) {
        try {
            if (raw == null) {
                return null;
            }
            return dataCipher.encrypt(String.valueOf(raw));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double convertToEntityAttribute(String encrypted) {
        try {
            if (encrypted == null || encrypted.isEmpty()) {
                return null;
            }
            String decrypted = dataCipher.decrypt(encrypted);
            return Double.parseDouble(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
