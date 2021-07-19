package br.com.baseapi.domain.exceptions;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
