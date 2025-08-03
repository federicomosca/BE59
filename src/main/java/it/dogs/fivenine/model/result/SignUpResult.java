package it.dogs.fivenine.model.result;

public class SignUpResult {
   
   private boolean success;
   private String errorCode;
   private String message;
   private Long userId;

   public SignUpResult(boolean success, String errorCode, String message, Long userId) {
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
    this.userId = userId;
   }
   
   public static SignUpResult success(Long userId) {
       return new SignUpResult(true, null, "Account created successfully", userId);
   }
   
   public static SignUpResult failure(String errorCode, String message) {
       return new SignUpResult(false, errorCode, message, null);
   }

   public boolean isSuccess() {
       return success;
   }

   public String getErrorCode() {
       return errorCode;
   }

   public String getMessage() {
       return message;
   }

   public Long getUserId() {
       return userId;
   }
}