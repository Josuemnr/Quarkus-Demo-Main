package org.acme.infrastructure.security;

import jakarta.enterprise.context.RequestScoped;
import org.acme.domain.models.User;

@RequestScoped
public class AuthContext {
    private User user;
    private String firebaseUid;
    private String firebaseEmail;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getFirebaseEmail() {
        return firebaseEmail;
    }

    public void setFirebaseEmail(String firebaseEmail) {
        this.firebaseEmail = firebaseEmail;
    }
}
