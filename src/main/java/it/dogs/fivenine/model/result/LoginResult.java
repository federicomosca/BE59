package it.dogs.fivenine.model.result;

public class LoginResult {
   
   private boolean success;
   private LoginError errorCode;
   private String message;
   private String token;
   private Long userId;
   private String username;

   public LoginResult(boolean success, LoginError errorCode, String message, String token, Long userId, String username) {
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
    this.token = token;
    this.userId = userId;
    this.username = username;
   }
   
   public static LoginResult success(String token, Long userId, String username) {
       return new LoginResult(true, null, "Login successful", token, userId, username);
   }
   
   public static LoginResult failure(LoginError error, String message) {
       return new LoginResult(false, error, message, null, null, null);
   }

   public boolean isSuccess() {
       return success;
   }

   public LoginError getErrorCode() {
       return errorCode;
   }

   public String getMessage() {
       return message;
   }

   public String getToken() {
       return token;
   }

   public Long getUserId() {
       return userId;
   }

   public String getUsername() {
       return username;
   }
}