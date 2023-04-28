package com.ne62.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ne62.BookLibraryApplication;
import com.ne62.models.Books;
import com.ne62.models.Members;
import com.ne62.repos.BookRepos;
import com.ne62.repos.MemberRepos;

@Service
public class LibraryService {

	@Autowired
	BookRepos book;

	@Autowired
	MemberRepos member;

	public String generateResponse(Boolean success, String message, JSONArray data) {
		JSONObject response = new JSONObject();
		response.put("success", success);
		response.put("message", message);
		response.put("data", data);

		return response.toString();
	};
	
	private JSONArray generateBookList(List<Books> bookList) {
		JSONArray jsArr = new JSONArray();
		for (Books b : bookList) {
			JSONObject books = new JSONObject();
			books.put("book_id", b.getBookId());
			books.put("book_title", b.getBookTitle());
			books.put("author", b.getAuthor());
			books.put("publisher", b.getPublisher());
			books.put("publish_date", b.getPublishDate());
			books.put("pages", b.getPages());
			books.put("status", b.getBookStatus());
			if (b.getBookStatus().equals("Lent")) {
				books.put("borrowed_by", b.getBorrowedBy());
				books.put("borrow_date", b.getBorrowDate());
				books.put("return_date", b.getReturnDate());
			}
			jsArr.put(books);
		}
		return jsArr;
	}

