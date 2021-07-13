package br.com.baseapi.security.token;

import br.com.baseapi.security.UserSystem;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> addInfo = new HashMap<>();

        UserSystem userSystem = (UserSystem) authentication.getPrincipal();
        addInfo.put("name", userSystem.getUser().getEmail());
        addInfo.put("email", userSystem.getUser().getEmail());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
        return accessToken;
    }
}
