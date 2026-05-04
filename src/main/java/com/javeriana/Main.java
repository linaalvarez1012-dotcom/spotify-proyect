package com.javeriana;

import java.util.Scanner;
import com.javeriana.controller.AdminController;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AdminController admin = new AdminController();

        int option;

        do {
            System.out.println("1. Módulo administrador");
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
                    System.out.println("9. Ver lista de clientes");
                    System.out.println("10. Ver lista de canciones");
                    System.out.println("11. Ver lista de artistas");
                    System.out.println("12. Ver lista de playlists");
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

                            admin.printArtists();
                            System.out.print("ID artista: ");
                            String artistId = sc.nextLine();

                            admin.createSong(name, genre, dur, artistId);
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

                        case 9:
                            admin.printCustomers();
                            break;

                        case 10:
                            admin.printSongs();
                            break;

                        case 11:
                            admin.printArtists();
                            break;

                        case 12:
                            admin.printPlaylists();
                            break;
                    }

                } while (op != 0);
            }

        } while (option != 0);

        sc.close();
    }
}
//Prueba git push y pull.