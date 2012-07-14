/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworldmobile.services;

/**
 *
 * @author ale
 */
public class CalculoDistancias {

    Double latA;
    Double latB;
    Double longA;
    Double longB;
    Double distancia;

    public CalculoDistancias(double latA, double latB, double longA, double longB) {
        Double conver = Math.PI / 180;
        this.latA = latA * conver;
        this.latB = latB * conver;
        this.longA = longA * conver;
        this.longB = longB * conver;
    }

    public Double calcularDistanciaKm() {

        distancia = 6371 * Math.acos(Math.cos(latA) * Math.cos(latB) * Math.cos(longB - longA) + Math.sin(latA) * Math.sin(latB));
        return distancia;
    }

    public Double getlatA() {
        return latA;
    }

    public Double getLatB() {
        return latB;
    }

    public Double getLongA() {
        return longA;
    }

    public Double getLongB() {
        return longB;
    }

    public Double getDistancia() {
        return distancia;
    }
}
