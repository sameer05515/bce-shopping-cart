package com.p.bce.shopping.cart.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.p.bce.shopping.cart.rpc.bc.OrderBC;
import com.p.bce.shopping.cart.rpc.bc.UserProfileBC;
import com.p.bce.shopping.cart.rpc.pojo.CartItemDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDetailDTO;

@Controller
public class OrderController {

    @Autowired
    private OrderBC orderBC;
    
    @Autowired
    private UserProfileBC userProfileBC;

    @GetMapping({"/pages/html/postLogin/Checkout.jsp", "/pages/html/postLogin/Checkout"})
    public String checkout(HttpSession session, Model model) {
        System.out.println("OrderController.checkout() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            System.out.println("Cart is empty, redirecting to Cart page");
            return "redirect:/pages/html/postLogin/Cart";
        }

        System.out.println("Cart has " + cart.size() + " items");
        
        // Load user profile for shipping address
        try {
            com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO userProfile = userProfileBC.getUserProfile(userName);
            model.addAttribute("userProfile", userProfile);
        } catch (Exception e) {
            System.err.println("Error loading user profile: " + e.getMessage());
        }
        
        // Calculate totals
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItemDTO item : cart) {
            subtotal = subtotal.add(item.getSubtotal());
        }
        
        // Calculate tax (18% GST)
        BigDecimal taxRate = new BigDecimal("0.18");
        BigDecimal tax = subtotal.multiply(taxRate);
        
        // Calculate shipping (Free for orders above ₹500, otherwise ₹50)
        BigDecimal shipping = BigDecimal.ZERO;
        if (subtotal.compareTo(new BigDecimal("500")) < 0) {
            shipping = new BigDecimal("50");
        }
        
        // Calculate total
        BigDecimal total = subtotal.add(tax).add(shipping);

        model.addAttribute("cartItems", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        model.addAttribute("shipping", shipping);
        model.addAttribute("total", total);

        System.out.println("Returning view: pages/postLogin/Checkout");
        return "pages/postLogin/Checkout";
    }

    @GetMapping({"/pages/html/postLogin/PlaceOrder.jsp", "/pages/html/postLogin/PlaceOrder"})
    public String placeOrderGet(HttpSession session) {
        // If accessed via GET, redirect to Checkout page
        return "redirect:/pages/html/postLogin/Checkout";
    }

    @PostMapping({"/pages/html/postLogin/PlaceOrder.jsp", "/pages/html/postLogin/PlaceOrder"})
    public String placeOrder(
            @RequestParam(value = "orderNotes", required = false) String orderNotes,
            HttpSession session, 
            RedirectAttributes redirectAttributes) {
        System.out.println("OrderController.placeOrder() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            System.out.println("Cart is empty, redirecting to Cart page");
            redirectAttributes.addFlashAttribute("error", "Your cart is empty.");
            return "redirect:/pages/html/postLogin/Cart";
        }

        System.out.println("Cart has " + cart.size() + " items");

        // Calculate totals (same as checkout page)
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItemDTO item : cart) {
            subtotal = subtotal.add(item.getSubtotal());
        }
        
        // Calculate tax (18% GST)
        BigDecimal taxRate = new BigDecimal("0.18");
        BigDecimal tax = subtotal.multiply(taxRate);
        
        // Calculate shipping (Free for orders above ₹500, otherwise ₹50)
        BigDecimal shipping = BigDecimal.ZERO;
        if (subtotal.compareTo(new BigDecimal("500")) < 0) {
            shipping = new BigDecimal("50");
        }
        
        // Calculate total
        BigDecimal total = subtotal.add(tax).add(shipping);

        // Create order
        OrderDTO order = new OrderDTO();
        order.setUserId(userName);
        order.setTotalAmount(total);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        
        // Store order notes in session for now (can be added to order table later)
        if (orderNotes != null && !orderNotes.trim().isEmpty()) {
            session.setAttribute("orderNotes_" + userName, orderNotes);
            System.out.println("Order notes: " + orderNotes);
        }

        // Convert cart items to order details
        List<OrderDetailDTO> orderDetails = new java.util.ArrayList<>();
        for (CartItemDTO cartItem : cart) {
            OrderDetailDTO detail = new OrderDetailDTO();
            detail.setBookId(cartItem.getBookId());
            detail.setQuantity(cartItem.getQuantity());
            detail.setPrice(cartItem.getPrice());
            orderDetails.add(detail);
        }

