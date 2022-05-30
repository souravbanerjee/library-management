package com.sourav.assignment.librarymanagement.model;

public class Book {

	private String id;

	private String name;

	private String category;


	private String author;

	private String publisher;

	private Integer stock;

	public Book() {
	}

	public Book(String name, String category, String author, String publisher,Integer stock) {
		this.name = name;
		this.category = category;
		this.author = author;
		this.publisher = publisher;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}


	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", category='" + category + '\'' +
				", author='" + author + '\'' +
				", publisher='" + publisher + '\'' +
				", stock='" + stock + '\'' +
				'}';
	}
}