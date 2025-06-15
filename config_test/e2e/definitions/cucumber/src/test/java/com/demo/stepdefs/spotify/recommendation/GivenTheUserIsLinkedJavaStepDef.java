package com.demo.stepdefs.spotify.recommendation;

import io.cucumber.java.en.Given;

public class GivenTheUserIsLinkedJavaStepDef {

    @Given("the user has already linked their Spotify account")
    public void user_is_already_linked() {
        // No-op: el stubTokenPort garantiza que existe token para userId=test
    }
}