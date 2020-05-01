package co.simplon.blog.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Specific exception that should be thrown we a user already exists in databse.
 */
public class ExistingUsernameException extends AuthenticationException {

    // TODO ajouter : @ResponseStatus(value= HttpStatus.XXX, reason = "utilisateur existe déjà")
    public ExistingUsernameException(String msg) {
        super(msg);
    }
}
