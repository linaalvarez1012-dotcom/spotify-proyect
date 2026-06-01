package com.javeriana.service;

import com.javeriana.model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.io.*;

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

    public void cargarPlaylistsTexto() {

        try (BufferedReader br = new BufferedReader(new FileReader("Playlist.txt"))) {

            playlists.clear();

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) continue;

                String[] partes = linea.split(",");

                if (partes.length < 2) continue;

                String nombrePlaylist = partes[0].trim();

                int numCanciones;

                try {
                    numCanciones = Integer.parseInt(partes[1].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                Playlist p = new Playlist(nombrePlaylist);

                // 🔹 leer canciones
                for (int i = 0; i < numCanciones; i++) {

                    String nombreCancion = br.readLine();

                    if (nombreCancion == null || nombreCancion.isBlank()) continue;

                    nombreCancion = nombreCancion.trim();

                    Song s = new Song(
                            nombreCancion,
                            "Desconocido",   // género default
                            0,               // duración default
                            "Desconocido",   // álbum default
                            new ArrayList<>() // sin artistas
                    );

                    p.getSongs().add(s);
                }

                playlists.add(p);
            }

            System.out.println("Playlists cargadas correctamente");

        } catch (IOException e) {
            System.out.println("Error al cargar playlists: " + e.getMessage());
        }
    }

    public void cargarPlaylistsBinario() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("Playlist.dat"))) {

            playlists = (List<Playlist>) in.readObject();

            System.out.println("Playlists cargadas");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void guardarPlaylistsTexto() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("Playlist.txt"))) {

            for (Playlist p : playlists) {

                writer.println(p.getName() + ", " + p.getSongs().size());

                for (Song s : p.getSongs()) {
                    writer.println(s.getName());
                }
            }

            System.out.println("Playlists guardadas correctamente");

        } catch (IOException e) {
            System.out.println("Error al guardar playlists: " + e.getMessage());
        }
    }

    public void guardarPlaylistsBinario() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("Playlist.dat"))) {

            out.writeObject(playlists);

            System.out.println("Playlists guardadas en binario");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}