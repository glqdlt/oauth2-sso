package com.glqdlt.sso.grantserver.app.res;

import com.glqdlt.sso.grantserver.entity.UserEntity;
import com.glqdlt.sso.grantserver.entity.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jhun
 * 2020-02-07
 */
@RestController
@RequestMapping("/userGrant/api")
public class UserGrantRestController {

    @Autowired
    private UserEntityRepo userEntityRepo;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/{userNo}/grant")
    public ResponseEntity findUserGrant(@PathVariable(name = "userNo") Integer userNo) {

        Optional<UserEntity> optionalUserEntity = userEntityRepo.findById(userNo);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            UserGrantResponseModel userGrantResponseModel = new UserGrantResponseModel();
            userGrantResponseModel.setUserId(userEntity.getUserId());
            userGrantResponseModel.setUserName(userEntity.getName());
            userGrantResponseModel.setUserNo(userEntity.getNo());
            userGrantResponseModel.setUserAuthority(userEntity.getAuthorities().stream().map(x -> (GrantedAuthority) x).collect(Collectors.toList()));
            return ResponseEntity.ok(userGrantResponseModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
