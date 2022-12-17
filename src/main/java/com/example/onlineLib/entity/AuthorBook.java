package com.example.onlineLib.entity;

import com.example.onlineLib.entity.template.AbsAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE book SET deleted=true WHERE id=?")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"book_id"}))
public class AuthorBook extends AbsAuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    private Book book;

    @OneToOne(optional = false)
    private FileImg bgImg;

    @Column(nullable = false)
    private String text;

    private String fontFamily;

    private String fontSize;
}
