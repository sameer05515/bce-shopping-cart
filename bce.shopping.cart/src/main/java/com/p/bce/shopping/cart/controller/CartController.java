package com.p.bce.shopping.cart.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.p.bce.shopping.cart.rpc.bc.BookDetailsBC;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;
import com.p.bce.shopping.cart.rpc.pojo.CartItemDTO;

@Controller
public class CartController {

    @Autowired
    private BookDetailsBC bookDetailsBC;

    @GetMapping({"/pages/html/postLogin/Cart.jsp", "/pages/html/postLogin/Cart"})
    public String viewCart(HttpSession session, Model model) {
        System.out.println("CartController.viewCart() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        System.out.println("Cart items: " + (cart != null ? cart.size() : "null"));

        // Calculate totals
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItemDTO item : cart) {
            subtotal = subtotal.add(item.getSubtotal());
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("total", subtotal); // For now, no tax/shipping

        System.out.println("Returning view: pages/postLogin/Cart");
        return "pages/postLogin/Cart";
    }

    @PostMapping({"/pages/html/postLogin/Inter_Cart.jsp", "/cart/add"})
    public String addToCart(
            @RequestParam(value = "chk", required = false) String[] chkParam,
            @RequestParam(value = "bookIds", required = false) String[] bookIdsParam,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        // Support both parameter names: "chk" (old) and "bookIds" (new)
        String[] bookIds = (chkParam != null && chkParam.length > 0) ? chkParam : bookIdsParam;
        
        if (bookIds == null || bookIds.length == 0) {
            redirectAttributes.addFlashAttribute("error", "Please select at least one book to add to cart.");
            return "redirect:/pages/html/postLogin/Search.jsp";
        }

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Add selected books to cart
        for (String bookIdStr : bookIds) {
            try {
                int bookId = Integer.parseInt(bookIdStr);
                BookDetailDTO book = bookDetailsBC.getBookDetail(bookId);
                
                if (book != null && book.getBookId() > 0) {
                    // Check if book already in cart
                    boolean found = false;
                    for (CartItemDTO item : cart) {
                        if (item.getBookId() == bookId) {
                            // Increase quantity if available
                            if (item.getQuantity() < book.getQuantity()) {
                                item.setQuantity(item.getQuantity() + 1);
                            }
                            found = true;
                            break;
                        }
                    }
                    
                    // Add new item if not found
                    if (!found) {
                        CartItemDTO cartItem = new CartItemDTO();
                        cartItem.setBookId(book.getBookId());
                        cartItem.setTitle(book.getTitle());
                        cartItem.setAuthor(book.getAuthor());
                        cartItem.setPublisher(book.getPublisher());
                        cartItem.setEdition(book.getEdition());
                        cartItem.setPrice(BigDecimal.valueOf(book.getPrice()));
                        cartItem.setQuantity(1);
                        cartItem.setAvailableQuantity(book.getQuantity());
                        cartItem.setDescription(book.getDescription());
                        cart.add(cartItem);
                    }
                }
            } catch (NumberFormatException e) {
                // Skip invalid book IDs
            }
        }

        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("message", "Items added to cart successfully!");
        return "redirect:/pages/html/postLogin/Cart";
    }

    @PostMapping("/pages/html/postLogin/Cart/update")
    public String updateCartItem(
            javax.servlet.http.HttpServletRequest request,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null) {
            return "redirect:/pages/html/postLogin/Cart";
        }

        // Update quantities for all items
        java.util.Map<String, String[]> params = request.getParameterMap();
        boolean hasError = false;
        java.util.List<CartItemDTO> itemsToRemove = new ArrayList<>();
        
        for (CartItemDTO item : cart) {
            String paramName = "quantity_" + item.getBookId();
            String[] quantityValues = params.get(paramName);
            if (quantityValues != null && quantityValues.length > 0) {
                try {
                    int quantity = Integer.parseInt(quantityValues[0]);
                    if (quantity <= 0) {
                        itemsToRemove.add(item);
                    } else if (quantity <= item.getAvailableQuantity()) {
                        item.setQuantity(quantity);
                    } else {
                        hasError = true;
                        redirectAttributes.addFlashAttribute("error", "Quantity exceeds available stock for " + item.getTitle() + ".");
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid quantities
                }
            }
        }
        
        // Remove items after iteration to avoid concurrent modification
        cart.removeAll(itemsToRemove);

        session.setAttribute("cart", cart);
        if (!hasError) {
            redirectAttributes.addFlashAttribute("message", "Cart updated successfully!");
        }
        return "redirect:/pages/html/postLogin/Cart";
    }

    @PostMapping("/pages/html/postLogin/Cart/remove")
    public String removeFromCart(
            @RequestParam("bookId") int bookId,
            HttpSession session) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getBookId() == bookId);
            session.setAttribute("cart", cart);
        }

        return "redirect:/pages/html/postLogin/Cart";
    }

    @PostMapping("/pages/html/postLogin/Cart/clear")
    public String clearCart(HttpSession session) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        session.removeAttribute("cart");
        return "redirect:/pages/html/postLogin/Cart";
    }

}

