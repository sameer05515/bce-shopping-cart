package com.p.bce.shopping.cart.rpc.bc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.bce.shopping.cart.rpc.dao.BookDetailsDAO;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;

@Service
public class BookDetailsBC {
	
	@Autowired
	private BookDetailsDAO objBookDetailsDAO;

	public BookDetailDTO getBookDetail(int bookId) {

		return objBookDetailsDAO.getBookDetail(bookId);
	}

}
