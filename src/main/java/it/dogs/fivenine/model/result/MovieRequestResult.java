package it.dogs.fivenine.model.result;

public class MovieRequestResult {
   
   private boolean success;
   private String errorCode;
   private String message;
   private Long requestId;

   public MovieRequestResult(boolean success, String errorCode, String message, Long requestId) {
    this.success = success;
    this.errorCode = errorCode;
    this.message = message;
    this.requestId = requestId;
   }
   
   public static MovieRequestResult success(Long requestId) {
       return new MovieRequestResult(true, null, "Movie request submitted successfully", requestId);
   }
   
   public static MovieRequestResult failure(String errorCode, String message) {
       return new MovieRequestResult(false, errorCode, message, null);
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

   public Long getRequestId() {
       return requestId;
   }
}