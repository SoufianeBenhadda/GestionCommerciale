package application;

import java.sql.Date;

public class Article {
 private int id;
 private String nom_article;
 private float prix;
 private int quantite;
 private Date created_at;
private String image_article;
 
public String getImage_article() {
	return image_article;
}
public void setImage_article(String image_article) {
	this.image_article = image_article;
}
public Date getCreated_at() {
	return created_at;
}
public void setCreated_at(Date created_at) {
	this.created_at = created_at;
}
public Article(int id, String nom_article, float prix, int quantite, Date created_at, String image_article) {
	super();
	this.id = id;
	this.nom_article = nom_article;
	this.prix = prix;
	this.quantite = quantite;
	this.created_at = created_at;
	this.image_article = image_article;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNom_article() {
	return nom_article;
}
public void setNom_article(String nom_article) {
	this.nom_article = nom_article;
}
public float getPrix() {
	return prix;
}
public void setPrix(float prix) {
	this.prix = prix;
}
public int getQuantite() {
	return quantite;
}
public void setQuantite(int quantite) {
	this.quantite = quantite;
}




}
