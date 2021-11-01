package org.oobook.libraryv1.business.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.oobook.libraryv1.business.entities.Hold;

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
 * Maintains a list of Hold objects. It is used by both Member and Book.
 * 
 * @author Brahma Dathan
 *
 */
public class HoldList implements Iterable<Hold>, Serializable {
	private List<Hold> holds = new LinkedList<Hold>();

	/**
	 * Adds a Hold object to the list.
	 * 
	 * @param hold the Hold object to be added
	 */
	public boolean addHold(Hold hold) {
		holds.add(hold);
		return true;
	}

	/**
	 * Removes the hold for a specific book
	 * 
	 * @param bookId the book id for removing a hold
	 * @return the removed Hold object if the hold could be removed; otherwise, nul
	 */
	public Hold removeHoldOnBook(String bookId) {
		for (ListIterator<Hold> iterator = holds.listIterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			String id = hold.getBook().getId();
			if (id.equals(bookId)) {
				iterator.remove();
				return hold;
			}
		}
		return null;
	}

	/**
	 * Removes the hold for the specific member
	 * 
	 * @param memberId the book id for removing a hold
	 * @return the removed Hold object if the hold could be removed; otherwise, nul
	 */
	public Hold removeHoldOnMember(String memberId) {
		for (ListIterator<Hold> iterator = holds.listIterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			String id = hold.getMember().getId();
			if (id.equals(memberId)) {
				iterator.remove();
				return hold;
			}
		}
		return null;
	}

	/**
	 * Returns true iff the holds list is empty.
	 * 
	 * @return iff the list of holds is true;
	 */
	public boolean isEmpty() {
		return getNextValidHold() == null;
	}

	/**
	 * Returns a valid hold and removes all invalid holds until a valid one of
	 * found.
	 * 
	 * @return the next valid hold
	 */
	public Hold getNextValidHold() {
		for (ListIterator<Hold> iterator = holds.listIterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			if (hold.isValid()) {
				return hold;
			} else {
				iterator.remove();
			}
		}
		return null;
	}

	@Override
	public Iterator<Hold> iterator() {
		return holds.iterator();
	}

}
