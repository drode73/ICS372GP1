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

package org.oobook.libraryv1.business.facade;

import org.oobook.libraryv1.business.entities.Book;
import org.oobook.libraryv1.business.entities.Member;
import org.oobook.libraryv1.business.entities.Transaction;

/**
 * The DataTransfer class is used to facilitate data transfer between Library
 * and UserInterface. It is also used to support iterating over Member and Book
 * objects. The class stores copies of fields that may be sent in either
 * direction.
 * 
 * @author Brahma Dathan
 *
 */
public abstract class DataTransfer {
	private String bookId;
	private String bookTitle;
	private String bookAuthor;
	private String bookBorrower;
	private String bookDueDate;
	private String memberId;
	private String memberName;
	private String memberAddress;
	private String memberPhone;
	private String transactionType;
	private String transactionDate;

	/**
	 * This sets all fields to "none".
	 */
	public DataTransfer() {
		reset();
	}

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

	public String getBookBorrower() {
		return bookBorrower;
	}

	public void setBookBorrower(String bookBorrower) {
		this.bookBorrower = bookBorrower;
	}

	public String getBookDueDate() {
		return bookDueDate;
	}

	public void setBookDueDate(String bookDueDate) {
		this.bookDueDate = bookDueDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	/**
	 * Sets all the member-related fields using the Member parameter.
	 * 
	 * @param member the member whose fields should be copied.
	 */
	public void setMemberFields(Member member) {
		memberId = member.getId();
		memberName = member.getName();
		memberPhone = member.getPhone();
		memberAddress = member.getAddress();
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setTransacionFields(Transaction transaction) {
		setTransactionType(transaction.getType());
		setTransactionDate(transaction.getDate());
		setBookTitle(transaction.getTitle());
	}

	/**
	 * Sets all the book-related fields using the Book parameter. If the book is not
	 * issued "none" is stored in the borrower and due date fields.
	 * 
	 * @param book the book whose fields should be copied.
	 */
	public void setBookFields(Book book) {
		if (book.getBorrower() != null) {
			bookBorrower = book.getBorrower().getId();
			bookDueDate = book.getDueDate();
		} else {
			bookBorrower = "Not checked out";
			bookDueDate = "Not applicable (not borrowed)";
		}
		bookId = book.getId();
		bookTitle = book.getTitle();
		bookAuthor = book.getAuthor();
	}

	/**
	 * Sets all String fields to "none"
	 */
	public void reset() {
		bookId = "Invalid book id";
		bookTitle = "No such book";
		bookBorrower = "Not checked out";
		bookDueDate = "Not applicable (not borrowed)";
		bookAuthor = "No such book";
		memberId = "Invalid member id";
		memberName = "No such member";
		memberPhone = "No such member";
		memberAddress = "No such member";
	}
}
