package com.javeriana.service;

import com.javeriana.model.Song;
import com.javeriana.model.Artist;
import com.javeriana.service.ArtistService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SongService {

    private List<Song> songs;
    ArtistService artistService = new ArtistService();

    public SongService(ArtistService artistService) {
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

    public void cargarSongsTexto() {

        try (BufferedReader br = new BufferedReader(new FileReader("Song.txt"))) {

            songs.clear();

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(",");

                if (partes.length < 5) continue;

                String name = partes[0].trim();
                String genre = partes[1].trim();

                int duracion;
                try {
                    duracion = Integer.parseInt(partes[2].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                String album = partes[3].trim();
                String nombreArtista = partes[4].trim();

                Artist artista = null;

                for (Artist a : artistService.getArtists()) {
                    if (a.getName().equalsIgnoreCase(nombreArtista)) {
                        artista = a;
                        break;
                    }
                }

                if (artista == null) continue;

                List<Artist> lista = new ArrayList<>();
                lista.add(artista);

                Song s = new Song(name, genre, duracion, album, lista);

                songs.add(s);
            }

            System.out.println("Canciones cargadas correctamente");

        } catch (IOException e) {
            System.out.println("Error al cargar Canciones: " + e.getMessage());
        }
    }

    public void cargarSongsBinario() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("Song.dat"))) {

            songs = (List<Song>) in.readObject();

            System.out.println("Songs cargadas");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void guardarSongsTexto() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("Song.txt"))) {

            for (Song s : songs) {

                String artista = (s.getArtists().isEmpty())
                        ? "Desconocido"
                        : s.getArtists().get(0).getName();

                writer.println(
                        s.getName() + ", " +
                                s.getGenre() + ", " +
                                s.getDurationInSeconds() + ", " +
                                s.getAlbum() + ", " +
                                artista
                );
            }

            System.out.println("Canciones guardadas correctamente");

        } catch (IOException e) {
            System.out.println("Error al guardar canciones: " + e.getMessage());
        }
    }

    public void guardarSongsBinario() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("Song.dat"))) {

            out.writeObject(songs);

            System.out.println("Songs guardadas en binario");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}