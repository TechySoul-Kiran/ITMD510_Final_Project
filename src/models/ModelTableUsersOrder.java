package models;

public class ModelTableUsersOrder {
	
	/**
	 * @param id
	 * @param pid
	 * @param pname
	 * @param qty
	 */
	public ModelTableUsersOrder(String id, String pid, String pname, String qty) {
		super();
		this.id = id;
		this.pid = pid;
		this.pname = pname;
		this.qty = qty;
	}

	String id, pid,pname,qty;

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
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
	 * @return the qty
	 */
	public String getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(String qty) {
		this.qty = qty;
	}

}
