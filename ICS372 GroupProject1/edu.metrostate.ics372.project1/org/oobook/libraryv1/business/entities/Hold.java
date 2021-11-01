package org.oobook.libraryv1.business.entities;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.Serializable;
import java.util.Calendar;

/**
 * Hold represents a hold placed by a member on a book.
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Hold implements Serializable {
	private Book book;
	private Member member;
	private Calendar date;

	/**
	 * The member and book are stored. The date is computed by adding the duration
	 * days to the current date.
	 * 
	 * @param member   who places the hold
	 * @param book     the book on which hold is placed
	 * @param duration for how long the hold is valid
	 */
	public Hold(Member member, Book book, Calendar date) {
		this.book = book;
		this.member = member;
		this.date = date;
	}

	public Member getMember() {
		return member;
	}

	public Book getBook() {
		return book;
	}

	public Calendar getDate() {
		return date;
	}

	/**
	 * Checks if the hold is valid. If the current time is less than the hold
	 * end-dae, the hold is still valid.
	 * 
	 * @return true if and only if the hold is valid
	 */
	public boolean isValid() {
		return (System.currentTimeMillis() < date.getTimeInMillis());
	}

	@Override
	public String toString() {
		return "Hold [book=" + book + ", member=" + member + ", date=" + date + "]";
	}

}
