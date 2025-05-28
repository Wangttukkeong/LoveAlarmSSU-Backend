package com.yourssu.rookieton.entity.converter;
import com.yourssu.rookieton.security.DataCipher;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Converter
@RequiredArgsConstructor
public class UUIDConverter implements AttributeConverter<UUID, String> {
    private final DataCipher dataCipher;

    @Override
    public String convertToDatabaseColumn(UUID raw) {
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
    public UUID convertToEntityAttribute(String encrypted) {
        try {
            if (encrypted == null || encrypted.isEmpty()) {
                return null;
            }
            String decrypted = dataCipher.decrypt(encrypted);
            return UUID.fromString(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
