package br.com.pagsys.msusers.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeycloakProperties {
    @Value("${app.config.keycloak.server-url}")
    private String serverUrl;

    @Value("${app.config.keycloak.realm}")
    private String realm;

    @Value("${app.config.keycloak.clientId}")
    private String clientId;

    @Value("${app.config.keycloak.client-secret}")
    private String clientSecret;

    private static Keycloak keycloakInstance = null;

    public Keycloak getInstance() {
        System.out.println(clientId);
        System.out.println(clientSecret);
        System.out.println(realm);
        System.out.println(serverUrl);
        if (keycloakInstance == null) {
            keycloakInstance = KeycloakBuilder
                    .builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType("client_credentials")
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username("admin")
                    .password("admin")
                    .build();
        }
        return keycloakInstance;
    }

    public String getRealm() {
        return realm;
    }
}
