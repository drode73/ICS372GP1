package org.oobook.libraryv1.business.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.oobook.libraryv1.business.collections.HoldList;

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
 * Book represents a book of the library.
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String author;
	private String id;
	private Member borrowedBy;
	private HoldList holds = new HoldList();
	private Calendar dueDate;

	/**
	 * Creates a book with the given id, title, and author name
	 * 
	 * @param title  book title
	 * @param author author name
	 * @param id     book id
	 */
	public Book(String title, String author, String id) {
		this.title = title;
		this.author = author;
		this.id = id;
	}

	/**
	 * Marks the book as issued to a member
	 * 
	 * @param member the borrower
	 * @return true iff the book could be issued. True currently
	 */
	public boolean issue(Member member) {
		borrowedBy = member;
		dueDate = new GregorianCalendar();
		dueDate.set(Calendar.HOUR, 0);
		dueDate.set(Calendar.MINUTE, 0);
		dueDate.set(Calendar.SECOND, 0);
		dueDate.add(Calendar.HOUR, 11);
		dueDate.add(Calendar.MINUTE, 59);
		dueDate.add(Calendar.SECOND, 59);
		dueDate.add(Calendar.MONTH, 1);
		return true;
	}

	/**
	 * Marks the book as returned
	 * 
	 * @return The member who had borrowed the book
	 */
	public Member returnBook() {
		if (borrowedBy == null) {
			return null;
		} else {
			Member borrower = borrowedBy;
			borrowedBy = null;
			return borrower;
		}
	}

	/**
	 * Renews the book
	 * 
	 * @param member who wants to renew the book
	 * @return true iff the book could be renewed
	 */
	public boolean renew(Member member) {
		if (hasHold()) {
			return false;
		}
		if ((member.getId()).equals(borrowedBy.getId())) {
			return (issue(member));
		}
		return false;
	}

	/**
	 * Adds one more hold to the book
	 * 
	 * @param hold the new hold on the book
	 */
	public void placeHold(Hold hold) {
		holds.addHold(hold);
	}

	/**
	 * Removes hold for a specific member
	 * 
	 * @param memberId whose hold has to be removed
	 * @return true iff the hold could be removed
	 */
	public boolean removeHold(String memberId) {
		Hold hold = holds.removeHoldOnMember(memberId);
		if (hold != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a valid hold
	 * 
	 * @return the next valid hold
	 */
	public Hold getNextHold() {
		return holds.getNextValidHold();
	}

	/**
	 * Checks whether there is a hold on this book
	 * 
	 * @return true iff there is a hold
	 */
	public boolean hasHold() {
		return holds.getNextValidHold() != null;
	}

	/**
	 * Returns an iterator for the holds
	 * 
	 * @return iterator for the holds on the book
	 */
	public Iterator getHolds() {
		return holds.iterator();
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

	public Member getBorrower() {
		return borrowedBy;
	}

	/**
	 * Returns a String version of due date
	 * 
	 * @return String version of due date
	 */
	public String getDueDate() {
		return (dueDate.getTime().toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Book other = (Book) object;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "title " + title + " author " + author + " id " + id + " borrowed by " + borrowedBy;
	}
}