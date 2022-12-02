package com.example.onlineLib.entity;

import com.example.onlineLib.entity.template.AbsAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author"}))
public class Book extends AbsAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String author;

    @Column(nullable = false)
    String language;

    String context;

    @Column(nullable = false)
    Float price;

    String img;

    @Column(nullable = false)
    String path;
}
