package br.com.grpcbooks.exception;

import io.grpc.Status;

public class BookNotFoundException extends BaseBusinessException {

    private static final String ERROR_MESSAGE = "Livro com ID %s não encontrado.";
    private final Long id;

    public BookNotFoundException(Long id) {
        super(String.format(ERROR_MESSAGE, id));
        this.id = id;
    }

    @Override
    public Status getStatusCode() {
        return Status.NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return String.format(ERROR_MESSAGE, this.id);
    }
}
