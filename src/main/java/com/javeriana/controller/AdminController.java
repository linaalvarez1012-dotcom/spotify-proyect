package com.javeriana.controller;

import java.util.*;
import com.javeriana.model.*;
import com.javeriana.service.ArtistService;

public class AdminController {

    private List<Artist> artists = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private List<Playlist> playlists = new ArrayList<>();

    ArtistService artistService;

    public AdminController(ArtistService artistService) {
        this.artistService = artistService;
    }

    public void createArtist(String name) {
        artists.add(new Artist(name));
    }

    public void removeArtist(String id) {

        if (id.isBlank()) {
            throw new RuntimeException(" ");
        }

        UUID uuid = UUID.fromString(id);

        // eliminar canciones en artist
        songs.removeIf(s -> s.getArtists().contains(a));

        // eliminar canciones de playlists
        for (Playlist p : playlists) {
            p.getSongs().removeIf(s -> s.getArtists().contains(a));
        }

        artistService.removeArtist(uuid);




    }

    public void createSong(String name, String genre, int dur, String artistId) {
        Artist a = findArtist(artistId);
        if (a == null) return;

        List<Artist> list = new ArrayList<>();
        list.add(a);

        songs.add(new Song(name, genre, dur, list));
    }

    public void removeSong(String id) {
        Song s = findSong(id);
        if (s == null) return;

        for (Playlist p : playlists) {
            p.getSongs().remove(s);
        }

        songs.remove(s);
    }

    public void createCustomer(String username, String password, String name, String lastname, int age) {
        Customer c = FindCustomer(customerID);
        if(c == null)
            return;

        List<Customer> list = new ArrayList<>();

        customers.add(new Customer(username,password,name,lastname,age));
    }

    public void removeCustomer(String id) {

        customers.removeIf(c -> c.getId().toString().equals(id));

    }

    public void createPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public void removePlaylist(String id) {
        playlists.removeIf(p -> p.getId().toString().equals(id));
    }
    public void printArtists() {
        for (Artist a : artists) System.out.println(a);
    }

    public void printSongs() {
        for (Song s : songs) System.out.println(s);
    }

    public void printCustomers() {
        for (Customer c : customers) System.out.println(c);
    }

    public void printPlaylists() {
        for (Playlist p : playlists) System.out.println(p);
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