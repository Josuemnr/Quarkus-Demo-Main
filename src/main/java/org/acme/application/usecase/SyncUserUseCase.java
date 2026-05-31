package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SyncUserUseCase {

    @Inject
    UserRepository userRepository;

    public SyncUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String firebaseUid, String email) {
        Optional<User> existing = userRepository.findByFirebaseUuid(firebaseUid);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Usuario creado manualmente en Firebase console — lo registramos en la BD local
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirebaseUuid(firebaseUid);
        user.setEmail(email);
        user.setFullName(email.split("@")[0]);
        user.setRole("USER");
        user.setActive(true);
        return userRepository.create(user);
    }
}
