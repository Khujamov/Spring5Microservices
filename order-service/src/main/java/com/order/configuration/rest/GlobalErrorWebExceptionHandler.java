package com.order.configuration.rest;

import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Global exception handler to manage unhandler errors in the Rest layer (Controllers)
 */
@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof NullPointerException) {
            return nullPointerException(exchange, (NullPointerException) ex);
        }
        else if (ex instanceof DataAccessException) {
            return dataAccessException(exchange, (DataAccessException) ex);
        }
        else {
            return throwable(exchange, ex);
        }
    }


    /**
     * Method used to manage when a Rest request throws a {@link NullPointerException}
     *
     * @param exchange
     *    {@link ServerWebExchange} with the request information
     * @param exception
     *    {@link NullPointerException} thrown
     *
     * @return {@link Mono} with the suitable response
     */
    private Mono<Void> nullPointerException(ServerWebExchange exchange, NullPointerException exception) {
        LOGGER.error("There was a NullPointerException. " + getErrorMessageUsingHttpRequest(exchange), exception);
        return buildPlainTestResponse("Trying to access to a non existing property", exchange,
                                      HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Method used to manage when a Rest request throws a {@link NullPointerException}
     *
     * @param exchange
     *    {@link ServerWebExchange} with the request information
     * @param exception
     *    {@link DataAccessException} thrown
     *
     * @return {@link Mono} with the suitable response
     */
    private Mono<Void> dataAccessException(ServerWebExchange exchange, DataAccessException exception) {
        LOGGER.error("There was an DataAccessException. " + getErrorMessageUsingHttpRequest(exchange), exception);
        return buildPlainTestResponse("Error trying to get/send information from/to database", exchange,
                                      HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Method used to manage when a Rest request throws a {@link Throwable}
     *
     * @param exchange
     *    {@link ServerWebExchange} with the request information
     * @param exception
     *    {@link Throwable} thrown
     *
     * @return {@link Mono} with the suitable response
     */
    private Mono<Void> throwable(ServerWebExchange exchange, Throwable exception) {
        LOGGER.error(getErrorMessageUsingHttpRequest(exchange), exception);
        return buildPlainTestResponse("Internal error in the application", exchange,
                                      HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Using the given {@link ServerWebExchange} builds a message with information about the Http request
     *
     * @param exchange
     *    {@link ServerWebExchange} with the request information
     *
     * @return error message with Http request information
     */
    private String getErrorMessageUsingHttpRequest(ServerWebExchange exchange) {
        return String.format("There was an error trying to execute the request with:%s" +
                             "Http method = %s %s" +
                             "Uri = %s %s" +
                             "Header = %s",
                             System.lineSeparator(),
                             exchange.getRequest().getMethod(), System.lineSeparator(),
                             exchange.getRequest().getURI().toString(), System.lineSeparator(),
                             exchange.getRequest().getHeaders().entrySet());
    }


    /**
     *    Builds the Http response with the given information, including {@link HttpStatus} and a custom message
     * to include iun the content.
     *
     * @param responseMessage
     * @param exchange
     *    {@link ServerWebExchange} with the request information
     * @param httpStatus
     *    {@link HttpStatus} used in the Http response
     *
     * @return {@link Mono} with the suitable Http response
     */
    private Mono<Void> buildPlainTestResponse(String responseMessage, ServerWebExchange exchange, HttpStatus httpStatus) {

        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);

        byte[] responseMessageBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
        DataBuffer bufferResponseMessage = exchange.getResponse().bufferFactory().wrap(responseMessageBytes);
        return exchange.getResponse().writeWith(Mono.just(bufferResponseMessage));
    }

}