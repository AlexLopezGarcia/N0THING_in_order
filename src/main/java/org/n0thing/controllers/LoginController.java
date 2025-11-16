package org.n0thing.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.n0thing.models.User;
import org.n0thing.services.AuthService;
import org.n0thing.utils.ValidationUtils;

public class LoginController {

    // Elements del formulario de login
    @FXML private VBox loginForm;
    @FXML private TextField loginEmailField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label loginErrorLabel;

    // Elementos del formulario de registro
    @FXML private VBox registerForm;
    @FXML private TextField registerNameField;
    @FXML private TextField registerEmailField;
    @FXML private PasswordField registerPasswordField;
    @FXML private PasswordField registerConfirmPasswordField;
    @FXML private Label registerErrorLabel;
    @FXML private Label passwordStrengthLabel;

    // Botones de pestañas
    @FXML private Button loginTabButton;
    @FXML private Button registerTabButton;
    @FXML private Button closeButton;

    @FXML
    private void initialize() {
        //Configuracion inicial si es necesario
    }

    @FXML
    private void handleLogin() {
        String email = loginEmailField.getText().trim();
        String password = loginPasswordField.getText();

        // Validaciones
        if (email.isEmpty() || password.isEmpty()) {
            showLoginError("Por favor completa todos los campos");
            return;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            showLoginError("El formato del correo no es válido");
            return;
        }

        // Intentar login
        User user = AuthService.login(email, password);

        if (user != null) {
            System.out.println("✓ Login exitoso: " + user.getNombre());
            // TODO: Abrir dashboard principal
            showLoginError("Login exitoso! (Dashboard pendiente)");
        } else {
            showLoginError("Correo o contraseña incorrectos");
        }
    }

    @FXML
    private void handleRegister() {
        String nombre = registerNameField.getText().trim();
        String email = registerEmailField.getText().trim();
        String password = registerPasswordField.getText();
        String confirmPassword = registerConfirmPasswordField.getText();

        // Validaciones
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showRegisterError("Por favor completa todos los campos");
            return;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            showRegisterError("El formato del correo no es válido");
            return;
        }

        if (AuthService.emailExists(email)) {
            showRegisterError("Este correo ya está registrado");
            return;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            showRegisterError("La contraseña debe tener mínimo 8 caracteres, una mayúscula, una minúscula y un número");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showRegisterError("Las contraseñas no coinciden");
            return;
        }

        // Registrar usuario
        boolean success = AuthService.register(email, password, nombre);

        if (success) {
            System.out.println("✓ Usuario registrado exitosamente");
            clearRegisterForm();
            showLoginForm();
            showLoginError("¡Cuenta creada! Ahora puedes iniciar sesión");
        } else {
            showRegisterError("Error al crear la cuenta. Intenta nuevamente");
        }
    }

    @FXML
    private void showLoginForm() {
        loginForm.setVisible(true);
        loginForm.setManaged(true);
        registerForm.setVisible(false);
        registerForm.setManaged(false);

        loginTabButton.getStyleClass().add("tab-button-active");
        registerTabButton.getStyleClass().remove("tab-button-active");

        hideErrors();
    }

    @FXML
    private void showRegisterForm() {
        registerForm.setVisible(true);
        registerForm.setManaged(true);
        loginForm.setVisible(false);
        loginForm.setManaged(false);

        registerTabButton.getStyleClass().add("tab-button-active");
        loginTabButton.getStyleClass().remove("tab-button-active");

        hideErrors();
    }

    @FXML
    private void checkPasswordStrength() {
        String password = registerPasswordField.getText();
        String strength = ValidationUtils.getPasswordStrength(password);

        passwordStrengthLabel.setText("Seguridad: " + strength);
        passwordStrengthLabel.getStyleClass().clear();
        passwordStrengthLabel.getStyleClass().add("info-label");

        if (strength.equals("Débil")) {
            passwordStrengthLabel.getStyleClass().add("strength-weak");
        } else if (strength.equals("Media")) {
            passwordStrengthLabel.getStyleClass().add("strength-medium");
        } else {
            passwordStrengthLabel.getStyleClass().add("strength-strong");
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void showLoginError(String message) {
        loginErrorLabel.setText(message);
        loginErrorLabel.setVisible(true);
        loginErrorLabel.setManaged(true);
    }

    private void showRegisterError(String message) {
        registerErrorLabel.setText(message);
        registerErrorLabel.setVisible(true);
        registerErrorLabel.setManaged(true);
    }

    private void hideErrors() {
        loginErrorLabel.setVisible(false);
        loginErrorLabel.setManaged(false);
        registerErrorLabel.setVisible(false);
        registerErrorLabel.setManaged(false);
    }

    private void clearRegisterForm() {
        registerNameField.clear();
        registerEmailField.clear();
        registerPasswordField.clear();
        registerConfirmPasswordField.clear();
        passwordStrengthLabel.setText("");
    }

}
