/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.p.bce.shopping.cart.rpc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author PREMENDRA
 */
public abstract class AbstractDAO {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

}
