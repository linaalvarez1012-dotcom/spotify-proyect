package com.javeriana.service;

import com.javeriana.model.Playlist;
import com.javeriana.model.Customer;

import java.util.List;
import java.util.UUID;

public class PlaylistService {

    private List<Playlist> playlists;
    private List<Customer> customers; // para cumplir la regla de negocio

    public PlaylistService(List<Playlist> playlists, List<Customer> customers) {
        this.playlists = playlists;
        this.customers = customers;
    }

    public void addPlaylist(String name) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Nombre inválido");
        }

        Playlist p = new Playlist(name); // ya crea songs vacío
        playlists.add(p);

        System.out.println("Playlist creada: " + p.getId());
    }

    public Playlist findPlaylist(UUID id) {
        for (Playlist p : playlists) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public void deletePlaylist(UUID id) {
        Playlist p = findPlaylist(id);
        if (p == null) return;

        for (Customer c : customers) {
            if (c.getPlaylists() != null) {
                c.getPlaylists().remove(p);
            }
        }

        playlists.remove(p);
        System.out.println("Playlist eliminada");
    }

    public void showPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("No hay playlists");
            return;
        }
        for (Playlist p : playlists) {
            System.out.println(p);
        }
    }
}