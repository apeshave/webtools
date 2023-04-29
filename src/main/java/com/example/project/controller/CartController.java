package com.example.project.controller;

import com.example.project.dto.Cart;
import com.example.project.dto.CartItem;
import com.example.project.dto.Product;
import com.example.project.repository.CartItemRepository;
import com.example.project.repository.CartRepository;
import com.example.project.repository.ProductRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart/update/{id}")
    public String showNewCartItemForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cartId", id);
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "create-item";
    }

    @PostMapping("/cart/update/{id}")
    public String saveCartItem(@PathVariable("id") Long id, @RequestParam("productId") Long productId, @ModelAttribute("cartItem") CartItem cartItem) {
        CartItem toBeCreated = new CartItem();
        toBeCreated.setProduct(productRepository.getProductById(productId));
        toBeCreated.setQuantity(cartItem.getQuantity());
        Cart cart = cartRepository.findById(id).get();
        Set<CartItem> cartItems = cart.getItems();
        if (null == cartItems)
        {
            cart.setItems(new HashSet<>());
        }
        toBeCreated.setCart(cart);
        cartItemRepository.save(toBeCreated);
        cartItems.add(toBeCreated);
        double total = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
        cart.setTotal(total);
        cartRepository.save(cart);
        return "redirect:/home";
    }
}
