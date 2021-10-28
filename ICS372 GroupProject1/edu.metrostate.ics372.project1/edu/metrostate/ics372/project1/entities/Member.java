package edu.metrostate.ics372.project1.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private Calendar date;

	// Make the paid fee the same for each Member
	private int fee = (int) 2.00;

	private static final String MEMBER_STRING = "M";

	/**
	 * MEMBER CLASS: Check if we need to create an List object Product with a
	 * LinkedList
	 */

	private List<Product> products = new LinkedList<Product>();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private static int idCounter;

	/**
	 * Creates a single member
	 * 
	 * @param name    name of the member
	 * @param address address of the member
	 * @param phone   phone number of the member
	 */
	public Member(String name, String address, String phone, Calendar date, int fees) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.date = date;
		this.fee = fees;
		id = MEMBER_STRING + ++idCounter;
	}

	/**
	 * Issue the product to the member
	 * 
	 * @param product the product to be issued
	 * @return true if the product could be marked as issued. always true currently
	 */
	public boolean issue(Product product) {
		if (products.add(product)) {
			transactions.add(new Transaction("Add to cart!", product.getProductName()));
			return true;
		}
		return false;
	}

	// Make the shipments for the members?

	/**
	 * Gets an iterator to the issued products
	 * 
	 * @return Iterator to the collection of issued products
	 */
	public Iterator<Product> getProductsIssued() {
		return products.iterator();
	}

	/**
	 * Gets an iterator to a collection of selected transactions
	 * 
	 * @param date the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */
	// Need to check with other Groupmates to see if they created Result java class
	/*
	 * public Iterator<Result> getTransactionsOnDate(Calendar date) { return new
	 * SafeTransactionIterator( new FilteredIterator(transactions.iterator(),
	 * transaction -> transaction.onDate(date))); }
	 */

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
	 * Getter for Date
	 * 
	 * @return date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Setter for Date
	 * 
	 * @param date member's join date
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * Getter the fee
	 * 
	 * @return fee
	 */
	public int getFee() {
		return fee;
	}

	/**
	 * Setter for Fee
	 * 
	 * @param fee member's pay the fee
	 */
	public void setFee(int fee) {
		this.fee = fee;
	}

	/**
	 * Checks whether the member is equal to the one supplied
	 * 
	 * @param object the member who should be compared
	 * @return true if the member id's match
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
		idCounter = (int) input.readObject();
	}

	/**
	 * String form of the member
	 * 
	 */
	@Override
	public String toString() {
		String string = "Member Name: " + name + ", Address: " + address + ", Phone: " + phone + ", MemberID: " + id
				+ ", Date Joined: " + this.date + ", Amount Fee: " + fee;
		string += " Purchased: [";
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			string += " " + product.getProductName();
		}
		string += "] Transactions: [";
		for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
			string += (Transaction) iterator.next();
		}
		string += "]";
		return string;
	}

}
