package org.acme.infrastructure.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

@Startup
@ApplicationScoped
public class FirebaseConfig {

    @ConfigProperty(name = "firebase.credentials", defaultValue = "")
    private String path;

    void onStart(@Observes StartupEvent ev) {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = resolveCredentials();
                if (serviceAccount == null) {
                    System.err.println("[Firebase] No se encontraron credenciales. Revisa FIREBASE_CREDENTIALS_BASE64 o firebase.credentials");
                    return;
                }
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
                System.out.println("[Firebase] Inicializado correctamente");
            }
        } catch (Exception e) {
            System.err.println("[Firebase] Error al inicializar: " + e.getMessage());
        }
    }

    private InputStream resolveCredentials() throws Exception {
        // Prioridad 1: variable de entorno con el JSON en base64 (usado en Railway)
        String base64 = System.getenv("FIREBASE_CREDENTIALS_BASE64");
        if (base64 != null && !base64.isBlank()) {
            byte[] decoded = Base64.getDecoder().decode(base64);
            return new ByteArrayInputStream(decoded);
        }
        // Prioridad 2: archivo local definido en application.properties
        if (!path.isBlank() && !path.equals("env")) {
            return new FileInputStream(path);
        }
        return null;
    }
}
