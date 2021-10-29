package org.oobook.grocerystore.business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProductList implements Serializable {
	private static ProductList productList;
	private List products = new LinkedList();

	/**
	 * Private constructor to create singleton
	 */
	private ProductList() {

	}

	/**
	 * ProductList Singleton
	 * 
	 * @return the ProductList singleton object
	 */
	public static ProductList instance() {
		if (productList == null) {
			return (productList = new ProductList());
		} else {
			return productList;
		}
	}

	/**
	 * Add a product to the Collection
	 * 
	 * @param product
	 * @return a boolean indicating successful addition to collection
	 */
	public boolean insertProduct(Product product) {
		products.add(product);
		return true;
	}

	/**
	 * Return a list of Products
	 */
	public void getProductList() {
		Iterator result = products.iterator();
		System.out.println("The List of Products:");
		while (result.hasNext()) {
			System.out.println(result.next());
		}
	}

	/**
	 * write objects for serialization
	 * 
	 * @param output stream
	 */
	private void writeObject(ObjectOutputStream output) {
		try {
			// output.defaultWriteObject();
			output.writeObject(productList);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/**
	 * read serialized object
	 * 
	 * @param input stream
	 */
	private void readObject(ObjectInputStream input) {
		try {
			if (productList != null) {
				return;
			} else {
				// input.defaultReadObject();
				if (productList == null) {
					productList = (ProductList) input.readObject();
				} else {
					input.readObject();
				}
			}
		} catch (IOException ioe) {
			System.out.println("in ProductList readObject \n" + ioe);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * String of the Product
	 */
	@Override
	public String toString() {
		return products.toString();
	}
}
