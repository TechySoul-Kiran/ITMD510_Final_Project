package models;

public class ModelTable {
	/**
	 * @param id
	 * @param name
	 * @param quantity
	 */
	public ModelTable(String id, String name, String quantity) {
	
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}

	String id, name, quantity;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
