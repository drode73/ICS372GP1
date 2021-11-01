package org.oobook.libraryv1.business.facade;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

import org.oobook.libraryv1.business.collections.Catalog;
import org.oobook.libraryv1.business.collections.MemberList;
import org.oobook.libraryv1.business.entities.Book;
import org.oobook.libraryv1.business.entities.Hold;
import org.oobook.libraryv1.business.entities.Member;
import org.oobook.libraryv1.business.iterators.SafeBookIterator;
import org.oobook.libraryv1.business.iterators.SafeMemberIterator;

/**
 * The facade class handling all requests from users.
 * 
 * @author Brahma Dathan
 *
 */
public class Library implements Serializable {
	private static final long serialVersionUID = 1L;
	private Catalog catalog = Catalog.getInstance();
	private MemberList members = MemberList.getInstance();
	private static Library library;

	/**
	 * Private for the singleton pattern Creates the catalog and member collection
	 * objects
	 */
	private Library() {
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static Library instance() {
		if (library == null) {
			return library = new Library();
		} else {
			return library;
		}
	}

	/**
	 * Organizes the operations for adding a book
	 * 
	 * @param title  book title
	 * @param author author name
	 * @param id     book id
	 * @return the Book object created
	 */
	public Result addBook(Request request) {
		Result result = new Result();
		Book book = new Book(request.getBookTitle(), request.getBookAuthor(), request.getBookId());
		if (catalog.insertBook(book)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setBookFields(book);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Organizes the operations for adding a member
	 * 
	 * @param name    member name
	 * @param address member address
	 * @param phone   member phone
	 * @return the Member object created
	 */
	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhone());
		if (members.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Organizes the placing of a hold
	 * 
	 * @param memberId member's id
	 * @param bookId   book's id
	 * @param duration for how long the hold should be valid in days
	 * @return indication on the outcome
	 */
	public Result placeHold(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (book.getBorrower() == null) {
			result.setResultCode(Result.BOOK_NOT_ISSUED);
			return result;
		}
		Calendar date = new GregorianCalendar();
		date.add(Calendar.DATE, request.getHoldDuration());
		Hold hold = new Hold(member, book, date);
		book.placeHold(hold);
		member.placeHold(hold);
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setBookFields(book);
		return result;
	}

	/**
	 * Searches for a given member
	 * 
	 * @param memberId id of the member
	 * @return true iff the member is in the member list collection
	 */
	public Result searchMembership(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
		} else {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
		}
		return result;
	}

	/**
	 * Organizes the issuing of a book
	 * 
	 * @param memberId member id
	 * @param bookId   book id
	 * @return the book issued
	 */
	public Result issueBook(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;

		}
		result.setBookFields(book);
		if (book.getBorrower() != null) {
			result.setResultCode(Result.BOOK_ISSUED);
			return result;
		}
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		if (!(book.issue(member) && member.issue(book))) {
			result.setResultCode(Result.OPERATION_FAILED);
		} else {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setBookFields(book);
		}
		return result;
	}

	/**
	 * Returns an iterator to the Result copy for books issued to a member
	 * 
	 * @param request - stores the member id
	 * @return iterator to the Result objects storing info about issued books
	 */
	public Iterator<Result> getBooks(Request request) {
		Member member = members.search(request.getMemberId());
		if (member == null) {
			return null;
		} else {
			return new SafeBookIterator(member.getBooksIssued());
		}
	}

	/**
	 * Renews a book
	 * 
	 * @param memberId member id
	 * @param bookId   id of the book to be renewed
	 * 
	 * @return the book renewed
	 */
	public Result renewBook(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		Member member = members.search(request.getMemberId());
		result.setBookFields(book);
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		if ((book.renew(member) && member.renew(book))) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.OPERATION_FAILED);
		}
		result.setBookFields(book);
		return result;
	}

	/**
	 * Processes holds for a single book
	 * 
	 * @param bookId id of the book
	 * @return the member who should be notified
	 */
	public Result processHold(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (book.getBorrower() != null) {
			result.setResultCode(result.BOOK_ISSUED);
			return result;
		}
		Hold hold = book.getNextHold();
		if (hold == null) {
			result.setResultCode(Result.NO_HOLD_FOUND);
			return result;
		}
		hold.getMember().removeHold(request.getBookId());
		hold.getBook().removeHold(hold.getMember().getId());
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setMemberFields(hold.getMember());
		return result;
	}

	/**
	 * Removes a hold for a specific book and member combincation
	 * 
	 * @param memberId id of the member
	 * @param bookId   book id
	 * @return result of the operation
	 */
	public Result removeHold(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (member.removeHold(request.getBookId()) && book.removeHold(request.getMemberId())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.NO_HOLD_FOUND);
		}
		return result;
	}

	/**
	 * Removes all out-of-date holds.
	 * 
	 */
	private Result removeInvalidHolds() {
		Result result = new Result();
		for (Iterator<Member> memberIterator = members.iterator(); memberIterator.hasNext();) {
			for (Iterator<Hold> iterator = memberIterator.next().getHolds(); iterator.hasNext();) {
				Hold hold = iterator.next();
				if (!hold.isValid()) {
					hold.getBook().removeHold(hold.getMember().getId());
					hold.getMember().removeHold(hold.getBook().getId());
				}
			}
		}
		result.setResultCode(Result.OPERATION_COMPLETED);
		return result;
	}

	/**
	 * Removes a specific book from the catalog
	 * 
	 * @param bookId id of the book
	 * @return a code representing the outcome
	 */
	public Result removeBook(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (book.hasHold()) {
			result.setResultCode(Result.BOOK_HAS_HOLD);
			return result;
		}
		if (book.getBorrower() != null) {
			result.setResultCode(Result.BOOK_ISSUED);
			return result;
		}
		if (catalog.removeBook(request.getBookId())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Returns a single book
	 * 
	 * @param bookId id of the book to be returned
	 * @return a code representing the outcome
	 */
	public Result returnBook(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		Member member = book.returnBook();
		if (member == null) {
			result.setResultCode(Result.BOOK_NOT_ISSUED);
			return result;
		}
		result.setMemberFields(member);
		if (!(member.returnBook(book))) {
			result.setResultCode(Result.OPERATION_FAILED);
			return result;
		}
		if (book.hasHold()) {
			result.setResultCode(Result.BOOK_HAS_HOLD);
			return result;
		}
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setBookFields(book);
		result.setMemberFields(member);
		return result;
	}

	/**
	 * Returns an iterator to the info. in transactions for a specific member on a
	 * certain date
	 * 
	 * @param memberId member id
	 * @param date     date of issue
	 * @return iterator to the collection
	 */
	public Iterator<Result> getTransactions(Request request) {
		Member member = members.search(request.getMemberId());
		if (member == null) {
			return new LinkedList<Result>().iterator();
		}
		return member.getTransactionsOnDate(request.getDate());
	}

	/**
	 * Retrieves a deserialized version of the library from disk
	 * 
	 * @return a Library object
	 */
	public static Library retrieve() {
		try {
			FileInputStream file = new FileInputStream("LibraryData");
			ObjectInputStream input = new ObjectInputStream(file);
			library = (Library) input.readObject();
			Member.retrieve(input);
			return library;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Serializes the Library object
	 * 
	 * @return true iff the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("LibraryData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(library);
			Member.save(output);
			file.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns an iterator to Member info. The Iterator returned is a safe one, in
	 * the sense that only copies of the Member fields are assembled into the
	 * objects returned via next().
	 * 
	 * @return an Iterator to Result - only the Member fields are valid.
	 */
	public Iterator<Result> getMembers() {
		return new SafeMemberIterator(members.iterator());
	}

	/**
	 * Returns an iterator to Book info. The Iterator returned is a safe one, in the
	 * sense that only copies of the Book fields are assembled into the objects
	 * returned via next().
	 * 
	 * @return an Iterator to Result - only the Book fields are valid.
	 */
	public Iterator<Result> getBooks() {
		return new SafeBookIterator(catalog.iterator());
	}

	/**
	 * String form of the library
	 * 
	 */
	@Override
	public String toString() {
		return catalog + "\n" + members;
	}
}
