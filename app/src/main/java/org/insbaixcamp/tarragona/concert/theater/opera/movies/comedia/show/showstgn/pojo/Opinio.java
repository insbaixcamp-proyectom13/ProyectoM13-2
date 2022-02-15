package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Opinio{
    @JsonProperty("data")
    public String getData() {
        return this.data; }
    public void setData(String data) {
        this.data = data; }
    String data;
    @JsonProperty("event")
    public int getEvent() {
        return this.event; }
    public void setEvent(int event) {
        this.event = event; }
    int event;
    @JsonProperty("nomUsuari")
    public String getNomUsuari() {
        return this.nomUsuari; }
    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari; }
    String nomUsuari;
    @JsonProperty("opinio")
    public String getOpinio() {
        return this.opinio; }
    public void setOpinio(String opinio) {
        this.opinio = opinio; }
    String opinio;
    @JsonProperty("puntuacio")
    public int getPuntuacio() {
        return this.puntuacio; }
    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio; }
    int puntuacio;
    @JsonProperty("usuari")
    public String getUsuari() {
        return this.usuari; }
    public void setUsuari(String usuari) {
        this.usuari = usuari; }
    String usuari;

    @Override
    public String toString() {
        return "Opinio{" +
                "data='" + data + '\'' +
                ", event=" + event +
                ", nomUsuari='" + nomUsuari + '\'' +
                ", opinio='" + opinio + '\'' +
                ", puntuacio=" + puntuacio +
                ", usuari='" + usuari + '\'' +
                '}';
    }
}
