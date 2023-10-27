package dev.steady.user.domain;

import dev.steady.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "stacks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    private Stack(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

}
