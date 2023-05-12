package com.tarento.keycloakspring.keycloak;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

@Component
public class KeycloakLogoutHandler implements LogoutHandler {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakLogoutHandler.class);
    private final RestTemplate restTemplate;

    public KeycloakLogoutHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        logoutFromKeycloak((OidcUser) auth.getPrincipal());
    }

    private void logoutFromKeycloak(OidcUser user) {
        String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(endSessionEndpoint)
                .queryParam("id_token_hint", user.getIdToken().getTokenValue());

        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfulley logged out from Keycloak");
        } else {
            logger.error("Could not propagate logout to Keycloak");
        }
    }

//    private void logoutFromKeycloak(OidcUser user) {
//        String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromUriString(endSessionEndpoint)
//                .queryParam("id_token_hint", user.getIdToken().getTokenValue());
//
//        try {
//            ResponseEntity<String> logoutResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
//            if (logoutResponse.getStatusCode().is2xxSuccessful() || logoutResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
//
//                logger.info("Successfully logged out from Keycloak");
//            } else {
//                logger.error("Could not propagate logout to Keycloak. Status code: {}", logoutResponse.getStatusCodeValue());
//            }
//        } catch (HttpClientErrorException e) {
//            logger.error("Could not propagate logout to Keycloak. Status code: {}", e.getStatusCode().value());
//        }
//    }


}


