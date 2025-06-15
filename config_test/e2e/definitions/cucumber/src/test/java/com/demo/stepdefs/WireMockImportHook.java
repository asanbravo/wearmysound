package com.demo.stepdefs;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class WireMockImportHook {

    private static final Path MOCKS_ROOT = Paths.get("src/test/resources/mocks");
    private static final Path COMMON_DIR = MOCKS_ROOT.resolve("common");

    private final WireMock wm;

    public WireMockImportHook() {
        String host = System.getenv().getOrDefault("WM_HOST", "localhost");
        int    port = Integer.parseInt(System.getenv().getOrDefault("WM_PORT", "8081"));
        wm = new WireMock(host, port);
    }

    /*──────────── hook ────────────*/
    @Before
    public void loadMappings(Scenario scenario) {

        wm.resetMappings();          // limpia todo

        importDir(COMMON_DIR);       // mappings comunes

        scenario.getSourceTagNames().stream()
                .filter(t -> t.startsWith("@ID-"))
                .findFirst()
                .map(t -> MOCKS_ROOT.resolve(t.substring(1)))
                .filter(Files::exists)
                .ifPresent(this::importDir);
    }

    /*──────────── helpers ────────────*/
    private void importDir(Path dir) {
        System.out.println("WireMock: importing from {}" + dir.toAbsolutePath());
        try (var paths = Files.list(dir)) {
            paths.filter(p -> p.toString().endsWith(".json"))
                    .filter(this::looksLikeMapping)      // solo JSON con request+response
                    .forEach(this::importStub);
        } catch (Exception ex) {
            throw new RuntimeException("Error importing stubs from " + dir, ex);
        }
    }

    /** descarta archivos que son solo cuerpos de respuesta */
    private boolean looksLikeMapping(Path p) {
        try {
            String json = Files.readString(p);
            return json.contains("\"request\"") && json.contains("\"response\"");
        } catch (Exception e) {
            return false;
        }
    }

    private void importStub(Path file) {
        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);

            /* si el stub lleva id no-UUID, elimínalo para que WireMock genere uno */
            if (json.contains("\"id\"") && !json.matches(".*[a-fA-F0-9\\-]{36}.*")) {
                json = json.replaceFirst("\"id\"\\s*:\\s*\"[^\"]+\",?", "");
            }

            StubMapping stub = StubMapping.buildFrom(json);
            wm.register(stub);

            // ←────── Mensaje de log solicitado
            System.out.println("WireMock: imported stub '{}'" + file.toAbsolutePath());

        } catch (Exception ex) {
            throw new RuntimeException(
                    "Error importing stub from file: " + file, ex);
        }
    }
}
