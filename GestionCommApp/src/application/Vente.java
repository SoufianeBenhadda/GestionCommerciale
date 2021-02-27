package application;

import java.util.Date;

public class Vente {
	private int id;
	
	public String produit;

	private int client;
	private float prixVente;
	private int quantite;
	private Date dateVente;
	private double total;
	private float benifice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduit() {
		return produit;
	}
	public void setProduit(String produit) {
		this.produit = produit;
	}
	
	
	@Override
	public String toString() {
		return "Vente [id=" + id + ", article=" + produit + ", client=" + client + ", prixVente=" + prixVente
				+ ", quantite=" + quantite + ", dateVente=" + dateVente + ", total=" + total + ", benifice=" + benifice
				+ "]";
	}
	public int getClient() {
		return client;
	}
	public void setClient(int client) {
		this.client = client;
	}
	public float getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(float prixVente) {
		this.prixVente = prixVente;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public Date getDateVente() {
		return dateVente;
	}
	public void setDateVente(Date dateVente) {
		this.dateVente = dateVente;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public float getBenifice() {
		return benifice;
	}
	public void setBenifice(float benifice) {
		this.benifice = benifice;
	}
	public Vente(int id, String produit, int client, float prixVente, int quantite, Date dateVente, double total,
			float benifice) {
		super();
		this.id = id;
		this.produit = produit;
		this.client = client;
		this.prixVente = prixVente;
		this.quantite = quantite;
		this.dateVente = dateVente;
		this.total = total;
		this.benifice = benifice;
	}
	

}
