package com.javeriana.controller;

import java.util.*;

import com.javeriana.exceptions.NotFoundException;
import com.javeriana.exceptions.AlreadyExistsException;
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
        if (name == null) {
            throw new IllegalArgumentException("El nombre del artista no puede estar vacio");
        }

        try {
            for (Artist a : artists) {
                if (a.getName().equalsIgnoreCase(name)) {
                    throw new AlreadyExistsException("El artista con nombre " + name + " ya existe");
                }
            }
            artists.add(new Artist(name));

        } catch (AlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeArtist(String id) {

        try {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("El id del artista no puede estar vacío o ser null");
            }

            Artist a = findArtist(id);
            if (a == null) {
                throw new NotFoundException("El artista con id " + id + " no existe");
            }

            songs.removeIf(s -> s.getArtists().contains(a));

            for (Playlist p : playlists) {
                p.getSongs().removeIf(s -> s.getArtists().contains(a));
            }

            UUID uuid = UUID.fromString(id);
            artistService.removeArtist(uuid);

        } catch (IllegalArgumentException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createSong(String name, String genre, int dur, String album, String artistId) {
        try {
            if (name == null || genre == null || album == null ||
                    name.isBlank() || genre.isBlank() || album.isBlank()) {
                throw new IllegalArgumentException("Los campos no pueden estar vacíos o ser null");
            }

            if (dur < 0) {
                throw new IllegalArgumentException("La duración de la canción no puede ser menor a 0");
            }

            if (artistId == null || artistId.isBlank()) {
                throw new IllegalArgumentException("El id de la canción no puede estar vacío o ser null");
            }

            Artist a = findArtist(artistId);
            if (a == null) {
                throw new NotFoundException("El artista con id " + artistId + " no existe");
            }

            List<Artist> list = new ArrayList<>();
            list.add(a);

            songService.addSong(name, genre, dur, album, list);

        } catch (IllegalArgumentException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeSong(String id) {
        try {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("El id de la canción no puede estar vacío o ser null");
            }

            UUID uuid = UUID.fromString(id);

            Song s = songService.findSong(uuid);
            if (s == null) {
                throw new NotFoundException("La canción con id " + id + " no existe");
            }

            for (Playlist p : playlists) {
                p.getSongs().remove(s);
            }

            songService.deleteSong(uuid);

        } catch (IllegalArgumentException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createCustomer(String username, String password, String name, String lastname, int age) {
        try {
            if (username == null || password == null || name == null || lastname == null ||
                    username.isBlank() || password.isBlank() || name.isBlank() || lastname.isBlank()) {
                throw new IllegalArgumentException("Los campos no pueden estar vacíos o ser null");
            }

            if (age <= 14) {
                throw new IllegalArgumentException("La edad debe ser mayor a 14 años");
            }

            customerService.addCustomer(username, password, name, lastname, age);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try{
            for (Customer c : customers) {
                if (c.getUsername().equalsIgnoreCase(username)) {
                    throw new AlreadyExistsException("El cliente con nombre de usuario " + username + " ya existe");
                }
            }
        } catch (IllegalArgumentException | AlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeCustomer(String id) {
        try {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("El id del cliente no puede estar vacío o ser null");
            }
            Customer c = FindCustomer(id);

            if (c == null) {
                throw new NotFoundException("El cliente con id " + id + " no existe");
            }
            UUID uuid = UUID.fromString(id);
            customerService.deleteCustomer(uuid);


        } catch (IllegalArgumentException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createPlaylist(String name) {
        try {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("El nombre de la lista de reproducción no puede estar vacío");
            }

            playlistService.addPlaylist(name);

        } catch (IllegalArgumentException  e) {
            System.out.println(e.getMessage());
        }
    }

    public void removePlaylist(String id) {
        try {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("El id de la playlist no puede estar vacío o ser null");
            }

            UUID uuid = UUID.fromString(id);
            Playlist p = playlistService.findPlaylist(uuid);

            if (p == null) {
                throw new NotFoundException("La playlist con id " + id + " no existe en la lista del usuario");
            }

        } catch (IllegalArgumentException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
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