package models;

public class ModelTableOrders {
	
	/**
	 * @param id
	 * @param pid
	 * @param pname
	 * @param qty
	 * @param custid
	 * @param custname
	 */
	public ModelTableOrders(String id, String pid, String pname, String qty, String custid, String custname) {
		
		this.id = id;
		this.pid = pid;
		this.pname = pname;
		this.qty = qty;
		this.custid = custid;
		this.custname = custname;
	}

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

	/**
	 * @return the custid
	 */
	public String getCustid() {
		return custid;
	}

	/**
	 * @param custid the custid to set
	 */
	public void setCustid(String custid) {
		this.custid = custid;
	}

	/**
	 * @return the custname
	 */
	public String getCustname() {
		return custname;
	}

	/**
	 * @param custname the custname to set
	 */
	public void setCustname(String custname) {
		this.custname = custname;
	}

	String id,pid,pname,qty,custid,custname;
	

}
