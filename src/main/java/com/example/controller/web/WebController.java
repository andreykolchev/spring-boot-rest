package com.example.controller.web;

import com.example.service.book.BookService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.InetAddress;

@Controller
@EnableWebMvc
public class WebController {

    final BookService bookService;

    public WebController(@Qualifier("booksService") BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/")
    public String index(Model model) {
        String ip = InetAddress.getLoopbackAddress().getHostAddress();
        String host = InetAddress.getLoopbackAddress().getHostName();
        model.addAttribute("host", host + "(" + ip + ")");
        return "index";
    }

    @RequestMapping(value = "/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "books";
    }
}
