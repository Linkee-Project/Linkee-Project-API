package com.example.linkeeuserservice.user.command.application.service;

public interface UserCommandService {

    void updateNickname(Long userId ,String newNickName);

    void deleteUser(Long userId);
}
