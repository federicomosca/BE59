package it.dogs.fivenine.model.result;

public class EmailConfirmationResult {
   
   private boolean success;
   private EmailConfirmationError errorCode;
   private String message;

   public EmailConfirmationResult(boolean success, EmailConfirmationError errorCode, String message){
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
   }
   
   public static EmailConfirmationResult success() {
       return new EmailConfirmationResult(true, null, "Email updated successfully");
   }
   
   public static EmailConfirmationResult failure(EmailConfirmationError error, String message) {
       return new EmailConfirmationResult(false, error, message);
   }

   public boolean isSuccess() {
       return success;
   }

   public EmailConfirmationError getErrorCode() {
       return errorCode;
   }

   public String getMessage() {
       return message;
   }
}