package com.demo.config;

import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthHelper {

    private static final String APP_BASE_URL = "http://localhost:8080";
    private static final String WM_BASE_URL  = "http://localhost:8081";

    public static SessionFilter loginWithSpotify() {
        SessionFilter session = new SessionFilter();

        // STEP 1: Inicio del flujo OAuth2 en la app
        Response step1 = given()
                .baseUri(APP_BASE_URL)
                .filter(session)
                .redirects().follow(false)
                .when()
                .get("/oauth2/authorization/spotify");
        String authorizeUrl = step1.getHeader("Location");
        System.out.println("[STEP1] authorizeUrl = " + authorizeUrl);
        System.out.println("[STEP1] session = " + session.getSessionId());

        // STEP 2: WireMock maneja /authorize
        String authorizePath = authorizeUrl.replace(WM_BASE_URL, "");
        Response step2 = given()
                .baseUri(WM_BASE_URL)
                .filter(session)
                .redirects().follow(false)
                .when()
                .get(authorizePath);
        String rawCallback = step2.getHeader("Location");
        System.out.println("[STEP2] rawCallback = " + rawCallback);

        // STEP 3: Callback a la app, capturamos el 302 con la cookie
        Response loginRedirect = given()
                .baseUri(APP_BASE_URL)
                .filter(session)
                .redirects().follow(false)
                .when()
                .get(rawCallback);
        System.out.println("[STEP3] redirect status = " + loginRedirect.getStatusCode());
        System.out.println("[STEP3] redirect location = " + loginRedirect.getHeader("Location"));
        System.out.println("[STEP3] Set-Cookie = " + loginRedirect.getHeader("Set-Cookie"));
        System.out.println("[STEP3] JSESSIONID = " + loginRedirect.getCookie("JSESSIONID"));

        // OPCIONAL: Comprobamos manualmente el home con la sesión autenticada
        Response home = given()
                .baseUri(APP_BASE_URL)
                .filter(session)
                .when()
                .get(loginRedirect.getHeader("Location"));
        System.out.println("[STEP3] home status = " + home.getStatusCode());

        return session;
    }
}
