package com.p.bce.shopping.cart.rpc.bc;

import com.p.bce.shopping.cart.rpc.dao.BookDetailsDAO;
import com.p.bce.shopping.cart.rpc.dao.CategoryDetailsDAO;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;

public class BookDetailsBC {
	
private BookDetailsDAO objBookDetailsDAO;
	
	public BookDetailsBC() {
		objBookDetailsDAO=new BookDetailsDAO();
	}

	public BookDetailDTO getBookDetail(int bookId) {

		return objBookDetailsDAO.getBookDetail(bookId);
	}

}
