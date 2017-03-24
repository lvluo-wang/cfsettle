package com.upg.ucars.framework.base;



/**
 * 分页
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class Page{
	
	/**默认每页行数*/
	public static final int Page_Size_Default = 10;
	public static final String SORT_ASC="asc";
	public static final String SORT_DESC="desc";
	
	/** 总行数 */
	private int totalRows;
	/** 每页显示的行数 */
	private int pageSize = Page_Size_Default;
	/** 当前页号 */
	private int currentPage = 1;
	/** 总页数 */
	private int totalPages;
	/** 排序名称 */
	private String sortName;
	/** 排序方式asc/desc */
	private String sortOrder;
	/** 是否查询所有数据，true 则不分页 */
	private boolean queryAll;  
	
	

	public void afterNew(int _totalRows) {
		totalRows = _totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}		
	}
	

	public int getTotalRows() {
		return totalRows;
	}

	public String getSortName() {
		return sortName;
	}


	public void setSortName(String sortName) {
		this.sortName = sortName;
	}


	public String getSortOrder() {
		return sortOrder;
	}


	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}


	public void first() {
		currentPage = 1;
	}

	public void previous() {
		if (currentPage == 1) {
			return;
		}
		currentPage--;
		
	}

	public void next() {
		if (currentPage < totalPages) {
			currentPage++;
		}
	}

	public void last() {
		currentPage = totalPages;
	}
	
	
	public int getStartRow() {
		return (currentPage - 1) * pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		if (currentPage<1){
			this.currentPage =1;
		}
		// 重新设置开始行
		
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	

	public boolean isQueryAll() {
		return queryAll;
	}


	public void setQueryAll(boolean queryAll) {
		this.queryAll = queryAll;
	}


	
}
