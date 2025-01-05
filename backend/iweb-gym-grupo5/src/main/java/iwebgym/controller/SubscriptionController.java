package iwebgym.controller;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.SubscriptionData;
import iwebgym.service.SubscriptionService;
import iwebgym.model.Suscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    // Crea un nuevo tipo de suscripción
    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        Subscription newSubscription = subscriptionService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSubscription);
    }
}
