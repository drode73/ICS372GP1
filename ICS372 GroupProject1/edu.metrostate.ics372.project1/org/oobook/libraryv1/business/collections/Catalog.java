package org.oobook.libraryv1.business.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.oobook.libraryv1.business.entities.Book;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 * 
 *            Redistribution and use with or without modification, are permitted
 *            provided that the following conditions are met:
 *
 *            - the use is for academic purpose only - Redistributions of source
 *            code must retain the above copyright notice, this list of
 *            conditions and the following disclaimer. - Neither the name of
 *            Brahma Dathan or Sarnath Ramnath may be used to endorse or promote
 *            products derived from this software without specific prior written
 *            permission.
 *
 *            The authors do not make any claims regarding the correctness of
 *            the code in this module and are not responsible for any loss or
 *            damage resulting from its use.
 */

/**
 * The collection class for Book objects
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Catalog implements Iterable<Book>, Serializable {
	private static final long serialVersionUID = 1L;
	private static Catalog catalog;
	private List<Book> books = new LinkedList<Book>();

	private Catalog() {

	}

	public static Catalog getInstance() {
		if (catalog == null) {
			catalog = new Catalog();
		}
		return catalog;
	}

	/**
	 * Checks whether a book with a given book id exists.
	 * 
	 * @param bookId the id of the book
	 * @return true iff the book exists
	 * 
	 */
	public Book search(String bookId) {
		for (Iterator<Book> iterator = books.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			if (book.getId().equals(bookId)) {
				return book;
			}
		}
		return null;
	}

	/**
	 * Removes a book from the catalog
	 * 
	 * @param bookId book id
	 * @return true iff book could be removed
	 */
	public boolean removeBook(String bookId) {
		Book book = search(bookId);
		if (book == null) {
			return false;
		} else {
			return books.remove(book);
		}
	}

	/**
	 * Inserts a book into the collection
	 * 
	 * @param book the book to be inserted
	 * @return true iff the book could be inserted. Currently always true
	 */
	public boolean insertBook(Book book) {
		books.add(book);
		return true;
	}

	/**
	 * Returns an iterator to all books
	 * 
	 * @return iterator to the collection
	 */
	public Iterator<Book> iterator() {
		return books.iterator();
	}

	/**
	 * String form of the collection
	 * 
	 */
	public String toString() {
		return books.toString();
	}
}
