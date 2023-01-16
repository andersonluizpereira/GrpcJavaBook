package br.com.grpcbooks.service;

import br.com.grpcbooks.dto.BookInputDTO;
import br.com.grpcbooks.dto.BookOutputDTO;

import java.util.List;

public interface IBookService {
    BookOutputDTO create(BookInputDTO inputDTO);
    BookOutputDTO findById(Long id);
    void delete(Long id);
    List<BookOutputDTO> findAll();
}
