package com.javeriana.exceptions;

public class Exception extends RuntimeException {
    public Exception(String message) {
        super(message);
    }
    public void seguirArtista(int id) throws NotFoundException{
        if(artista == null){
            throw new NotFoundException("El Artista con id" + id + "no existe");
        }
    }
}
