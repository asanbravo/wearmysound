package com.example.demo.port.out;

import com.example.demo.model.Track;

import java.util.List;

public interface RecentTracksPort {

    /**
     * @return las N últimas canciones (Spotify “recently played”)
     */
    List<Track> fetchRecentTracks(String userId, int limit);
}