package it.dogs.fivenine.model.result;

public class EmailChangeResult {
   
   private boolean success;
   private EmailChangeError errorCode;
   private String message;

   public EmailChangeResult(boolean success, EmailChangeError errorCode, String message) {
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
}
   
   public static EmailChangeResult success() {
       return new EmailChangeResult(true, null, "Email change request sent");
   }
   
   public static EmailChangeResult failure(EmailChangeError error, String message) {
       return new EmailChangeResult(false, error, message);
   }

   public boolean isSuccess() {
       return success;
   }

   public EmailChangeError getErrorCode() {
       return errorCode;
   }

   public String getMessage() {
       return message;
   }
}