package org.example.seminarproject.controller;

import jakarta.validation.Valid;
import org.example.seminarproject.entity.ContactMessage;
import org.example.seminarproject.repository.ContactMessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;

@Controller
public class ContactController {
    private final ContactMessageRepository contactMessageRepository;

    public ContactController(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    // Display the contact form
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    // Validate and save the message
    @PostMapping("/contact")
    public String submitContact(
            @Valid @ModelAttribute ContactMessage contactMessage,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "contact";
        }

        contactMessage.setCreatedAt(LocalDateTime.now());
        contactMessageRepository.save(contactMessage);
        return "redirect:/contact?sent";
    }

    // Display messages newest first
    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("messages",
                contactMessageRepository.findAllByOrderByCreatedAtDescIdDesc());
        return "messages";
    }
}