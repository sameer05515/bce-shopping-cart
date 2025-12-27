package com.p.bce.shopping.cart.rpc.pojo;

public class BookDetailDTO {
//	SELECT `BookId`, `CategoryId`, `Title`, `Author`, `Publisher`, `Edition`, `Price`, `Quantity`, `Description` 
	// FROM `book_details` WHERE 1
	
	private int bookId;
	private int categoryId;
	private String title;
	private String author;
	private String publisher;
	private String edition;
	private double price;
	private int quantity;
	private String description;
	private String categoryName; // For display purposes
	
	public BookDetailDTO() {
		super();
	}

	public BookDetailDTO(int bookId, int categoryId, String title, String author, String publisher, String edition,
			double price, int quantity, String description) {
		super();
		this.bookId = bookId;
		this.categoryId = categoryId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.edition = edition;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "BookDetailDTO [bookId=" + bookId + ", categoryId=" + categoryId + ", title=" + title + ", author="
				+ author + ", publisher=" + publisher + ", edition=" + edition + ", price=" + price + ", quantity="
				+ quantity + ", description=" + description + ", categoryName=" + categoryName + "]";
	}
	
	

}
