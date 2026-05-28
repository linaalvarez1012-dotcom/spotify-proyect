package com.javeriana.service;

import com.javeriana.model.Customer;
import com.javeriana.model.Playlist;
import com.javeriana.model.Artist;
import com.javeriana.model.Song;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.UUID;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomerService {

    private List<Customer> customers;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }

    public void addCustomer(String username, String password, String name, String lastName, int age) {
        Customer customer = new Customer(username, password, name, lastName, age);
        customers.add(customer);
        System.out.println("Cliente creado: " + customer.getId());
    }

    public Customer FindCustomer(UUID id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void deleteCustomer(UUID id) {
        Customer customer = FindCustomer(id);

        if (customer != null) {
            customers.remove(customer);
            System.out.println("Cliente eliminado");
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    public void showCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No hay clientes");
            return;
        }

        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    public void generarReporteClientesTxt() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("reporte_clientes.txt"))) {

            writer.println("Reporte de clientes\n");

            for (Customer c : customers) {

                int totalCanciones = 0;
                for (Playlist p : c.getPlaylists()) {
                    totalCanciones += p.getSongs().size();
                }

                int totalArtistas = 0;
                if (c.getFollowedArtists() != null) {
                    totalArtistas = c.getFollowedArtists().size();
                }

                Map<Artist, Integer> contador = new HashMap<>();

                for (Playlist p : c.getPlaylists()) {
                    for (Song s : p.getSongs()) {
                        for (Artist a : s.getArtists()) {

                            contador.put(a, contador.getOrDefault(a, 0) + 1);
                        }
                    }
                }

                Artist top = null;
                int max = 0;

                for (Artist a : contador.keySet()) {
                    if (contador.get(a) > max) {
                        max = contador.get(a);
                        top = a;
                    }
                }

                String nombreTop = (top != null) ? top.getName() : "Ninguno";

                writer.println(c.getName());
                writer.println("- Canciones en listas de reproducción: " + totalCanciones);
                writer.println("- Artistas seguidos: " + totalArtistas);
                writer.println("- Artista que más escucha: " + nombreTop);
                writer.println();

            }

            System.out.println("Reporte generado correctamente");

        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    public void generarReporteClientesBinario() {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("reporte_clientes.dat"))) {

            List<String> reporte = new ArrayList<>();

            for (Customer c : customers) {

                int totalCanciones = 0;
                for (Playlist p : c.getPlaylists()) {
                    totalCanciones += p.getSongs().size();
                }

                int totalArtistas = 0;
                if (c.getFollowedArtists() != null) {
                    totalArtistas = c.getFollowedArtists().size();
                }

                Map<Artist, Integer> contador = new HashMap<>();

                for (Playlist p : c.getPlaylists()) {
                    for (Song s : p.getSongs()) {
                        for (Artist a : s.getArtists()) {
                            contador.put(a, contador.getOrDefault(a, 0) + 1);
                        }
                    }
                }

                Artist top = null;
                int max = 0;

                for (Artist a : contador.keySet()) {
                    if (contador.get(a) > max) {
                        max = contador.get(a);
                        top = a;
                    }
                }

                String nombreTop = (top != null) ? top.getName() : "Ninguno";

                String bloque =
                        c.getName() + "\n" +
                                "- Canciones en listas de reproducción: " + totalCanciones + "\n" +
                                "- Artistas seguidos: " + totalArtistas + "\n" +
                                "- Artista que más escucha: " + nombreTop + "\n";

                reporte.add(bloque);
            }

            out.writeObject(reporte);

            System.out.println("Reporte binario guardado");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void generarToptxt(Customer c) {

        if (c == null) {
            return;
        }

        Map<Artist, Integer> contador = new HashMap<>();

        for (Playlist p : c.getPlaylists()) {
            for (Song s : p.getSongs()) {
                for (Artist a : s.getArtists()) {

                    contador.put(a, contador.getOrDefault(a, 0) + 1);
                }
            }
        }

        List<Map.Entry<Artist, Integer>> lista = new ArrayList<>(contador.entrySet());

        lista.sort((a, b) -> b.getValue() - a.getValue());

        String fileName = "reporte_top3 " + c.getName().replace(" ", "_") + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("Top 3 artistas preferidos de " + c.getName() + "\n");

            for (int i = 0; i < Math.min(3, lista.size()); i++) {

                String nombre = lista.get(i).getKey().getName();

                writer.println((i + 1) + ". " + nombre);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}