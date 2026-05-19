package com.javeriana.exceptions;

import com.javeriana.controller.AdminController;

import java.lang.Exception;

public class NotFoundException extends Exception {
    public void seguirArtista(int id) throws NotFoundException{
        if(id; == null){
            throw new NotFoundException("El Artista con id" + id + "no existe");
        }
    }
    public void  EliminarPlaylist(int PlaylistId) throws NotFoundException{
        if(PlaylistId == null){
            throw new NotFoundException("La Playlist de id: " + PlaylistId + " no existe en la lista de usuario")
        }
    }
    public void EliminarArtista(int id) throws new NotFoundException{
        if(id == null){
            throw new NotFoundException()
        }
    }

}
