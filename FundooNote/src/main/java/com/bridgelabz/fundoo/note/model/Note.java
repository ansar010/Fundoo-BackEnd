package com.bridgelabz.fundoo.note.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bridgelabz.fundoo.user.model.User;

@Entity
@Table(name = "Note")
public class Note implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String description;

	private boolean archive;
	private String color;
	private String remainder;
	private boolean pin;
	private boolean trash;
	@ManyToOne
	@JoinColumn(name = "userId")
	User user;
//	@ManyToMany(mappedBy="noteList")
//	@JsonIgnore
//	List<Label> label;
//	
//	
//	public List<Label> getLabel() {
//		return label;
//	}

//	public void setLabel(List<Label> label) {
//		this.label = label;
//	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRemainder() {
		return remainder;
	}

	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", description=" + description + ", archive=" + archive
				+ ", color=" + color + ", remainder=" + remainder + ", pin=" + pin + ", trash=" + trash + ", user="
				+ user + "]";
	}

}