        // Create order
        System.out.println("Creating order with " + orderDetails.size() + " items, total: " + total);
        int orderId = orderBC.createOrder(order, orderDetails);
        System.out.println("Order created with ID: " + orderId);
        if (orderId > 0) {
            // Clear cart
            session.removeAttribute("cart");
            redirectAttributes.addFlashAttribute("message", "Order placed successfully! Order ID: " + orderId);
            System.out.println("Order placed successfully, redirecting to OrderHistory");
            return "redirect:/pages/html/postLogin/OrderHistory";
        } else {
            System.err.println("ERROR: Failed to create order");
            redirectAttributes.addFlashAttribute("error", "Failed to place order. Please check stock availability.");
            return "redirect:/pages/html/postLogin/Cart";
        }
    }

    @GetMapping({"/pages/html/postLogin/OrderHistory.jsp", "/pages/html/postLogin/OrderHistory"})
    public String orderHistory(HttpSession session, Model model) {
        System.out.println("OrderController.orderHistory() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        try {
            List<OrderDTO> orders = orderBC.getOrdersByUser(userName);
            System.out.println("Orders retrieved: " + (orders != null ? orders.size() : "null"));
            
            // Ensure orders list is never null
            if (orders == null) {
                orders = new java.util.ArrayList<>();
            }
            
            model.addAttribute("orders", orders);
            System.out.println("Returning view: pages/postLogin/OrderHistory");
            return "pages/postLogin/OrderHistory";
        } catch (Exception e) {
            System.err.println("ERROR in orderHistory: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            // On error, still return the template with empty list
            model.addAttribute("orders", new java.util.ArrayList<OrderDTO>());
            model.addAttribute("error", "Error loading order history: " + e.getMessage());
            return "pages/postLogin/OrderHistory";
        }
    }

    @GetMapping({"/pages/html/postLogin/OrderDetails.jsp", "/pages/html/postLogin/OrderDetails"})
    public String orderDetails(
            @RequestParam("id") int orderId,
            HttpSession session,
            Model model) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        OrderDTO order = orderBC.getOrderById(orderId);
        if (order == null) {
            return "redirect:/pages/html/postLogin/OrderHistory?error=Order not found";
        }

        // Verify user owns the order
        if (!order.getUserId().equals(userName)) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        model.addAttribute("order", order);
        return "pages/postLogin/OrderDetails";
    }

    @PostMapping("/pages/html/postLogin/Order/Cancel")
    public String cancelOrder(
            @RequestParam("id") int orderId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = orderBC.cancelOrder(orderId, userName);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Order cancelled successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel order.");
        }

            return "redirect:/pages/html/postLogin/OrderHistory";
    }

    // Admin endpoints
    @GetMapping("/admin/orders")
    public String adminOrderList(HttpSession session, Model model) {
        System.out.println("OrderController.adminOrderList() called");
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        try {
            List<OrderDTO> orders = orderBC.getAllOrders();
            System.out.println("All orders retrieved: " + (orders != null ? orders.size() : "null"));
            
            // Ensure orders list is never null
            if (orders == null) {
                orders = new java.util.ArrayList<>();
            }
            
            model.addAttribute("orders", orders);
            System.out.println("Returning view: admin/OrderList");
            return "admin/OrderList";
        } catch (Exception e) {
            System.err.println("ERROR in adminOrderList: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            // On error, still return the template with empty list
            model.addAttribute("orders", new java.util.ArrayList<OrderDTO>());
            model.addAttribute("error", "Error loading orders: " + e.getMessage());
            return "admin/OrderList";
        }
    }

    @GetMapping("/admin/orders/details")
    public String adminOrderDetails(
            @RequestParam("id") int orderId,
            HttpSession session,
            Model model) {
        
        System.out.println("OrderController.adminOrderDetails() called for order ID: " + orderId);
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            System.out.println("User not logged in, redirecting to Unauthorised");
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        System.out.println("User logged in: " + userName);
        try {
            OrderDTO order = orderBC.getOrderById(orderId);
            if (order == null) {
                System.out.println("Order not found: " + orderId);
                return "redirect:/admin/orders?error=Order not found";
            }

            System.out.println("Order found: " + order.getOrderId() + " for user: " + order.getUserId());
            model.addAttribute("order", order);
            System.out.println("Returning view: admin/OrderDetails");
            return "admin/OrderDetails";
        } catch (Exception e) {
            System.err.println("ERROR in adminOrderDetails: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            return "redirect:/admin/orders?error=" + e.getMessage();
        }
    }

    @PostMapping("/admin/orders/updateStatus")
    public String updateOrderStatus(
            @RequestParam("id") int orderId,
            @RequestParam("status") String status,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        boolean success = orderBC.updateOrderStatus(orderId, status);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Order status updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update order status.");
        }

        return "redirect:/admin/orders";
    }

}

