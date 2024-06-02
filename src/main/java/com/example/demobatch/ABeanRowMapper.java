package com.example.demobatch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class ABeanRowMapper implements RowMapper<ABean> {

	public static final String ID_COLUMN = "brand";
	public static final String NAME_COLUMN = "origin";
	public static final String CREDIT_COLUMN = "characteristics";

	public ABean mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		ABean customerCredit = new ABean();

		customerCredit.setCoffee_id(rs.getInt("coffee_id"));
		customerCredit.setBrand(rs.getString(ID_COLUMN));
		customerCredit.setOrigin(rs.getString(NAME_COLUMN));
		customerCredit.setCharacteristics(rs.getString(CREDIT_COLUMN));

		return customerCredit;
	}
}
