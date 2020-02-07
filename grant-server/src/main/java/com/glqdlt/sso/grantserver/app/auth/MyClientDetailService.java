package com.glqdlt.sso.grantserver.app.auth;

import com.glqdlt.sso.grantserver.entity.ClientEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author Jhun
 * 2020-02-06
 */
@Service
public class MyClientDetailService implements ClientDetailsService {

    @Autowired
    private ClientEntityRepo clientEntityRepo;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientEntityRepo.findByName(clientId).orElseThrow(() -> new ClientRegistrationException(String.format("%s Not Founded Client", clientId)));
    }
}
