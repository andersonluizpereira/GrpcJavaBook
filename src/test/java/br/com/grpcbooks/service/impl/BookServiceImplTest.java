package br.com.grpcbooks.service.impl;

import br.com.grpcbooks.domain.Book;
import br.com.grpcbooks.dto.BookInputDTO;
import br.com.grpcbooks.dto.BookOutputDTO;
import br.com.grpcbooks.exception.BookAlreadyExistsException;
import br.com.grpcbooks.exception.BookNotFoundException;
import br.com.grpcbooks.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("when create book service is call with valid data a book is returned")
    public void createBookSuccessTest() {
        Book book = new Book(1L,
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);

        when(bookRepository.save(any())).thenReturn(book);

        BookInputDTO inputDTO = new BookInputDTO(
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);
        BookOutputDTO outputDTO = bookService.create(inputDTO);

        assertThat(outputDTO)
                .usingRecursiveComparison()
                .isEqualTo(book);
    }

    @Test
    @DisplayName("when create book service is call with duplicated name, throw BookAlreadyExistsException")
    public void createBookExceptionTest() {
        Book book = new Book(1L,
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);

        when(bookRepository.findByTitleIgnoreCase(any())).thenReturn(Optional.of(book));

        BookInputDTO inputDTO = new BookInputDTO(
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);

        assertThatExceptionOfType(BookAlreadyExistsException.class)
                .isThrownBy(() -> bookService.create(inputDTO));
    }

    @Test
    @DisplayName("when findById book is call with valid id a book is returned")
    public void findByIdSuccessTest() {
        Long id = 1L;
        Book book = new Book(1L,
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        BookOutputDTO outputDTO = bookService.findById(id);

        assertThat(outputDTO)
                .usingRecursiveComparison()
                .isEqualTo(book);
    }

    @Test
    @DisplayName("when findById book is call with invalid id throws BookNotFoundException")
    public void findByIdExceptionTest() {
        Long id = 1L;

        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> bookService.findById(id));
    }

    @Test
    @DisplayName("when delete book is call with id should does not throw")
    public void deleteSuccessTest() {
        Long id = 1L;
        Book book = new Book(1L,
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                "description",
                10.0,
                10);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        assertThatNoException().isThrownBy(() -> bookService.findById(id));
    }

    @Test
    @DisplayName("when delete book is call with invalid id throws BookNotFoundException")
    public void deleteExceptionTest() {
        Long id = 1L;

        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> bookService.delete(id));
    }

    @Test
    @DisplayName("when findAll book is call a list of book is returned")
    public void findAllSuccessTest() {
        List<Book> books = List.of(
                new Book(1L,
                        "Clean Code",
                        "Robert C. Martin",
                        "978-0132350884",
                        "description",
                        10.0,
                        10),
                new Book(2L,
                        "Domain-Driven Design",
                        "Eric Evans",
                        "978-0321125217",
                        "description",
                        10.0,
                        10));

        when(bookRepository.findAll()).thenReturn(books);

        List<BookOutputDTO> outputDTOS = bookService.findAll();

        assertThat(outputDTOS)
                .extracting("id", "title", "author", "isbn", "description" ,"price", "quantityInStock")
                .contains(
                        tuple(1L,
                                "Clean Code",
                                "Robert C. Martin",
                                "978-0132350884",
                                "description",
                                10.0,
                                10),
                        tuple(2L,
                                "Domain-Driven Design",
                                "Eric Evans",
                                "978-0321125217",
                                "description",
                                10.0,
                                10)
                );
    }
}
