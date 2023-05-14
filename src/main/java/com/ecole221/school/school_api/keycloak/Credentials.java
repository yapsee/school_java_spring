package com.ecole221.school.school_api.keycloak;

import org.keycloak.representations.idm.CredentialRepresentation;

public class Credentials {

    //Encoding password

    public static CredentialRepresentation createPassword(String password){
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();

        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        return credentialRepresentation;
    }
}