	public ResponseEntity<String> addMember(String request) {

		JSONObject user = new JSONObject(request);
		List<Members> m1 = member.findByMemberId(user.getString("member_id"));
		if (m1.size() > 0) {
			return new ResponseEntity<String>(generateResponse(false, "Member ID already exist", new JSONArray()),
					HttpStatus.OK);
		} else {

			Members m = new Members();
			m.setMemberId(user.getString("member_id"));
			m.setMemberName(user.getString("member_name"));
			m.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
			m.setCreatedBy("RegisterAPI");
			try {
				member.save(m);
				return new ResponseEntity<String>(generateResponse(true, "Success registering member", new JSONArray()),
						HttpStatus.OK);
			} catch (DataIntegrityViolationException e) {
				// TODO: handle exception
				e.printStackTrace();
				return new ResponseEntity<String>(generateResponse(false, "Error Registering member", null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}

	public ResponseEntity<String> addBook(String request) {
		JSONObject entry = new JSONObject(request);
		System.out.println(request);
		try {

			List<Books> b = book.findByBookId(entry.getString("book_id"));
			if (b.size() > 0) {
				return new ResponseEntity<String>(generateResponse(false, "Book ID already exist", new JSONArray()),
						HttpStatus.OK);
			} else {
				Books newbook = new Books();
				newbook.setBookId(entry.getString("book_id"));
				newbook.setBookTitle(entry.getString("book_title"));
				newbook.setAuthor(entry.getString("author"));
				newbook.setPublisher(entry.getString("publisher"));
				newbook.setPublishDate(Date.valueOf(entry.getString("publish_date")));
				newbook.setPages(Integer.valueOf(entry.getString("pages")));
				newbook.setBookStatus(0);
				newbook.setCreatedBy("AddBook API");
				newbook.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
				book.save(newbook);
				return new ResponseEntity<String>(generateResponse(false, "Book saved successfully", new JSONArray()),
						HttpStatus.OK);
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(generateResponse(false, "Saving failed", new JSONArray()), HttpStatus.OK);
		}

	}

	public ResponseEntity<String> updateBook(String request) {
		try {

			JSONObject entry = new JSONObject(request);
			List<Books> b = book.findByBookId(entry.getString("book_id"));
			if (b.size() == 1) {
				Books updatedbook = b.get(0);
				updatedbook.setBookTitle(entry.getString("book_title"));
				updatedbook.setAuthor(entry.getString("author"));
				updatedbook.setPublisher(entry.getString("publisher"));
				updatedbook.setPublishDate(Date.valueOf(entry.getString("publish_date")));
				updatedbook.setPages(Integer.valueOf(entry.getString("pages")));
				updatedbook.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
				updatedbook.setModifiedBy("UpdateBookAPI");
				book.save(updatedbook);
				return new ResponseEntity<String>(generateResponse(true, "Book updated successfully", new JSONArray()),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(generateResponse(false, "Book ID not found", new JSONArray()),
						HttpStatus.OK);
			}
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(generateResponse(false, "Saving failed", new JSONArray()), HttpStatus.OK);
		}
	}

	public ResponseEntity<String> deleteBook(String book_id) {
		try {
			List<Books> b = book.findByBookId(book_id);
			if (b.size() == 1) {
				Books bk = b.get(0);
				book.delete(bk);
				return new ResponseEntity<String>(generateResponse(true, "Book deleted successfully", new JSONArray()),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(generateResponse(false, "Book ID not found", new JSONArray()),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<String>(generateResponse(false, "Delete failed", new JSONArray()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> getBookList(Integer request) {

		ResponseEntity<String> resp = new ResponseEntity<String>("", HttpStatus.OK);
		// Get Books
		List<Books> booklist = new ArrayList<Books>();
		if (request == null) {
			booklist = book.findAll();
		} else if (request == 1 || request == 0) {
			booklist = book.findByBookStatus(request);
		}

		if (booklist.isEmpty()) {
			resp = new ResponseEntity<String>(generateResponse(false, "No books found", new JSONArray()),
					HttpStatus.OK);
		} else {
			JSONArray jsArr = generateBookList(booklist);
			resp = new ResponseEntity<String>(generateResponse(true, "Books found", jsArr), HttpStatus.OK);

		}

		return resp;
	}

	public ResponseEntity<String> lendBook(String request) {
		ResponseEntity<String> resp = new ResponseEntity<String>("", HttpStatus.OK);
		JSONObject req = new JSONObject(request);
		try {
			List<Members> mb = member.findByMemberId(req.getString("member_id"));
			if (mb.size()<1) {
				resp = new ResponseEntity<String>(generateResponse(false, "Unregistered member, please register first", new JSONArray()), HttpStatus.OK);
			} else if (mb.size()>1) {
				resp = new ResponseEntity<String>(generateResponse(false, "Please check member database", new JSONArray()), HttpStatus.OK);
			} else {
				List<Books> bl = book.findByBookIdAndBookStatus(req.getString("book_id"),0);
				if (bl.isEmpty()||bl.size()>1) {
					resp = new ResponseEntity<String>(generateResponse(false, "Book unavailable or is being lent", new JSONArray()),
							HttpStatus.OK);
				} else {
					Books b = bl.get(0);
					b.setBookStatus(1);
					b.setBorrowedBy(req.getString("member_id"));
					b.setBorrowDate(Date.valueOf(LocalDate.now()));
					b.setReturnDate(Date.valueOf(LocalDate.now().plusDays(7)));
					b.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
					b.setModifiedBy("LendBookAPI");
					book.save(b);
					JSONArray jsArr = generateBookList(bl);
					resp =  new ResponseEntity<String>(generateResponse(true, "Book lent", jsArr), HttpStatus.OK);
				}
				
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			resp =  new ResponseEntity<String>(generateResponse(false, "Update status failed", new JSONArray()), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp =  new ResponseEntity<String>(generateResponse(false, "Error encountered", new JSONArray()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	public ResponseEntity<String> returnBook(String request) {
		ResponseEntity<String> resp = new ResponseEntity<String>("", HttpStatus.OK);
		JSONObject req = new JSONObject(request);
		try {
			List<Members> mb = member.findByMemberId(req.getString("member_id"));
			if (mb.size()<1) {
				resp = new ResponseEntity<String>(generateResponse(false, "Unregistered member, please register first", new JSONArray()), HttpStatus.OK);
			} else if (mb.size()>1) {
				resp = new ResponseEntity<String>(generateResponse(false, "Please check member database", new JSONArray()), HttpStatus.OK);
			} else {
				List<Books> bl = book.findByBookIdAndBookStatus(req.getString("book_id"),1);
				if (bl.isEmpty()||bl.size()>1) {
					resp = new ResponseEntity<String>(generateResponse(false, "Book unavailable or is already returned", new JSONArray()),
							HttpStatus.OK);
				} else {
					Books b = bl.get(0);
					b.setBookStatus(0);
					b.setBorrowedBy(null);
					b.setBorrowDate(null);
					b.setReturnDate(null);
					b.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
					b.setModifiedBy("ReturnBookAPI");
					book.save(b);
					JSONArray jsArr = generateBookList(bl);
					resp =  new ResponseEntity<String>(generateResponse(true, "Book returned", jsArr), HttpStatus.OK);
				}
				
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			resp =  new ResponseEntity<String>(generateResponse(false, "Update status failed", new JSONArray()), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp =  new ResponseEntity<String>(generateResponse(false, "Error encountered", new JSONArray()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

}
