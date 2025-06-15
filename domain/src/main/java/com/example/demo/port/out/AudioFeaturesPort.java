package com.example.demo.port.out;

import java.util.List;
import java.util.Map;
import com.example.demo.model.AudioFeatures;

/**
 * Puerto para obtener características de audio de una o varias pistas.
 */
public interface AudioFeaturesPort {
    /**
     * Dado un listado de IDs de Spotify, devuelve un mapa donde la clave es el ID de Spotify
     * y el valor son sus características de audio (desde Reccobeats).
     */
    Map<String, AudioFeatures> getAudioFeatures(List<String> spotifyIds);
}

