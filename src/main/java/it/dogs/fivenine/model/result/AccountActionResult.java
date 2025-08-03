package it.dogs.fivenine.model.result;

public class AccountActionResult {
   
   private boolean success;
   private AccountActionError errorCode;
   private String message;
   private String action;

   public AccountActionResult(boolean success, AccountActionError errorCode, String message, String action) {
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
    this.action = action;
   }
   
   public static AccountActionResult success(String action, String message) {
       return new AccountActionResult(true, null, message, action);
   }
   
   public static AccountActionResult failure(String action, AccountActionError error, String message) {
       return new AccountActionResult(false, error, message, action);
   }

   public boolean isSuccess() {
       return success;
   }

   public AccountActionError getErrorCode() {
       return errorCode;
   }

   public String getMessage() {
       return message;
   }

   public String getAction() {
       return action;
   }
}