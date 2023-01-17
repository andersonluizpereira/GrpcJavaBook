package br.com.grpcbooks.service.impl;

import br.com.grpcbooks.dto.BookInputDTO;
import br.com.grpcbooks.dto.BookOutputDTO;
import br.com.grpcbooks.exception.BookAlreadyExistsException;
import br.com.grpcbooks.exception.BookNotFoundException;
import br.com.grpcbooks.repository.BookRepository;
import br.com.grpcbooks.service.IBookService;
import br.com.grpcbooks.util.BookConverterUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    private final BookRepository productRepository;

    public BookServiceImpl(BookRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BookOutputDTO create(BookInputDTO inputDTO) {
        checkDuplicity(inputDTO.getTitle());
        var product = BookConverterUtil.bookInputDtoToBook(inputDTO);
        var productCreated = this.productRepository.save(product);
        return BookConverterUtil.bootToBookOutputDto(productCreated);
    }

    @Override
    public BookOutputDTO findById(Long id) {
        var product = this.productRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return BookConverterUtil.bootToBookOutputDto(product);
    }

    @Override
    public void delete(Long id) {
        var product = this.productRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        this.productRepository.delete(product);
    }

    @Override
    public List<BookOutputDTO> findAll() {
        var products = this.productRepository.findAll();
        return products.stream()
                .map(BookConverterUtil::bootToBookOutputDto)
                .collect(Collectors.toList());
    }

    private void checkDuplicity(String title) {
        this.productRepository.findByTitleIgnoreCase(title)
                .ifPresent(e -> {
                    throw new BookAlreadyExistsException(title);
                });
    }
}
