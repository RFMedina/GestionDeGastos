package com.gdg.gestiondegastos.exceptions;

public class OperacionEnListaException extends Exception {

    public OperacionEnListaException(String nombre) {
        super("El elemento " + nombre + " está repetido");
    }

}