package br.com.grpcbooks.util;

import br.com.grpcbooks.domain.Book;
import br.com.grpcbooks.dto.BookInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookConverterUtilTest {

    @Test
    void productToBookOutputDtoTest() {
        var product = new Book(1L,
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);
        var productOutputDto = BookConverterUtil
                .bootToBookOutputDto(product);

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(productOutputDto);
    }

    @Test
    void productInputDtoToBookTest() {
        var bookInput = new BookInputDTO(
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);
        var book = BookConverterUtil
                .bookInputDtoToBook(bookInput);

        Assertions.assertThat(bookInput)
                .usingRecursiveComparison()
                .isEqualTo(book);
    }
}