package com.pathbook.pathbook_api.exception;

/** 사용자를 찾을 수 없는 경우 예외를 발생시킵니다. */
public class UserNotFoundException extends RecordNotFoundException {
    private final String userId;
    private final String email;

    private UserNotFoundException(String userId, String email) {
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
     * {@code userId}와 일치하는 사용자가 없는 경우 예외를 발생시킵니다.
     *
     * @param userId
     * @return {@link UserNotFoundException}
     */
    public static UserNotFoundException withUserId(String userId) {
        return new UserNotFoundException(userId, null);
    }

    /**
     * {@code email}와 일치하는 사용자가 없는 경우 예외를 발생시킵니다.
     *
     * @param email
     * @return {@link UserNotFoundException}
     */
    public static UserNotFoundException withEmail(String email) {
        return new UserNotFoundException(null, email);
    }

    private static String createMessage(String userId, String email) {
        if (userId != null && email != null) {
            throw new IllegalArgumentException("Cannot specify both userId and email");
        }

        if (userId != null) {
            return String.format("User not found with ID: %s", userId);
        }

        if (email != null) {
            return String.format("User not found with email: %s", email);
        }

        return "User not found";
    }
}
