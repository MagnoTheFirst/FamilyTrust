package ch.my.familytrust.controllers;

import ch.my.familytrust.entities.Account;
import ch.my.familytrust.services.AccountManagementService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("api/v1/frontend")
public class FrontendController {


    private final AccountManagementService accountManagementService;

    public FrontendController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("message", "Willkommen bei meiner Spring Boot App mit Thymeleaf!");
        return "accounts"; // Verweist auf die Template-Datei "index.html"
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("message", "Willkommen bei meiner Spring Boot App mit Thymeleaf!");
        return "account"; // Verweist auf die Template-Datei "index.html"
    }



    /**
     * Zeigt die Kontenübersichtsseite für einen bestimmten Benutzer an.
     * Enthält das Formular zur Kontoerstellung und die Liste der bestehenden Konten.
     *
     * @param userId Die ID des Benutzers, dessen Konten angezeigt werden sollen.
     * @param model Das Spring Model-Objekt zur Übergabe von Daten an das Template.
     * @return Der Name des Thymeleaf-Templates (konten.html).
     */
    @GetMapping("/user/{userId}/accounts")
    public String showAccounts(@PathVariable UUID userId, Model model) {
        // Ein neues AccountForm-Objekt für das Formular erstellen
        if (!model.containsAttribute("accountForm")) {
            model.addAttribute("accountForm", new Account());
        }

        // Die ID des Benutzers an das Template übergeben, da das Formular diese benötigt
        model.addAttribute("userId", userId);

        // Alle Konten für diesen Benutzer abrufen (in einer echten App nach userId filtern)
        List<Account> accounts = accountManagementService.getAccountsByUserId(userId);
        model.addAttribute("accounts", accounts);

        return "accounts"; // Verweist auf die Template-Datei `konten.html`
    }

}
