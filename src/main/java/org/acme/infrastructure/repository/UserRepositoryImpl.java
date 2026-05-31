package org.acme.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;
import org.acme.infrastructure.entities.UserEntity;
import org.acme.infrastructure.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository, PanacheRepositoryBase<UserEntity, UUID> {


    @Override
    @Transactional
    public User create(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        persist(userEntity);
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findByFirebaseUuid(String firebaseUuid) {
        Optional<UserEntity> optionalUserEntity = find("firebaseUuid", firebaseUuid).firstResultOptional();
        optionalUserEntity.ifPresent(e -> System.out.println("Usuario encontrado: " + e.getFullName()));
        return optionalUserEntity.map(this::map);
    }

    private User map(UserEntity userEntity) {
        return UserMapper.toDomain(userEntity);
    }
}
