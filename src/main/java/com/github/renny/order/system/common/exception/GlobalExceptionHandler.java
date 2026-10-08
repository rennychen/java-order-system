package com.github.renny.order.system.common.exception;

import com.github.renny.order.system.common.response.ApiResponse;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e){
        log.warn("業務例外: {}",e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));
        return ResponseEntity.badRequest().body(ApiResponse.error("格式錯誤," +errorMessage));
    }

    @ExceptionHandler({
            ObjectOptimisticLockingFailureException.class,
            OptimisticLockException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleOptimisticLockException(Exception e){
        log.warn("資料衝突,{}",e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error("資料已被修改，請重新整理"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegaArgumentException(IllegalArgumentException e){
        log.warn("觸發實體類別業務機制,{}",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("格式錯誤,請重新嘗試"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception e){
        log.error("發生非預期例外: {} | 詳細如下" ,e.toString(), e);
        return ResponseEntity.internalServerError().body(ApiResponse.error("發生非預期例外,請稍後嘗試"));
    }
}
