package br.com.grpcbooks.util;

import br.com.grpcbooks.domain.Book;
import br.com.grpcbooks.dto.BookInputDTO;
import br.com.grpcbooks.dto.BookOutputDTO;

public class BookConverterUtil {

    public static BookOutputDTO bootToBookOutputDto(Book product) {
        return new BookOutputDTO(
                product.getId(),
                product.getTitle(),
                product.getAuthor(),
                product.getIsbn(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock()
        );
    }

    public static Book bookInputDtoToBook(BookInputDTO product) {
        return new Book(
                null,
                product.getTitle(),
                product.getAuthor(),
                product.getIsbn(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock()
        );
    }
}

