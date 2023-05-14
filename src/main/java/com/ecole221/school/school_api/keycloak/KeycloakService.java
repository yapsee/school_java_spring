package com.ecole221.school.school_api.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class KeycloakService {
    //realm name
    @Value("${app.keycloak.realm.name}")
    public String realmName;
    //client id
    @Value("${app.keycloak.client-id}")
    public String clientId;
    //Import KeycloakConfig
    private final KeycloakConfig keycloakConfig;

    public KeycloakService(KeycloakConfig keycloakConfig) {
        this.keycloakConfig = keycloakConfig;
    }



    public void addUser(UserDTO userDTO){
        // Call password encoder
        CredentialRepresentation credential = Credentials
                .createPassword(userDTO.getPassword());
        // Mapping User
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getPrenom());
        user.setLastName(userDTO.getNom());
        user.setEmail(userDTO.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        user.setEmailVerified(true);
        // Connect to App realm
        Keycloak keycloak = keycloakConfig.getInstance();
        // Add user to  App realm
        UsersResource usersResource = keycloak.realm(realmName).users();
        usersResource.create(user);
        // Search user on realm
        user = keycloak.realm(realmName).users().search(user.getUsername()).get(0);
        // Fetch added user
        UserResource userResource = keycloak.realm(realmName).users().get(user.getId());
        //RoleRepresentation rp = keycloak.realm(realmName).roles().get("app_admin").toRepresentation();


        //userResource.roles().realmLevel().add(Arrays.asList(rp));

        // Get client
        ClientRepresentation app1Client = keycloak.realm(realmName).clients() //
                .findByClientId("school-app").get(0);
        for (String role: userDTO.getRoles()) {
            // Get client level role (requires view-clients role)
            RoleRepresentation userClientRole = keycloak.realm(realmName).clients()
                    .get(app1Client.getId())
                    .roles().get(role).toRepresentation();

            // Assign client level role to user
            userResource.roles() //
                    .clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));
        }


    }

    public UserRepresentation getUser(String userName){
        UsersResource usersResource = keycloakConfig.getInstance().realm(realmName).users();
        List<UserRepresentation> users = usersResource.search(userName, true);
        return users.size()>0?users.get(0):null;
    }

    public List<UserRepresentation> getUsers(){
        Keycloak kc = keycloakConfig.getInstance();
        RealmResource realmResource = kc.realm(realmName);
        UsersResource userRessource = realmResource.users();
        System.out.println("Count: " + userRessource.count());

        //UsersResource usersResource = keycloakConfig.getInstance().realm(realmName).users();
        //List<UserRepresentation> users = usersResource.list();
        return userRessource.list();
    }
}
