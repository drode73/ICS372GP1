package org.oobook.grocerystore.business.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represent a single Transaction (User visited the store, total price paid
 * during the visit)
 * 
 * @author Joshua
 *
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private String total;
	private String visited;
	private Calendar date;

	/**
	 * Creates the transaction with the member visited the store and how much they
	 * invested. The date is the current date.
	 * 
	 * @param type  The type of transaction
	 * @param title The title of the book
	 * 
	 */
	public Transaction(String total, String visited) {
		this.total = total;
		this.visited = visited;
		date = new GregorianCalendar();
	}

	/**
	 * Checks whether this transaction is on the given date
	 * 
	 * @param date The date for which transactions are being sought
	 * @return true if the dates match
	 */
	public boolean onDate(Calendar date) {
		return ((date.get(Calendar.YEAR) == this.date.get(Calendar.YEAR))
				&& (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH))
				&& (date.get(Calendar.DATE) == this.date.get(Calendar.DATE)));
	}

	/**
	 * Return the total field
	 * 
	 * @return total field
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * Returns the visited field
	 * 
	 * @return visited field
	 */
	public String getVisited() {
		return visited;
	}

	/**
	 * Returns the date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getDate() {
		return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
	}

	/**
	 * String form of the transaction
	 * 
	 */
	@Override
	public String toString() {
		return ("Amount of Money You Spend:$ " + total + "   " + "Amount of Time You Visited: " + visited);
	}
}
