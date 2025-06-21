package com.pathbook.pathbook_api.dto;

import java.time.LocalDate;

/** 사용자의 정보에 대한 인터페이스입니다. private 필드를 만들어 getter 구현을 포함하여야 합니다. */
public interface UserInfo {
    /**
     * 사용자의 ID입니다.
     *
     * @return Id
     */
    public String getId();

    /**
     * 사용자의 이메일입니다.
     *
     * @return Email
     */
    public String getEmail();

    /**
     * 사용자의 사용자명입니다.
     *
     * @return Username
     */
    public String getUsername();

    /**
     * 사용자의 성별입니다.
     *
     * @return Sex
     */
    public String getSex();

    /**
     * 사용자의 생년월일입니다.
     *
     * @return Birth date
     */
    public LocalDate getBirthDate();

    /**
     * 사용자의 바이오/상태메시지입니다.
     *
     * @return Bio
     */
    public String getBio();
}
