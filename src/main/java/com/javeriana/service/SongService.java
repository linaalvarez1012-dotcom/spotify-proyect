package com.javeriana.service;

import com.javeriana.model.Song;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SongService {

    private List<Song> songs;

    public SongService() {
        this.songs = new ArrayList<>();
    }

    public void addSong(String name, String genre, int duration, String album, List artists) {
        Song song = new Song(name, genre, duration, album, artists);
        songs.add(song);
        System.out.println("Canción creada: " + song.getId());
    }

    public Song findSong(UUID id) {
        for (Song s : songs) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void deleteSong(UUID id) {
        Song s = findSong(id);

        if (s != null) {
            songs.remove(s);
            System.out.println("Canción eliminada");
        } else {
            System.out.println("Canción no encontrada");
        }
    }

    public void showSongs() {
        for (Song s : songs) {
            System.out.println(s);
        }
    }

    public void cargarSongBin (){
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("songs.dat"))) {

            out.writeObject(songs);

            System.out.println("Songs guardadas en binario");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}