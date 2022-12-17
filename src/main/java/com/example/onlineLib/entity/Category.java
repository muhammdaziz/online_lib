package com.example.onlineLib.entity;

import com.example.onlineLib.entity.template.AbsAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE book SET deleted=true WHERE id=?")
public class Category extends AbsAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category category;
}
