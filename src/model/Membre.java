package model;

import javafx.beans.property.*;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * Created by Dell on 30/08/2016.
 */
public class Membre {

    private IntegerProperty id;
    private IntegerProperty age;
    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty telephone;
    private StringProperty sexe;
    private StringProperty profession;
    private StringProperty lieuNaissance;
    private StringProperty email;
    private StringProperty adresse;
    private StringProperty situationM;
    private InputStream photo;
    private StringProperty extra;
    private ObjectProperty<LocalDate> dateNaissance;

    public StringProperty adresseProperty() {
        return adresse;
    }

    public Membre() {
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
        this.prenom = new SimpleStringProperty();
        this.age = new SimpleIntegerProperty();
        this.telephone = new SimpleStringProperty();
        this.sexe = new SimpleStringProperty();
        this.profession = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.adresse = new SimpleStringProperty();
        this.situationM = new SimpleStringProperty();

        this.photo = this.getClass().getResourceAsStream("/images/avatar.png");


        this.dateNaissance = new SimpleObjectProperty<>();
        this.lieuNaissance = new SimpleStringProperty();
        this.extra = new SimpleStringProperty();
    }

    public Membre(String nom, String prenom, String telephone, String sexe, String profession, String email, String adresse, InputStream photo, LocalDate naissance, String lieuNaissance, String extra, String situationM) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.telephone = new SimpleStringProperty(telephone);
        this.sexe = new SimpleStringProperty(sexe);
        this.profession = new SimpleStringProperty(profession);
        this.email = new SimpleStringProperty(email);
        this.adresse = new SimpleStringProperty(adresse);
        this.photo = photo;
        this.dateNaissance = new SimpleObjectProperty<LocalDate>(naissance);
        this.lieuNaissance = new SimpleStringProperty(lieuNaissance);
        this.extra = new SimpleStringProperty(extra);
        this.situationM = new SimpleStringProperty(situationM);
        this.age = new SimpleIntegerProperty(14);
    }


    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public String getSexe() {
        return sexe.get();
    }

    public void setSexe(String sexe) {
        this.sexe.set(sexe);
    }

    public StringProperty sexeProperty() {
        return sexe;
    }

    public String getProfession() {
        return profession.get();
    }

    public void setProfession(String profession) {
        this.profession.set(profession);
    }

    public StringProperty professionProperty() {
        return profession;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public StringProperty adresse() {
        return adresse;
    }


    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }


    public LocalDate getDateNaissance() {
        return dateNaissance.get();
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance.set(dateNaissance);
    }

    public ObjectProperty<LocalDate> dateNaissanceProperty() {
        return dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance.get();
    }

    public StringProperty lieuNaissanceProperty() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance.set(lieuNaissance);
    }


    public String getExtra() {
        return extra.get();
    }

    public StringProperty extraProperty() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra.set(extra);
    }

    public String getSituationM() {
        return situationM.get();
    }

    public StringProperty situationMProperty() {
        return situationM;
    }

    public void setSituationM(String situationM) {
        this.situationM.set(situationM);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

}
