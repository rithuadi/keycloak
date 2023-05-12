package com.tarento.keycloakspring.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private RestTemplate restTemplate;




    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello Anonymous");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Hello User");
    }

    @GetMapping("/login")
    public String login() {
        // Redirect the user to the Keycloak login page
        return "login";
//        return "redirect:/auth/realms/<REALM_NAME>/protocol/openid-connect/auth?client_id=<CLIENT_ID>&redirect_uri=<REDIRECT_URI>&response_type=code&scope=openid";
    }

    @GetMapping("/jwtRoles")
    public ResponseEntity<String> jwtRoles(@AuthenticationPrincipal Jwt jwt) {

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        // Get the list of roles from the "realm_access.roles" claim
        List<String> roles = jwt.getClaimAsStringList("realm_access.roles");

        // Print the roles to the console
        System.out.println("Roles: " + roles);

        // Other code...

        return ResponseEntity.ok("Success");
    }


//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest){
//        String url = "http://localhost:8080/realms/SpringbootKeycloak/protocol/openid-connect/token";
//        System.out.println("login api");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> payload = new LinkedMultiValueMap<String, String>();
//        payload.add("client_id", "springboot-keycloak-client");
//        payload.add("password", loginRequest.getPassword());
//        payload.add("grant_type", "password");
//        payload.add("username", loginRequest.getUsername());
//
//
//        HttpEntity<Object> request = new HttpEntity<>(payload, headers);
//        String response = "";
//        response = restTemplate.postForObject(url, payload, String.class);
//        JSONObject jsonObject = new JSONObject(response);
//        System.out.println(jsonObject);
//
//        return "success";
//    }
}
