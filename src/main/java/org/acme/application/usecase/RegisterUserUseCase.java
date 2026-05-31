package org.acme.application.usecase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.RegisterUserDto;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;

import java.util.UUID;

@ApplicationScoped
public class RegisterUserUseCase {

    @Inject
    private UserRepository userRepository;

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(RegisterUserDto registerUserDto) throws FirebaseAuthException {
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setFullName(registerUserDto.getFullName());
        user.setRole("USER");
        user.setActive(true);
        user.setId(UUID.randomUUID());
        UserRecord.CreateRequest request= new UserRecord.CreateRequest();
        request.setEmail(user.getEmail());
        request.setPassword(registerUserDto.getPassword());
        request.setDisabled(false);
        request.setEmailVerified(true);
        UserRecord firebaseUserRecord=FirebaseAuth.getInstance().createUser(request);
        user.setFirebaseUuid(firebaseUserRecord.getUid());
        return userRepository.create(user);
    }


}
