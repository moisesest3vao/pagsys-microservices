package br.com.pagsys.msusers.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakManager {

    private final KeycloakProperties keycloakProperties;

    public RealmResource getKeyCloakInstanceWithRealm() {
        return keycloakProperties.getInstance().realm(keycloakProperties.getRealm());
    }

//    public String getKeycloakUrl(){
//        keycloakProperties.get
//    }

}