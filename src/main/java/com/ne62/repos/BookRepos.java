package com.ne62.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ne62.models.Books;

@Repository
public interface BookRepos extends JpaRepository<Books, String> {

	List<Books> findByBookStatus(Integer bookStatus);
	List<Books> findByBookId(String bookId);
	List<Books> findByBookIdAndBookStatus(String bookId, Integer bookStatus);
}
