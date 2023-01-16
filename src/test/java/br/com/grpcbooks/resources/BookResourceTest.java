package br.com.grpcbooks.resources;

import br.com.grpcbooks.*;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
public class BookResourceTest {

    @GrpcClient("inProcess")
    private BookServiceGrpc.BookServiceBlockingStub serviceBlockingStub;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("when valid data is provided a product is created")
    public void createBookSuccessTest() {
        BookRequest productRequest = BookRequest.newBuilder()
                .setTitle("Clean Code")
                .setAuthor("Robert C. Martin")
                .setIsbn("978-0132350884")
                .setDescription("description")
                .setPrice(10.99)
                .setQuantityInStock(100).build();

        BookResponse productResponse = serviceBlockingStub.create(productRequest);

        assertThat(productRequest)
                .usingRecursiveComparison()
                .comparingOnlyFields("title", "author", "isbn", "description", "price", "quantity_in_stock")
                .isEqualTo(productResponse);
    }

    @Test
    @DisplayName("when create is called with duplicated name, throw BookAlreadyExistsException")
    public void createBookAlreadyExistsExceptionTest() {
        BookRequest productRequest = BookRequest.newBuilder()
                .setTitle("Clean Code")
                .setAuthor("Robert C. Martin")
                .setIsbn("978-0132350884")
                .setDescription("description")
                .setPrice(10.00)
                .setQuantityInStock(100)
                .build();

        assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.create(productRequest))
                .withMessage("ALREADY_EXISTS: Livro Clean Code já cadastrado no sistema.");

    }

    @Test
    @DisplayName("when findById method is call with valid id a product is returned")
    public void findByIdSuccessTest() {
        RequestById request = RequestById.newBuilder().setId(1L).build();

        BookResponse productResponse = serviceBlockingStub.findById(request);

        assertThat(productResponse.getId()).isEqualTo(request.getId());
        assertThat(productResponse.getTitle()).isEqualTo("Clean Code");
    }

    @Test
    @DisplayName("when findById is call with invalid throw BookNotFoundException")
    public void findByIdExceptionTest() {
        RequestById request = RequestById.newBuilder().setId(100L).build();

        assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.findById(request))
                .withMessage("NOT_FOUND: Livro com ID 100 não encontrado.");

    }

    @Test
    @DisplayName("when delete is call with id should does not throw")
    public void deleteSuccessTest() {
        RequestById request = RequestById.newBuilder().setId(1L).build();

        assertThatNoException().isThrownBy(() -> serviceBlockingStub.delete(request));
    }

    @Test
    @DisplayName("when delete is call with invalid id throws BookNotFoundException")
    public void deleteExceptionTest() {
        RequestById request = RequestById.newBuilder().setId(100L).build();

        assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.delete(request))
                .withMessage("NOT_FOUND: Livro com ID 100 não encontrado.");
    }

    @Test
    @DisplayName("when findAll method is call a product list is returned")
    public void findAllSuccessTest() {
        EmptyRequest request = EmptyRequest.newBuilder().build();

        BookResponseList responseList = serviceBlockingStub.findAll(request);

        assertThat(responseList).isInstanceOf(BookResponseList.class);
        assertThat(responseList.getBooksCount()).isEqualTo(2);
        assertThat(responseList.getBooksList())
                .extracting("id", "name", "price", "quantityInStock")
                .contains(
                        tuple(1L, "Book A", 10.99, 10),
                        tuple(2L, "Book B", 10.99, 10)
                );
    }
}
