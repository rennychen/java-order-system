package com.github.renny.order.system.modules.user.service;

import com.github.renny.order.system.common.exception.PasswordNotMatchException;
import com.github.renny.order.system.common.exception.ResourceNotFoundException;
import com.github.renny.order.system.modules.user.dto.request.UpdateUserNameRequest;
import com.github.renny.order.system.modules.user.dto.request.UpdateUserPasswordRequest;
import com.github.renny.order.system.modules.user.dto.response.ReadUserProfileResponse;
import com.github.renny.order.system.modules.user.dto.response.UpdateUserNameResponse;
import com.github.renny.order.system.modules.user.entity.User;
import com.github.renny.order.system.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ReadUserProfileResponse readUserProfile(Long id){
        log.info("準備讀取 user 基本資訊,userId: {}",id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶,請登入後重新嘗試"));
        log.info("user 基本資訊讀取成功");
        return new ReadUserProfileResponse(user.getEmail(),user.getUserName());
    }

    @Transactional
    public UpdateUserNameResponse updateUserName(Long id, UpdateUserNameRequest request){
        log.info("準備更改 userName,userId: {}",id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶,請登入後重新嘗試"));
        user.changeUserName(request.getUserName());
        log.info("userName更新完成");
        return new UpdateUserNameResponse(user.getEmail(),user.getUserName());
    }
    // TODO: 後續補上 token 失效
    @Transactional
    public void updateUserPassword(Long id, UpdateUserPasswordRequest request){
        log.info("準備更改 user 密碼,userId: {}",id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶,請登入後重新嘗試"));
        if(!passwordEncoder.matches(request.getOldPassword(),user.getPassword())){
            throw new PasswordNotMatchException("舊密碼輸入錯誤,請重新嘗試");
        }
        if(!request.getNewPassword().equals(request.getCheckNewPassword())){
            throw new PasswordNotMatchException("新密碼與二次確認密碼不同,請重新嘗試");
        }
        String hashNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.changePassword(hashNewPassword);
        log.info("user 密碼更新完成");
    }
    // TODO: 後續補上 token 失效
    @Transactional
    public void softDeleteUserAccount(Long id){
        log.info("準備軟刪除用戶,userId: {}",id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("找不到該用戶,請登入後重新嘗試"));
        user.markAsDelete();
        log.info("用戶軟刪除狀態:{}",user.isDeleted());
    }
}
