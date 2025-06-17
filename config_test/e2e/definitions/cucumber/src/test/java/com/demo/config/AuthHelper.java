package com.demo.config;

import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import java.net.URI;
import static io.restassured.RestAssured.*;

public class AuthHelper {

    // Leer URLs completas desde variables de entorno (o usar defaults para desarrollo local)
    private static final String APP_BASE_URL =
            System.getenv().getOrDefault("TEST_APP_BASE_URL", "http://localhost:8080");
    private static final String WM_BASE_URL  =
            System.getenv().getOrDefault("TEST_WM_BASE_URL", "http://localhost:8081");

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

        // STEP 2: WireMock maneja /authorize (solo path+query)
        URI authUri = URI.create(authorizeUrl);
        String authorizePath = authUri.getRawPath()
                + (authUri.getRawQuery() != null ? "?" + authUri.getRawQuery() : "");
        Response step2 = given()
                .baseUri(WM_BASE_URL)
                .filter(session)
                .redirects().follow(false)
                .when()
                .get(authorizePath);
        String rawCallback = step2.getHeader("Location");
        System.out.println("[STEP2] rawCallback = " + rawCallback);

        // STEP 3: Callback a la app, capturamos el 302 con la cookie (solo path+query)
        URI callbackUri = URI.create(rawCallback);
        String callbackPath = callbackUri.getRawPath()
                + (callbackUri.getRawQuery() != null ? "?" + callbackUri.getRawQuery() : "");
        Response loginRedirect = given()
                .baseUri(APP_BASE_URL)
                .filter(session)
                .redirects().follow(false)
                .when()
                .get(callbackPath);
        System.out.println("[STEP3] redirect status = " + loginRedirect.getStatusCode());
        System.out.println("[STEP3] redirect location = " + loginRedirect.getHeader("Location"));
        System.out.println("[STEP3] Set-Cookie = " + loginRedirect.getHeader("Set-Cookie"));
        System.out.println("[STEP3] JSESSIONID = " + loginRedirect.getCookie("JSESSIONID"));

        // OPCIONAL: Comprobamos manualmente el home con la sesión autenticada
        String homeLocation = loginRedirect.getHeader("Location");
        URI homeUri = URI.create(homeLocation);
        String homePath = homeUri.getRawPath()
                + (homeUri.getRawQuery() != null ? "?" + homeUri.getRawQuery() : "");
        Response home = given()
                .baseUri(APP_BASE_URL)
                .filter(session)
                .when()
                .get(homePath);
        System.out.println("[STEP3] home status = " + home.getStatusCode());

        return session;
    }
}