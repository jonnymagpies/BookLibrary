package com.ne62.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UserIDGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		
		String prefix = "U";
		  String suffix = "";
		  try {
		   Connection connection = session.connection();
		   Statement statement = connection.createStatement();
		   ResultSet resultSet = statement.executeQuery("select count(member_id) from members");
		   if(resultSet.next()) {
		    Integer id = resultSet.getInt(1) + 1;
		    suffix = String.format("%05d", id);;
		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		return prefix + suffix;
	}

}
