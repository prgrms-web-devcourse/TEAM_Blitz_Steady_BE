package dev.steady.template.domain;

import dev.steady.global.entity.BaseEntity;
import dev.steady.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "templates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Embedded
    private Questions questions;

    private Template(User user, String title, List<String> questions) {
        this.user = user;
        this.title = title;
        this.questions = new Questions(this, questions);
    }

    public static Template create(User user, String title, List<String> questions) {
        return new Template(user, title, questions);
    }

    public void validateOwner(User user) {
        if (!this.user.equals(user)) {
            throw new IllegalArgumentException();
        }
    }

    public List<String> getContents() {
        return questions.getContents();
    }

}
