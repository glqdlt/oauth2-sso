package com.glqdlt.sso.grantserver.app.res;

import com.glqdlt.sso.grantserver.entity.UserAuthorities;
import com.glqdlt.sso.grantserver.entity.UserEntity;
import com.glqdlt.sso.grantserver.entity.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/userInfo")
    public ResponseEntity findUserGrant(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("authorization").replace("Bearer ", "");

        OAuth2AccessToken accessToken = Optional.ofNullable(tokenStore.readAccessToken(token)).orElseThrow(() -> new RuntimeException("Not Found Token -_-!"));

        OAuth2Authentication loginUser = tokenStore.readAuthentication(accessToken);

        UserEntity user = (UserEntity) loginUser.getPrincipal();

        Optional<UserEntity> optionalUserEntity = userEntityRepo.findByUserId(user.getUserId());
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            UserGrantResponseModel<UserAuthorities> userGrantResponseModel =
                    new UserGrantResponseModel<>(
                            userEntity.getNo(),
                            userEntity.getUserId(),
                            userEntity.getEmail(),
                            userEntity.getUsername(),
                            userEntity.getUserAuthorities()
                    );

            /**
             *
             * @param userDetail
             * 이거 왜 이러냐면, successHandler 에 매핑되는 principal 이 map 으로 그냥 맵핑 된다 -_-; extractor 가 있긴 한데..
             * 그지 같이 설계된거같다. 문서 기준으로 보면 구글이고 페이스북이고 다 매핑하려는 거 아닌가?
             * @see <a href='https://www.baeldung.com/spring-security-oauth-principal-authorities-extractor'>https://www.baeldung.com/spring-security-oauth-principal-authorities-extractor</a>
             * @see com.glqdlt.sso.grantserver.app.secu.SecurityConfig
             *
             * @param userId
             * userId는 아래 에서 쓰임
             * @see com.glqdlt.sso.grantserver.app.secu.SecurityConfig
             * @see org.springframework.security.oauth2.client.registration.ClientRegistration.UserInfoEndpoint.getUserNameAttributeName()
             */
            Map<String, Object> result = new HashMap<>();
            result.put("userDetail", userGrantResponseModel);
            result.put("userId", userGrantResponseModel.getUserId());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
