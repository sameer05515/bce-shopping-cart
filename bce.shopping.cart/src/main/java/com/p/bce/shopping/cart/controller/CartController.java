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
import com.p.bce.shopping.cart.rpc.bc.CartBC;
import com.p.bce.shopping.cart.rpc.bc.WishlistBC;
import com.p.bce.shopping.cart.rpc.pojo.BookDetailDTO;
import com.p.bce.shopping.cart.rpc.pojo.CartItemDTO;

@Controller
public class CartController {

    @Autowired
    private BookDetailsBC bookDetailsBC;
    
    @Autowired
    private WishlistBC wishlistBC;
    
    @Autowired
    private CartBC cartBC;

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
        
        // Load cart from database if session cart is empty
        if (cart == null || cart.isEmpty()) {
            List<CartItemDTO> dbCart = cartBC.getCartItems(userName);
            if (dbCart != null && !dbCart.isEmpty()) {
                cart = dbCart;
                session.setAttribute("cart", cart);
                System.out.println("Loaded " + cart.size() + " items from database cart");
            } else {
                cart = new ArrayList<>();
            }
        }
        
        if (cart == null) {
            cart = new ArrayList<>();
        }

        System.out.println("Cart items: " + (cart != null ? cart.size() : "null"));

        // Calculate totals
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItemDTO item : cart) {
            subtotal = subtotal.add(item.getSubtotal());
        }
        
        // Calculate estimated tax (18% GST)
        BigDecimal taxRate = new BigDecimal("0.18");
        BigDecimal estimatedTax = subtotal.multiply(taxRate);
        
        // Calculate estimated shipping (Free for orders above ₹500, otherwise ₹50)
        BigDecimal estimatedShipping = BigDecimal.ZERO;
        if (subtotal.compareTo(new BigDecimal("500")) < 0) {
            estimatedShipping = new BigDecimal("50");
        }
        
        // Calculate estimated total
        BigDecimal estimatedTotal = subtotal.add(estimatedTax).add(estimatedShipping);

        try {
            model.addAttribute("cartItems", cart);
            model.addAttribute("subtotal", subtotal);
            model.addAttribute("estimatedTax", estimatedTax);
            model.addAttribute("estimatedShipping", estimatedShipping);
            model.addAttribute("estimatedTotal", estimatedTotal);
            model.addAttribute("total", subtotal); // Keep for backward compatibility
            
            // Add helper attributes for template comparisons
            model.addAttribute("subtotalValue", subtotal.doubleValue());
            model.addAttribute("estimatedShippingValue", estimatedShipping.doubleValue());
            model.addAttribute("isFreeShipping", estimatedShipping.compareTo(BigDecimal.ZERO) == 0);
            model.addAttribute("needsMoreForFreeShipping", subtotal.compareTo(new BigDecimal("500")) < 0);
            if (subtotal.compareTo(new BigDecimal("500")) < 0) {
                BigDecimal remaining = new BigDecimal("500").subtract(subtotal);
                model.addAttribute("remainingForFreeShipping", remaining);
            }

            System.out.println("Cart subtotal: " + subtotal);
            System.out.println("Estimated tax: " + estimatedTax);
            System.out.println("Estimated shipping: " + estimatedShipping);
            System.out.println("Estimated total: " + estimatedTotal);
            System.out.println("Returning view: pages/postLogin/Cart");
            return "pages/postLogin/Cart";
        } catch (Exception e) {
            System.err.println("ERROR in viewCart: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            // On error, still return the template with basic data
            model.addAttribute("cartItems", cart);
            model.addAttribute("subtotal", subtotal);
            model.addAttribute("estimatedTax", BigDecimal.ZERO);
            model.addAttribute("estimatedShipping", BigDecimal.ZERO);
            model.addAttribute("estimatedTotal", subtotal);
            model.addAttribute("total", subtotal);
            model.addAttribute("isFreeShipping", true);
            model.addAttribute("needsMoreForFreeShipping", false);
            model.addAttribute("error", "Error calculating cart totals: " + e.getMessage());
            return "pages/postLogin/Cart";
        }
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
        
        // Save to database for persistence
        cartBC.saveCart(userName, cart);
        
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
        
        // Save to database for persistence
        cartBC.saveCart(userName, cart);
        
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
            
            // Remove from database
            cartBC.removeCartItem(userName, bookId);
        }

        return "redirect:/pages/html/postLogin/Cart";
    }

    @PostMapping("/pages/html/postLogin/Cart/clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        session.removeAttribute("cart");
        
        // Clear from database
        cartBC.clearCart(userName);
        
        redirectAttributes.addFlashAttribute("message", "Cart cleared successfully!");
        return "redirect:/pages/html/postLogin/Cart";
    }
    
    /**
     * Save item for later - move from cart to wishlist
     */
    @PostMapping("/pages/html/postLogin/Cart/saveForLater")
    public String saveForLater(
            @RequestParam("bookId") int bookId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Cart is empty.");
            return "redirect:/pages/html/postLogin/Cart";
        }

        // Find the item in cart
        CartItemDTO itemToMove = null;
        for (CartItemDTO item : cart) {
            if (item.getBookId() == bookId) {
                itemToMove = item;
                break;
            }
        }

        if (itemToMove == null) {
            redirectAttributes.addFlashAttribute("error", "Item not found in cart.");
            return "redirect:/pages/html/postLogin/Cart";
        }

        // Add to wishlist
        boolean added = wishlistBC.addToWishlist(userName, bookId);
        if (added) {
            // Remove from cart
            cart.removeIf(item -> item.getBookId() == bookId);
            session.setAttribute("cart", cart);
            
            // Remove from database
            cartBC.removeCartItem(userName, bookId);
            
            redirectAttributes.addFlashAttribute("message", itemToMove.getTitle() + " moved to wishlist!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Item is already in your wishlist or could not be moved.");
        }

        return "redirect:/pages/html/postLogin/Cart";
    }
    
    /**
     * Quick update quantity for a single item
     */
    @PostMapping("/pages/html/postLogin/Cart/updateQuantity")
    public String updateQuantity(
            @RequestParam("bookId") int bookId,
            @RequestParam("quantity") int quantity,
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

        // Find and update the item
        for (CartItemDTO item : cart) {
            if (item.getBookId() == bookId) {
                // Refresh stock availability
                BookDetailDTO book = bookDetailsBC.getBookDetail(bookId);
                if (book != null && book.getBookId() > 0) {
                    item.setAvailableQuantity(book.getQuantity());
                }
                
                if (quantity <= 0) {
                    cart.remove(item);
                    cartBC.removeCartItem(userName, bookId);
                    redirectAttributes.addFlashAttribute("message", item.getTitle() + " removed from cart.");
                } else if (quantity <= item.getAvailableQuantity()) {
                    item.setQuantity(quantity);
                    cartBC.saveCartItem(userName, bookId, quantity);
                    redirectAttributes.addFlashAttribute("message", "Quantity updated successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("error", 
                        "Only " + item.getAvailableQuantity() + " items available for " + item.getTitle() + ".");
                }
                break;
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/pages/html/postLogin/Cart";
    }
    
    /**
     * Refresh cart - update stock availability and prices
     */
    @PostMapping("/pages/html/postLogin/Cart/refresh")
    public String refreshCart(HttpSession session, RedirectAttributes redirectAttributes) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/pages/html/postLogin/Cart";
        }

        List<CartItemDTO> itemsToRemove = new ArrayList<>();
        boolean hasChanges = false;

        // Refresh each item's stock and price
        for (CartItemDTO item : cart) {
            BookDetailDTO book = bookDetailsBC.getBookDetail(item.getBookId());
            if (book != null && book.getBookId() > 0) {
                // Update available quantity
                int oldAvailable = item.getAvailableQuantity();
                item.setAvailableQuantity(book.getQuantity());
                
                // Update price if changed
                BigDecimal newPrice = BigDecimal.valueOf(book.getPrice());
                if (item.getPrice().compareTo(newPrice) != 0) {
                    item.setPrice(newPrice);
                    hasChanges = true;
                }
                
                // Check if item is out of stock
                if (book.getQuantity() == 0) {
                    itemsToRemove.add(item);
                    hasChanges = true;
                } else if (item.getQuantity() > book.getQuantity()) {
                    // Adjust quantity if exceeds available stock
                    item.setQuantity(book.getQuantity());
                    hasChanges = true;
                }
            } else {
                // Book no longer exists
                itemsToRemove.add(item);
                hasChanges = true;
            }
        }

        // Remove unavailable items
        for (CartItemDTO item : itemsToRemove) {
            cartBC.removeCartItem(userName, item.getBookId());
        }
        cart.removeAll(itemsToRemove);

        session.setAttribute("cart", cart);
        
        // Sync with database
        cartBC.saveCart(userName, cart);
        
        if (hasChanges) {
            if (!itemsToRemove.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", 
                    itemsToRemove.size() + " item(s) removed due to unavailability.");
            } else {
                redirectAttributes.addFlashAttribute("message", "Cart refreshed successfully!");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Cart is up to date!");
        }

        return "redirect:/pages/html/postLogin/Cart";
    }

}

