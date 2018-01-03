package com.github.todo.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Ikram
 * Date: 10/29/17
 * Time: 3:16 AM
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
@Value /* to generate equals, hashCode, getters & toString ( immutable ) */
@JsonDeserialize(builder = ToDo.Builder.class)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@Entity
public class ToDo {
    @Id
    private final String id;

    @NotNull
    @NotEmpty
    private final String title;

    private final boolean completed ;
    private final Date createdAt;


    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private ToDo(String id, @NonNull String title, boolean completed, Date createdAt)
    {
        this.id = generateString(id);
        this.title = title;
        this.completed = completed;
        this.createdAt = new Date();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder
    {
    }

    public static String generateString(String id) {

        String returnString = id;
        if(returnString.equals(null)) {
           returnString = UUID.randomUUID().toString();
        }
        return returnString;
    }

}