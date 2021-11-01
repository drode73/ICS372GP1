package org.oobook.libraryv1.business.tests;

import org.oobook.libraryv1.business.entities.Book;
import org.oobook.libraryv1.business.entities.Member;
import org.oobook.libraryv1.business.facade.Library;
import org.oobook.libraryv1.business.facade.Request;
import org.oobook.libraryv1.business.facade.Result;

/**
 * This class generates sample automated tests for the library system using
 * asserts.
 * 
 * @author Brahma Dathan
 *
 */
public class AutomatedTester {
	private Library library;
	private String[] names = { "n1", "n2", "n3" };
	private String[] addresses = { "a1", "a2", "a3" };
	private String[] phones = { "p1", "p2", "p3" };
	private Member[] members = new Member[3];
	private String[] titles = { "t1", "t2", "t3", "t4", "t5", "t6" };
	private String[] authors = { "a1", "a2", "a3", "a4", "a5", "a6" };
	private String[] ids = { "i1", "i2", "i3", "i4", "i5", "i6" };
	private Book[] books = new Book[6];

	/**
	 * Tests Member creation.
	 */
	public void testAddMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberAddress(addresses[count]);
			Request.instance().setMemberName(names[count]);
			Request.instance().setMemberPhone(phones[count]);
			Result result = Library.instance().addMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(names[count]);
			assert result.getMemberPhone().equals(phones[count]);
		}
	}

	public void testAddBook() {
		for (int count = 0; count < books.length; count++) {
			Request.instance().setBookAuthor(authors[count]);
			Request.instance().setBookTitle(titles[count]);
			Request.instance().setBookId(ids[count]);
			Result result = Library.instance().addBook(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getBookTitle().equals(titles[count]);
			assert result.getBookId().equals(ids[count]);
		}
	}

	public void testSearchMembership() {
		Request.instance().setMemberId("M1");
		assert Library.instance().searchMembership(Request.instance()).getMemberId().equals("M1");
		Request.instance().setMemberId("M4");
		assert Library.instance().searchMembership(Request.instance()) == null;
	}

	public void testAll() {
		testAddMember();
		testAddBook();
		testSearchMembership();
	}

	public static void main(String[] args) {
		new AutomatedTester().testAll();
	}
}
