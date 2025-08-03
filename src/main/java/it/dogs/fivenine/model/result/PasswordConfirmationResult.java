package it.dogs.fivenine.model.result;

public class PasswordConfirmationResult {
   
   private boolean success;
   private PasswordConfirmationError errorCode;
   private String message;

   public PasswordConfirmationResult(boolean success, PasswordConfirmationError errorCode, String message){
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
   }
   
   public static PasswordConfirmationResult success() {
       return new PasswordConfirmationResult(true, null, "Password updated successfully");
   }
   
   public static PasswordConfirmationResult failure(PasswordConfirmationError error, String message) {
       return new PasswordConfirmationResult(false, error, message);
   }

   public boolean isSuccess() {
       return success;
   }

   public PasswordConfirmationError getErrorCode() {
       return errorCode;
   }

   public String getMessage() {
       return message;
   }
}