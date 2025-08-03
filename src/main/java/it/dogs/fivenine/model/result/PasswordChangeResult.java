package it.dogs.fivenine.model.result;

public class PasswordChangeResult {
   
   private boolean success;
   private PasswordChangeError errorCode;
   private String message;

   public PasswordChangeResult(boolean success, PasswordChangeError errorCode, String message) {
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
}
   
   public static PasswordChangeResult success() {
       return new PasswordChangeResult(true, null, "Password change request sent");
   }
   
   public static PasswordChangeResult failure(PasswordChangeError error, String message) {
       return new PasswordChangeResult(false, error, message);
   }

   public boolean isSuccess() {
       return success;
   }

   public PasswordChangeError getErrorCode() {
       return errorCode;
   }

   public String getMessage() {
       return message;
   }
}