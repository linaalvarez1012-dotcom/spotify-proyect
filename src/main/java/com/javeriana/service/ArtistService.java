package com.javeriana.service;

import com.javeriana.model.Artist;
import com.javeriana.model.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArtistService {

    private List<Artist> artists = new ArrayList<>();

    public void createArtist(String name) {
        artists.add(new Artist(name));
    }

    public boolean removeArtist(UUID id) {
        Artist a = findArtist(id);
        if (a == null) {
            return false;
        }

        return artists.remove(a);
    }

    private Artist findArtist(UUID id) {

        for (Artist artist : artists) {
            if (artist.getId().equals(id)) {
                return artist;
            }
        }

        return null;
    }

    List<Artist> getArtistsFromIds(List<UUID> ids) {
        List<Artist> artists = new ArrayList<>();

        return artists;

    }
}
