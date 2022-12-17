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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author_id"}))
public class BookFile extends AbsAuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false)
    private Author author;

    @Column(nullable = false)
    private String language;

    private String context;

    @Column(nullable = false)
    private Float price;

    @OneToOne(optional = false)
    private FileImg img;

    @OneToOne(optional = false)
    private FileImg path;

    @ManyToOne(optional = false)
    private Category category;
}
