package com.javeriana.controller;

import java.util.*;

import com.javeriana.exceptions.NotFoundException;
import com.javeriana.model.*;
import com.javeriana.service.ArtistService;
import com.javeriana.service.CustomerService;
import com.javeriana.service.SongService;
import com.javeriana.service.PlaylistService;

public class AdminController {

    private List<Artist> artists = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private List<Playlist> playlists = new ArrayList<>();

    ArtistService artistService;
    CustomerService customerService;
    SongService songService;
    PlaylistService playlistService;

    public AdminController(ArtistService artistService) {
        this.artistService = artistService;
        this.customerService = new CustomerService();
        this.songService = new SongService();
        this.playlistService = new PlaylistService(playlists, customers);
    }

    public void createArtist(String name) {
        artists.add(new Artist(name));
    }

    public void removeArtist(String id) {

        if (id.isBlank()) {
            throw new RuntimeException(" ");
        }

        UUID uuid = UUID.fromString(id);

        Artist a = findArtist(id);
        if (a == null) return;

        songs.removeIf(s -> s.getArtists().contains(a));

        for (Playlist p : playlists) {
            p.getSongs().removeIf(s -> s.getArtists().contains(a));
        }

        artistService.removeArtist(uuid);
    }

    public void createSong(String name, String genre, int dur, String album, String artistId) {

        if (artistId.isBlank()) {
            throw new RuntimeException("ID inválido");
        }

        Artist a = findArtist(artistId);
        if (a == null) return;

        List<Artist> list = new ArrayList<>();
        list.add(a);

        songService.addSong(name, genre, dur, album, list);
    }

    public void removeSong(String id) {

        if (id.isBlank()) {
            throw new NotFoundException("La cancion con id" + id + "no existe");
        }

        UUID uuid = UUID.fromString(id);

        Song s = songService.findSong(uuid);
        if (s == null) return;

        for (Playlist p : playlists) {
            p.getSongs().remove(s);
        }

        songService.deleteSong(uuid);
    }

    public void createCustomer(String username, String password, String name, String lastname, int age) {
        customerService.addCustomer(username, password, name, lastname, age);
    }

    public void removeCustomer(String id) {

        if (id.isBlank()) {
            throw new NotFoundException("El Artista con id:" + id + "no existe");
        }

        UUID uuid = UUID.fromString(id);

        customerService.deleteCustomer(uuid);
    }

    public void createPlaylist(String name) {
        playlistService.addPlaylist(name);
    }

    public void removePlaylist(String id) {

        if (id.isBlank()) {
            throw new NotFoundException("La Playlist de id: " + id + " no existe en la lista de usuario");
        }

        UUID uuid = UUID.fromString(id);

        playlistService.deletePlaylist(uuid);
    }

    public void printArtists() {
        for (Artist a : artists) {
            System.out.println(a);
        }
    }

    public void printPlaylists() {
        playlistService.showPlaylists();
    }

    public void printSongs() {
        for (Song s : songs) System.out.println(s);
    }

    public void printCustomers() {
        for (Customer c : customers) System.out.println(c);
    }

    private Artist findArtist(String id) {
        for (Artist a : artists)
            if (a.getId().toString().equals(id)) return a;
        return null;
    }

    private Song findSong(String id) {
        for (Song s : songs)
            if (s.getId().toString().equals(id)) return s;
        return null;
    }

    private Playlist findPlaylist(String id) {
        for (Playlist p : playlists)
            if (p.getId().toString().equals(id)) return p;
        return null;
    }

    private Customer FindCustomer(String id){
        for(Customer c : customers)
            if (c.getId().toString().equals(id)) return c;
        return null;
    }
}