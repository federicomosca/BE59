package it.dogs.fivenine.model.result;

public enum EmailConfirmationError {
   TOKEN_NOT_FOUND,
   TOKEN_EXPIRED,
   TOKEN_ALREADY_USED,
   INVALID_TOKEN,
   USER_NOT_FOUND,
   CONFIRMATION_ERROR
}
