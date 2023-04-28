package com.ne62.controllers;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ne62.service.LibraryService;
import com.ne62.service.RequestValidator;

@RestController
public class MainController {

	@Autowired
	LibraryService service;

	@Autowired
	RequestValidator validate;

	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> register(@RequestBody String request) {
		try {
			if (validate.checkValue(new JSONObject(request)) == true) {
				return service.addMember(request);
			} else {
				return new ResponseEntity<String>(
						service.generateResponse(false, "Field cannot be empty", new JSONArray()),
						HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Invalid value of pages", new JSONArray()), HttpStatus.BAD_REQUEST);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(service.generateResponse(false, "Invalid JSON format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Date must be in 'yyyy-MM-dd' format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/book/add", produces = "application/json")
	public ResponseEntity<String> addBook(@RequestBody String request) {
		try {
			if (validate.checkValue(new JSONObject(request)) == true) {
				return service.addBook(request);
			} else {
				return new ResponseEntity<String>(
						service.generateResponse(false, "Field cannot be empty", new JSONArray()),
						HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Invalid value of pages", new JSONArray()), HttpStatus.BAD_REQUEST);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(service.generateResponse(false, "Invalid JSON format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Date must be in 'yyyy-MM-dd' format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping(value = "/book/update", produces = "application/json")
	public ResponseEntity<String> updateBook(@RequestBody String request) {
		try {
			if (validate.checkValue(new JSONObject(request)) == true) {
				return service.updateBook(request);
			} else {
				return new ResponseEntity<String>(
						service.generateResponse(false, "Field cannot be empty", new JSONArray()),
						HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Invalid value of pages", new JSONArray()), HttpStatus.BAD_REQUEST);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(service.generateResponse(false, "Invalid JSON format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Date must be in 'yyyy-MM-dd' format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/book/delete", produces = "application/json")
	public ResponseEntity<String> deleteBook(@RequestParam String book_id) {
		try {
			return service.deleteBook(book_id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<String>(service.generateResponse(false, "Field cannot be empty", new JSONArray()),
					HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(value = "/book/get", produces = "application/json")
	public ResponseEntity<String> getBook() {
		return service.getBookList(null);
	}

	@GetMapping(value = "/book/get/{status}", produces = "application/json")
	public ResponseEntity<String> getBook(@PathVariable String status) {
		try {
			if (status.equals("0") || status.equals("1")) {
				return service.getBookList(Integer.valueOf(status));
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception

			return new ResponseEntity<String>(
					service.generateResponse(false, "Invalid path variable, fill with 0 or 1", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PatchMapping(value = "/book/lend", produces = "application/json")
	public ResponseEntity<String> lendBook(@RequestBody String request) {
		try {
			if (validate.checkValue(new JSONObject(request)) == true) {
				return service.lendBook(request);
			} else {
				return new ResponseEntity<String>(
						service.generateResponse(false, "Field cannot be empty", new JSONArray()),
						HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Invalid value of pages", new JSONArray()), HttpStatus.BAD_REQUEST);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(service.generateResponse(false, "Invalid JSON format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Date must be in 'yyyy-MM-dd' format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping(value = "/book/return", produces = "application/json")
	public ResponseEntity<String> returnBook(@RequestBody String request) {
		try {
			if (validate.checkValue(new JSONObject(request)) == true) {
				return service.returnBook(request);
			} else {
				return new ResponseEntity<String>(
						service.generateResponse(false, "Field cannot be empty", new JSONArray()),
						HttpStatus.BAD_REQUEST);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Invalid value of pages", new JSONArray()), HttpStatus.BAD_REQUEST);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(service.generateResponse(false, "Invalid JSON format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(
					service.generateResponse(false, "Date must be in 'yyyy-MM-dd' format", new JSONArray()),
					HttpStatus.BAD_REQUEST);
		}
	}

}
