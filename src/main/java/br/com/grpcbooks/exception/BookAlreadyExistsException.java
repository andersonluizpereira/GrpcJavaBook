package br.com.grpcbooks.exception;

import io.grpc.Status;

public class BookAlreadyExistsException extends BaseBusinessException {

    private static final String ERROR_MESSAGE = "Livro %s já cadastrado no sistema.";
    private final String name;

    public BookAlreadyExistsException(String name) {
        super(String.format(ERROR_MESSAGE, name));
        this.name = name;
    }

    @Override
    public Status getStatusCode() {
        return Status.ALREADY_EXISTS;
    }

    @Override
    public String getErrorMessage() {
        return String.format(ERROR_MESSAGE, this.name);
    }
}
