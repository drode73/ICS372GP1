package edu.metrostate.ics372.project1.entities;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String productName;
	private String productID;
	private int productPrice;
	private int productReorder;

	/**
	 * 
	 * @param name  Product Name
	 * @param id    Product ID
	 * @param price Product Price
	 * @param order Product Minimum Reorder
	 */
	public Product(String name, String id, int price, int order) {
		this.productName = name;
		this.productID = id;
		this.productPrice = price;
		this.productReorder = order;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * Get the Product by Reordering (multiply by 2)
	 * 
	 * @return productReorder
	 */
	public int getProductReorder() {
		return productReorder * 2;
	}

	public void setProductReorder(int productReorder) {
		this.productReorder = productReorder;
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
		Product other = (Product) object;
		if (productID == null) {
			if (other.productID != null) {
				return false;
			}
		} else if (!productID.equals(other.productID)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productID == null) ? 0 : productID.hashCode());
		return result;
	}

	public String toString() {
		return "Product Name: " + productName + "Product ID: " + productID + "Product Price: " + productPrice
				+ "Minimum Reorder Level: " + productReorder;
	}

}
