package com.demo.stepdefs.spotify.authorization;

import io.cucumber.java.en.Given;

public class GivenTheUserHasGrantedPermissionsJavaStepDef {

    @Given("the user has granted permissions in Spotify")
    public void user_has_granted_permissions() {
        // WireMock already stubs a successful token response,
        // so there is nothing extra to prepare here.
    }
}
