package com.ne62.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "books")
public class Books {

	@Id
	@Column(name = "book_id")
	String bookId;
	@Column(name = "book_title")
	String bookTitle;
	@Column(name = "author")
	String author;
	@Column(name = "publisher")
	String publisher;
	@Column(name = "publish_date")
	Date publishDate;
	@Column(name = "pages")
	Integer pages;
	@Column(name = "book_status", nullable = false, columnDefinition = "integer default 0")
	Integer bookStatus;
	@Column(name = "borrowed_by", nullable = true)
	String borrowedBy;
	@Column(name = "borrow_date", nullable = true)
	Date borrowDate;
	@Column(name = "return_date", nullable = true)
	Date returnDate;
	@Column(name = "created_date")
	Timestamp createdDate;
	@Column(name = "created_by")
	String createdBy;
	@Column(name = "modified_date")
	Timestamp modifiedDate;
	@Column(name = "modified_by")
	String modifiedBy;
	
	
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
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
	public String getPublishDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(publishDate);
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public String getBookStatus() {
		return bookStatus==0?"Available":"Lent";
	}
	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}
	public String getBorrowedBy() {
		return borrowedBy;
	}
	public void setBorrowedBy(String borrowedBy) {
		this.borrowedBy = borrowedBy;
	}
	public String getBorrowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(borrowDate);
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	public String getReturnDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(returnDate);
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
}
