package com.pathbook.pathbook_api.exception;

/** 사용자가 이미 존재하는 경우 예외를 발생시킵니다. */
public class UserAlreadyExistsException extends RecordAlreadyExistsException {
    private final String userId;
    private final String email;

    private UserAlreadyExistsException(String userId, String email) {
        super(createMessage(userId, email));

        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    /**
     * {@code userId}가 중복되는 사용자가 존재하는 경우 예외를 발생시킵니다.
     *
     * @param userId
     * @return {@link UserAlreadyExistsException}
     */
    public static UserAlreadyExistsException withUserId(String userId) {
        return new UserAlreadyExistsException(userId, null);
    }

    /**
     * {@code email}가 중복되는 사용자가 존재하는 경우 예외를 발생시킵니다.
     *
     * @param email
     * @return {@link UserAlreadyExistsException}
     */
    public static UserAlreadyExistsException withEmail(String email) {
        return new UserAlreadyExistsException(null, email);
    }

    private static String createMessage(String userId, String email) {
        if (userId != null && email != null) {
            throw new IllegalArgumentException("Cannot specify both userId and email");
        }

        if (userId != null) {
            return String.format("User already exists with ID: %s", userId);
        }

        if (email != null) {
            return String.format("User already exists with email: %s", email);
        }

        return "User already exists";
    }
}
