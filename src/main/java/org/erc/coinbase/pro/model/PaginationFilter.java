package org.erc.coinbase.pro.model;

import lombok.Data;

@Data
public abstract class PaginationFilter {

	private String before;
	
	private String after;
	
	private int limit;
	
	public boolean hasPagination() {
		return before !=null || after != null || limit >0;
	}

	
}
