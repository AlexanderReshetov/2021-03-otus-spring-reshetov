package ru.otus.main.service.exception;

public class AuctionNotExistsException extends RuntimeException {
    public AuctionNotExistsException(String message) {
        super(message);
    }
}
