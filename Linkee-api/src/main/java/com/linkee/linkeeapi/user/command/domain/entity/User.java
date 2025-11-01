package com.linkee.linkeeapi.user.command.domain.entity;

import com.linkee.linkeeapi.common.enums.Role;
import com.linkee.linkeeapi.common.enums.Status;
import com.linkee.linkeeapi.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@Entity
@Table(
        name = "tb_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_user_email", columnNames = "user_email"),
                @UniqueConstraint(name = "uk_user_nickname", columnNames = "user_nickname")
        }
)
@Getter
@Setter
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

    @Column(name = "user_password", nullable = false, length = 300)
    private String userPassword;

    @Column(name = "user_nickname", nullable = false, length = 10)
    private String userNickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    @Builder.Default
    private Status userStatus = Status.Y;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    @Builder.Default
    private Role userRole = Role.USER;


    public void setEncodedPassword(String encodedPassword) {
        this.userPassword = encodedPassword;
    }

}
