package com.example.w17_application.entite;

public class User {
    int id;
    String nom;
    String email;
    String password;
    String image_profil;

    String no_civique;
    String street;
    String city;
    String pays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage_profil() {
        return image_profil;
    }

    public void setImage_profil(String image_profil) {
        this.image_profil = image_profil;
    }

    public String getNo_civique() {
        return no_civique;
    }

    public void setNo_civique(String no_civique) {
        this.no_civique = no_civique;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
