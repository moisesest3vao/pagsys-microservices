package br.com.pagsys.msusers.service;

import br.com.pagsys.msusers.config.KeycloakManager;
import br.com.pagsys.msusers.dto.GetUserByTokenResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class KeycloakUserService {

    @Autowired
    private KeycloakManager keyCloakManager;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.config.keycloak.server-url}")
    String keycloakServerUrl;


    public Integer createUser(UserRepresentation userRepresentation) {
        Response response = keyCloakManager.getKeyCloakInstanceWithRealm().users().create(userRepresentation);
        return response.getStatus();
    }

    public void updateUser(UserRepresentation userRepresentation) {
        keyCloakManager.getKeyCloakInstanceWithRealm().users().get(userRepresentation.getId()).update(userRepresentation);
    }


    public List<UserRepresentation> readUserByEmail(String email) {
        return keyCloakManager.getKeyCloakInstanceWithRealm().users().search(email);
    }

    public Integer deleteUserByEmail(String email) {
        List<UserRepresentation> users = keyCloakManager.getKeyCloakInstanceWithRealm().users().search(email);

        if(!users.isEmpty()){
            UserRepresentation userRepresentation = users.get(0);
            String id = userRepresentation.getId();

            keyCloakManager.getKeyCloakInstanceWithRealm().users().delete(id);
            return 0;
        }
        return 1;
    }

    public GetUserByTokenResponse readUserByToken(String token) {
        String url = keycloakServerUrl + "/realms/pagsysrealm/protocol/openid-connect/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        System.out.println(token);
        headers.set("authorization", token);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<GetUserByTokenResponse> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                GetUserByTokenResponse.class,
                1
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }
}