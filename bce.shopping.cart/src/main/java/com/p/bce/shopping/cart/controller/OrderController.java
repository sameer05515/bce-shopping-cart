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
import com.p.bce.shopping.cart.rpc.pojo.CartItemDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDTO;
import com.p.bce.shopping.cart.rpc.pojo.OrderDetailDTO;

@Controller
public class OrderController {

    @Autowired
    private OrderBC orderBC;

    @GetMapping("/pages/html/postLogin/Checkout.jsp")
    public String checkout(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/pages/html/postLogin/Cart";
        }

        // Calculate totals
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItemDTO item : cart) {
            subtotal = subtotal.add(item.getSubtotal());
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("total", subtotal);

        return "pages/postLogin/Checkout";
    }

    @PostMapping("/pages/html/postLogin/PlaceOrder.jsp")
    public String placeOrder(HttpSession session, RedirectAttributes redirectAttributes) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty.");
            return "redirect:/pages/html/postLogin/Cart";
        }

        // Calculate total
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDTO item : cart) {
            total = total.add(item.getSubtotal());
        }

        // Create order
        OrderDTO order = new OrderDTO();
        order.setUserId(userName);
        order.setTotalAmount(total);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");

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
        int orderId = orderBC.createOrder(order, orderDetails);
        if (orderId > 0) {
            // Clear cart
            session.removeAttribute("cart");
            redirectAttributes.addFlashAttribute("message", "Order placed successfully! Order ID: " + orderId);
            return "redirect:/pages/html/postLogin/OrderHistory";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to place order. Please check stock availability.");
            return "redirect:/pages/html/postLogin/Cart";
        }
    }

    @GetMapping("/pages/html/postLogin/OrderHistory.jsp")
    public String orderHistory(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        List<OrderDTO> orders = orderBC.getOrdersByUser(userName);
        model.addAttribute("orders", orders);

        return "pages/postLogin/OrderHistory";
    }

    @GetMapping("/pages/html/postLogin/OrderDetails.jsp")
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

        return "redirect:/pages/html/postLogin/OrderHistory.jsp";
    }

    // Admin endpoints
    @GetMapping("/admin/orders")
    public String adminOrderList(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        List<OrderDTO> orders = orderBC.getAllOrders();
        model.addAttribute("orders", orders);

        return "admin/OrderList";
    }

    @GetMapping("/admin/orders/details")
    public String adminOrderDetails(
            @RequestParam("id") int orderId,
            HttpSession session,
            Model model) {
        
        String userName = (String) session.getAttribute("user");
        if (userName == null) {
            return "redirect:/pages/html/preLogin/Unauthorised.html";
        }

        OrderDTO order = orderBC.getOrderById(orderId);
        if (order == null) {
            return "redirect:/admin/orders?error=Order not found";
        }

        model.addAttribute("order", order);
        return "admin/OrderDetails";
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

