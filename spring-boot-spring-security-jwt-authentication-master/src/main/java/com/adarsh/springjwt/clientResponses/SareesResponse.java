package com.adarsh.springjwt.clientResponses;

public class SareesResponse {
    private int id;
    private String sareetype;
    private int sareesmrp;
    private int sareesprice;
    private int sareesquantity;
    private int pieces;

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSareetype() {
        return sareetype;
    }

    public void setSareetype(String sareetype) {
        this.sareetype = sareetype;
    }

    public int getSareesmrp() {
        return sareesmrp;
    }

    public void setSareesmrp(int sareesmrp) {
        this.sareesmrp = sareesmrp;
    }

    public int getSareesprice() {
        return sareesprice;
    }

    public void setSareesprice(int sareesprice) {
        this.sareesprice = sareesprice;
    }

    public int getSareesquantity() {
        return sareesquantity;
    }

    public void setSareesquantity(int sareesquantity) {
        this.sareesquantity = sareesquantity;
    }
}
