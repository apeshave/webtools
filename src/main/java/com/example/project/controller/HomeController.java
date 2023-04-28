package com.example.project.controller;

import com.example.project.dto.Cart;
import com.example.project.dto.CartItem;
import com.example.project.dto.Customer;
import com.example.project.dto.Product;
import com.example.project.repository.CartItemRepository;
import com.example.project.repository.CartRepository;
import com.example.project.repository.CustomerRepository;
import com.example.project.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void init(){
        Customer customer = new Customer();
        customer.setName("Pranav Kulkarni");
        customer.setEmail("kulkarnipranav98@gmail.com");
        customerRepository.save(customer);
        Product a = new Product("Product A", 10.00);
        productRepository.save(a);
        Product b = new Product("Product B", 5.00);
        productRepository.save(b);
        Product c = new Product("Product C", 20.00);
        productRepository.save(c);
    }

    @GetMapping("/create-customer")
    public String showCreateCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "create-customer";
    }

    @PostMapping("/create-customer")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // Retrieve the username from the session
        String username = (String) session.getAttribute("username");
        // Pass the username to the Thymeleaf template
        Customer customer = customerRepository.findCustomerByEmail(username);
        Cart cart = null;
        if (null == customer) {
            return "redirect:/create-customer";
        } else {
            List<CartItem> items = new ArrayList<>();
            if (customer.getCart() != null) {
                cart = cartRepository.findById(customer.getCart().getId()).get();
                log.warn(Arrays.toString(cart.getItems().toArray()));
            }else {
                cart = new Cart(items, 0);
                cart = cartRepository.save(cart);
                customer.setCart(cart);
                customerRepository.save(customer);
            }
            model.addAttribute("cart", cart.getItems());
            model.addAttribute("total", cart.getTotal());
            model.addAttribute("cartId", cart.getId());
        }
        // Return the name of the Thymeleaf template to render
        return "home";
    }
}

