package com.linkee.linkeeapi.user.command.domain.entity;

import com.linkee.linkeeapi.common.enums.Role;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "tb_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_user_email", columnNames = "user_email"),
                @UniqueConstraint(name = "uk_user_nickname", columnNames = "user_nickname")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_email", nullable = false, length = 50)
    private String userEmail;

    @Column(name = "user_pw", nullable = false, length = 300)
    private String userPassword;

    @Column(name = "user_nickname", nullable = false, length = 10)
    private String userNickname;

    //네이버 로그인 id를 받아오기 위한 칼럼 추가
    @Column(name = "user_login_id", nullable = false, length = 50)
    private String userLoginId;


    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    @Builder.Default
    private Status userStatus = Status.Y;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    @Builder.Default
    private Role userRole = Role.USER;


    public User(String userLoginId, String userEmail, String userNickname, Role userRole) {
        this.userLoginId = userLoginId;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userPassword = ""; // 소셜 로그인은 비밀번호 X → 빈 문자열 저장
        this.userRole = userRole;
        this.userStatus = Status.Y; // 활성 상태 기본값
    }


    public void modifyUserNickName(String userNickname){
        this.userNickname = userNickname;
    }


    public void deactivateUser() {
        this.userStatus = Status.N;
    }

    public void changePassword(String encode) {
        this.userPassword = encode;
    }
}
