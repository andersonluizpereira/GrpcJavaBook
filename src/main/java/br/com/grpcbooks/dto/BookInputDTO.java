package br.com.grpcbooks.dto;

public class BookInputDTO {
    private String title;
    private String author;
    private String isbn;
    private String description;
    private Double price;
    private Integer quantityInStock;

    public BookInputDTO() {
    }

    public BookInputDTO(String title, String author, String isbn, String description, Double price, Integer quantityInStock) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }
}
