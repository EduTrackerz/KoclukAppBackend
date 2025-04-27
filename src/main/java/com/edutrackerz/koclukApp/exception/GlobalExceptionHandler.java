package com.edutrackerz.koclukApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
    * uygulamada oluşan validation hatalarını global olarak yakalar
    * ve frontend'e hata cevabı (JSON) döner.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Eğer bir @Valid anotasyonu hatalı çalışırsa,
     * MethodArgumentNotValidException fırlatılır.
     * Bu metod o hatayı yakalar ve field bazlı açıklayıcı error mesajları döner. (Entity tanımlamaları)
     * @param ex MethodArgumentNotValidException nesnesi
     * @return Alan ismi-hata mesajı eşleşmelerini içeren bir JSON cevabı
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Hatalı olan her alanı döndür
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // Hatalı alanın ismi
            String message = error.getDefaultMessage(); // Hata mesajı
            errors.put(fieldName, message); // map'e koy
        });

        // 400 status kodu ile json olarak frontend'e yolla
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

