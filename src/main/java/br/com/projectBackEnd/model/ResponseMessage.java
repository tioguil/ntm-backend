package br.com.projectBackEnd.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Component
public class ResponseMessage {
    private String statusCode;
    private String message;
    private Object response;
}
