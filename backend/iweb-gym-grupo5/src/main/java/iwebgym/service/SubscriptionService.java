package iwebgym.service;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.SubscriptionData;
import iwebgym.model.Suscripcion;
import iwebgym.repository.SubsciptionRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    // Devuelve todos los tipos de suscripción
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    // Crea un nuevo tipo de suscripción
    public Subscription createSubscription(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = new Subscription();
        subscription.setNombre(subscriptionDTO.getNombre());
        subscription.setPrecio(subscriptionDTO.getPrecio());
        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscriptionById(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new NoSuchElementException("La suscripción con ID " + id + " no existe.");
        }
        subscriptionRepository.deleteById(id);
    }
}

