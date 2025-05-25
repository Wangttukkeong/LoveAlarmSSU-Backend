package com.yourssu.rookieton.entity.converter;

import com.yourssu.rookieton.security.DataCipher;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Converter
@RequiredArgsConstructor
public class DateConverter implements AttributeConverter<LocalDate, String> {
    private final DataCipher dataCipher;

    @Override
    public String convertToDatabaseColumn(LocalDate raw) {
        try {
            if (raw == null) {
                return null;
            }
            return dataCipher.encrypt(raw.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(String encrypted) {
        try {
            if (encrypted == null || encrypted.isEmpty()) {
                return null;
            }
            String decrypted = dataCipher.decrypt(encrypted);
            return LocalDate.parse(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
