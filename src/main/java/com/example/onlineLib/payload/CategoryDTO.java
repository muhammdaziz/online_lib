package com.example.onlineLib.payload;

import com.example.onlineLib.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CategoryDTO {

    private Integer id;

    private String name;

    private Integer parentId;
}
