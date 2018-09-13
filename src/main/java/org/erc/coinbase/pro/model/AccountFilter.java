package org.erc.coinbase.pro.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AccountFilter extends PaginationFilter{

	private String id;

}
