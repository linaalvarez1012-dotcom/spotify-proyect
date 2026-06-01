package com.javeriana;

import java.util.Scanner;
import com.javeriana.controller.AdminController;
import com.javeriana.service.ArtistService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArtistService artistService = new ArtistService();
        AdminController admin = new AdminController(artistService);

        int option;

        do {
            System.out.println("1. Módulo administrador");
            System.out.println("2. Guardar información");
            System.out.println("3. Cargar información");
            System.out.println("4. Módulo reportes");
            System.out.println("0. Salir");

            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                int op;
                do {
                    System.out.println("\n1. Crear artista");
                    System.out.println("2. Eliminar artista");
                    System.out.println("3. Crear canción");
                    System.out.println("4. Eliminar canción");
                    System.out.println("5. Crear cliente");
                    System.out.println("6. Eliminar cliente");
                    System.out.println("7. Crear playlist");
                    System.out.println("8. Eliminar playlist");
                    System.out.println("0. Volver al menu principal");

                    op = sc.nextInt();
                    sc.nextLine();

                    switch (op) {

                        case 1:
                            System.out.print("Nombre artista: ");
                            admin.createArtist(sc.nextLine());
                            break;

                        case 2:
                            admin.printArtists();
                            System.out.print("ID: ");
                            admin.removeArtist(sc.nextLine());
                            break;

                        case 3:
                            System.out.print("Nombre canción: ");
                            String name = sc.nextLine();
                            System.out.print("Género: ");
                            String genre = sc.nextLine();
                            System.out.print("Duración: ");
                            int dur = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Álbum: ");
                            String album = sc.nextLine();

                            admin.printArtists();
                            System.out.print("ID artista: ");
                            String artistId = sc.nextLine();

                            admin.createSong(name, genre, dur, album, artistId);
                            break;

                        case 4:
                            admin.printSongs();
                            System.out.print("ID: ");
                            admin.removeSong(sc.nextLine());
                            break;

                        case 5:
                            System.out.print("Username: ");
                            String u = sc.nextLine();
                            System.out.print("Password: ");
                            String p = sc.nextLine();
                            System.out.print("Nombre: ");
                            String n = sc.nextLine();
                            System.out.print("Apellido: ");
                            String l = sc.nextLine();
                            System.out.print("Edad: ");
                            int age = sc.nextInt();
                            sc.nextLine();

                            admin.createCustomer(u, p, n, l, age);
                            break;

                        case 6:
                            admin.printCustomers();
                            System.out.print("ID: ");
                            admin.removeCustomer(sc.nextLine());
                            break;

                        case 7:
                            System.out.print("Nombre playlist: ");
                            admin.createPlaylist(sc.nextLine());
                            break;

                        case 8:
                            admin.printPlaylists();
                            System.out.print("ID: ");
                            admin.removePlaylist(sc.nextLine());
                            break;
                    }

                } while (op != 0);
            }

            else if (option == 2) {

                System.out.println("\n¿QUÉ DESEA GUARDAR?");
                System.out.println("1. Artistas");
                System.out.println("2. Canciones");
                System.out.println("3. Clientes");
                System.out.println("4. Playlists");

                int A = sc.nextInt();
                sc.nextLine();

                System.out.println("\n¿CÓMO GUARDAR?");
                System.out.println("1. Texto");
                System.out.println("2. Binario");
                System.out.println("3. Ambos");

                int formato = sc.nextInt();
                sc.nextLine();

                switch (A) {

                    case 1:
                        if (formato == 1 || formato == 3)
                            admin.guardarArtistasTexto();
                        if (formato == 2 || formato == 3)
                            admin.guardarArtistasBinario();
                        break;

                    case 2:
                        if (formato == 1 || formato == 3)
                            admin.guardarSongsTexto();
                        if (formato == 2 || formato == 3)
                            admin.guardarSongsBinario();
                        break;

                    case 3:
                        if (formato == 1 || formato == 3)
                            admin.guardarCustomersTexto();
                        if (formato == 2 || formato == 3)
                            admin.guardarCustomersBinario();
                        break;

                    case 4: // PLAYLISTS
                        if (formato == 1 || formato == 3)
                            admin.guardarPlaylistsTexto();
                        if (formato == 2 || formato == 3)
                            admin.guardarPlaylistsBinario();
                        break;
                }
            }

            else if (option == 3) {

                System.out.println("\n¿QUÉ DESEA CARGAR?");
                System.out.println("1. Artistas");
                System.out.println("2. Canciones");
                System.out.println("3. Clientes");
                System.out.println("4. Playlists");

                int B = sc.nextInt();
                sc.nextLine();

                System.out.println("\n¿DESDE DÓNDE?");
                System.out.println("1. Texto");
                System.out.println("2. Binario");

                int formato = sc.nextInt();
                sc.nextLine();

                switch (B) {

                    case 1:
                        if (formato == 1)
                            admin.cargarArtistasTexto();
                        else
                            admin.cargarArtistasBinario();
                        break;

                    case 2:
                        if (formato == 1)
                            admin.cargarSongsTexto();
                        else
                            admin.cargarSongsBinario();
                        break;

                    case 3:
                        if (formato == 1)
                            admin.cargarCustomersTexto();
                        else
                            admin.cargarCustomersBinario();
                        break;

                    case 4:
                        if (formato == 1)
                            admin.cargarPlaylistsTexto();
                        else
                            admin.cargarPlaylistsBinario();
                        break;
                }
            }

            else if (option == 4) {

                System.out.println("\n1. Reporte de clientes");
                System.out.println("2. Top 3 artistas por cliente");
                System.out.println("3. Reporte de artistas");

                int C = sc.nextInt();
                sc.nextLine();

                switch (C) {

                    case 1:
                        admin.generarReporteClientesTxt();
                        break;

                    case 2:
                        admin.printCustomers();
                        System.out.print("ID cliente: ");
                        admin.generarToptxt(sc.nextLine());
                        break;

                    case 3:
                        admin.generarReporteArtistas();
                        break;
                }
            }

        } while (option != 0);

        sc.close();
    }
}