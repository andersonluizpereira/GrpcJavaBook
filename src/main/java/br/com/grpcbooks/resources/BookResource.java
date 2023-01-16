package br.com.grpcbooks.resources;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import br.com.grpcbooks.*;
import br.com.grpcbooks.dto.BookInputDTO;
import br.com.grpcbooks.dto.BookOutputDTO;
import br.com.grpcbooks.service.IBookService;

import java.util.List;
import java.util.stream.Collectors;


@GrpcService
public class BookResource extends BookServiceGrpc.BookServiceImplBase {

    private final IBookService productService;

    public BookResource(IBookService productService) {
        this.productService = productService;
    }

    @Override
    public void create(BookRequest request, StreamObserver<BookResponse> responseObserver) {
        BookInputDTO inputDTO = new BookInputDTO(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getDescription(),
                request.getPrice(),
                request.getQuantityInStock());

        BookOutputDTO outputDTO = this.productService.create(inputDTO);

        BookResponse response = BookResponse.newBuilder()
                .setId(outputDTO.getId())
                .setTitle(outputDTO.getTitle())
                .setAuthor(outputDTO.getAuthor())
                .setIsbn(outputDTO.getIsbn())
                .setDescription(outputDTO.getDescription())
                .setPrice(outputDTO.getPrice())
                .setQuantityInStock(outputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(RequestById request, StreamObserver<BookResponse> responseObserver) {
        BookOutputDTO outputDTO = productService.findById(request.getId());
        BookResponse response = BookResponse.newBuilder()
                .setId(outputDTO.getId())
                .setTitle(outputDTO.getTitle())
                .setAuthor(outputDTO.getAuthor())
                .setIsbn(outputDTO.getIsbn())
                .setDescription(outputDTO.getDescription())
                .setPrice(outputDTO.getPrice())
                .setQuantityInStock(outputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(RequestById request, StreamObserver<EmptyResponse> responseObserver) {
        productService.delete(request.getId());
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(EmptyRequest request, StreamObserver<BookResponseList> responseObserver) {
        List<BookOutputDTO> outputDTOList = productService.findAll();
        List<BookResponse> productResponseList = outputDTOList.stream()
                .map(outputDTO ->
                        BookResponse.newBuilder()
                                .setId(outputDTO.getId())
                                .setTitle(outputDTO.getTitle())
                                .setAuthor(outputDTO.getAuthor())
                                .setIsbn(outputDTO.getIsbn())
                                .setDescription(outputDTO.getDescription())
                                .setPrice(outputDTO.getPrice())
                                .setQuantityInStock(outputDTO.getQuantityInStock())
                                .build())
                .collect(Collectors.toList());

        BookResponseList response = BookResponseList.newBuilder()
                .addAllBooks(productResponseList)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

