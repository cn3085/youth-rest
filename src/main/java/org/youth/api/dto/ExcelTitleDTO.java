package org.youth.api.dto;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExcelTitleDTO {
	
	private String columnCode;
	private String columnName;
	private int columnSize;
	
}
