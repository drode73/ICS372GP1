package org.oobook.libraryv1.business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.oobook.libraryv1.business.collections.HoldList;
import org.oobook.libraryv1.business.facade.Result;
import org.oobook.libraryv1.business.iterators.FilteredIterator;
import org.oobook.libraryv1.business.iterators.SafeTransactionIterator;

/**
 * Member represents a member of the library.
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private static final String MEMBER_STRING = "M";
	private List<Book> booksBorrowed = new LinkedList<Book>();
	private HoldList booksOnHold = new HoldList();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private static int idCounter;

	/**
	 * Creates a single member
	 * 
	 * @param name    name of the member
	 * @param address address of the member
	 * @param phone   phone number of the member
	 */
	public Member(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		id = MEMBER_STRING + ++idCounter;
	}

	/**
	 * Stores the book as issued to the member
	 * 
	 * @param book the book to be issued
	 * @return true iff the book could be marked as issued. always true currently
	 */
	public boolean issue(Book book) {
		if (booksBorrowed.add(book)) {
			transactions.add(new Transaction("Issued", book.getTitle()));
			return true;
		}
		return false;
	}

	/**
	 * Marks the book as not issued to the member
	 * 
	 * @param book the book to be returned
	 * @return true iff the book could be marked as marked as returned
	 */
	public boolean returnBook(Book book) {
		if (booksBorrowed.remove(book)) {
			transactions.add(new Transaction("Returned", book.getTitle()));
			return true;
		}
		return false;
	}

	/**
	 * Marks the book as renewed
	 * 
	 * @param book the book to be renewed
	 * @return true iff the book could be renewed
	 */
	public boolean renew(Book book) {
		for (Iterator<Book> iterator = booksBorrowed.iterator(); iterator.hasNext();) {
			Book aBook = iterator.next();
			String id = aBook.getId();
			if (id.equals(book.getId())) {
				transactions.add(new Transaction("Renewed", book.getTitle()));
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets an iterator to the issued books
	 * 
	 * @return Iterator to the collection of issued books
	 */
	public Iterator<Book> getBooksIssued() {
		return booksBorrowed.iterator();
	}

	/**
	 * Places a hold for the book
	 * 
	 * @param hold the book to be placed a hold
	 */
	public boolean placeHold(Hold hold) {
		transactions.add(new Transaction("Hold placed", hold.getBook().getTitle()));
		return booksOnHold.addHold(hold);
	}

	/**
	 * Removes a hold
	 * 
	 * @param bookId the book id for removing a hold
	 * @return true iff the hold could be removed
	 */
	public boolean removeHold(String bookId) {
		Hold hold = booksOnHold.removeHoldOnBook(bookId);
		if (hold != null) {
			transactions.add(new Transaction("Hold removed", hold.getBook().getTitle()));
			return true;
		}
		return false;
	}

	/**
	 * Gets an iterator to a collection of selected transactions
	 * 
	 * @param date the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */
	public Iterator<Result> getTransactionsOnDate(Calendar date) {
		return new SafeTransactionIterator(
				new FilteredIterator(transactions.iterator(), transaction -> transaction.onDate(date)));
	}

	/**
	 * Returns the list of all transactions for this member.
	 * 
	 * @return the iterator to the list of Transaction objects
	 */
	public Iterator<Transaction> getTransactions() {
		return transactions.iterator();
	}

	/**
	 * Getter for name
	 * 
	 * @return member name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for phone number
	 * 
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Getter for address
	 * 
	 * @return member address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Getter for id
	 * 
	 * @return member id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for name
	 * 
	 * @param newName member's new name
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Setter for address
	 * 
	 * @param newName member's new address
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}

	/**
	 * Setter for phone
	 * 
	 * @param newName member's new phone
	 */
	public void setPhone(String newPhone) {
		phone = newPhone;
	}

	/**
	 * String form of the member
	 * 
	 */
	@Override
	public String toString() {
		String string = "Member name " + name + " address " + address + " id " + id + "phone " + phone;
		string += " borrowed: [";
		for (Iterator iterator = booksBorrowed.iterator(); iterator.hasNext();) {
			Book book = (Book) iterator.next();
			string += " " + book.getTitle();
		}
		string += "] holds: [";
		for (Iterator iterator = booksOnHold.iterator(); iterator.hasNext();) {
			Hold hold = (Hold) iterator.next();
			string += " " + hold.getBook().getTitle();
		}
		string += "] transactions: [";
		for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
			string += (Transaction) iterator.next();
		}
		string += "]";
		return string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Checks whether the member is equal to the one supplied
	 * 
	 * @param object the member who should be compared
	 * @return true iff the member ids match
	 */

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
		Member other = (Member) object;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Iterator<Hold> getHolds() {
		return booksOnHold.iterator();
	}

	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
		idCounter = (int) input.readObject();
	}

}