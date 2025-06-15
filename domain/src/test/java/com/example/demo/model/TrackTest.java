package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    // AudioFeatures ahora tiene (energy, tempo, valence)
    private final AudioFeatures originalAudio = new AudioFeatures(0.5, 100.0, 0.7);
    private final AudioFeatures newAudio      = new AudioFeatures(0.9, 120.0, 0.2);

    @Test
    void gettersReturnConstructorValues() {
        Track track = new Track("id123", "My Song", "Some Artist", originalAudio);

        assertEquals("id123",       track.getId());
        assertEquals("My Song",     track.getTitle());
        assertEquals("Some Artist", track.getArtist());
        assertSame(originalAudio,   track.getAudio());
    }

    @Test
    void equalsAndHashCode_considerOnlyId() {
        // Mismo id, distinto resto → iguales
        Track a = new Track("idX", "TitleA", "ArtistA", originalAudio);
        Track b = new Track("idX", "TitleB", "ArtistB", newAudio);

        assertEquals(a, b, "Tracks con el mismo id deben ser iguales");
        assertEquals(a.hashCode(), b.hashCode(),
                "HashCodes deben coincidir para el mismo id");
    }

    @Test
    void equalsReturnsFalseForDifferentIdOrTypeOrNull() {
        Track a = new Track("id1", "T", "A", originalAudio);
        Track b = new Track("id2", "T", "A", originalAudio);

        assertNotEquals(a, b,             "Tracks con distinto id no son iguales");
        assertNotEquals(a, null,          "Track no debe ser igual a null");
        assertNotEquals(a, "some string", "Track no debe ser igual a otro tipo");
    }

    @Test
    void withAudioFeatures_returnsNewInstanceWithUpdatedAudio() {
        Track original = new Track("id9", "Song9", "Artist9", originalAudio);
        Track updated  = original.withAudioFeatures(newAudio);

        // Nueva instancia, no muta la original
        assertNotSame(original, updated);

        // Campos inmutables se mantienen
        assertEquals(original.getId(),     updated.getId());
        assertEquals(original.getTitle(),  updated.getTitle());
        assertEquals(original.getArtist(), updated.getArtist());

        // AudioFeatures cambiado en la nueva instancia
        assertSame(newAudio, updated.getAudio());
        // La original sigue intacta
        assertSame(originalAudio, original.getAudio());
    }

    @Test
    void withAudioFeatures_acceptsNullAudio() {
        Track original = new Track("idNull", "NullSong", "NullArtist", originalAudio);
        Track updated  = original.withAudioFeatures(null);

        assertNotNull(updated, "withAudioFeatures nunca retorna null");
        assertNull(updated.getAudio(), "Si paso null, el audio del nuevo Track debe ser null");
        // Original sigue con su AudioFeatures
        assertSame(originalAudio, original.getAudio());
    }
}
