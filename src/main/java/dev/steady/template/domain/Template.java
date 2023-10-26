package dev.steady.template.domain;

import dev.steady.global.entity.BaseEntity;
import dev.steady.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
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

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    private Template(User user, String title, List<String> questions) {
        List<Question> list = questions.stream()
                .map(question -> new Question(this, question))
                .toList();
        this.user = user;
        this.title = title;
        this.questions = list;
    }

    public static Template create(User user, String title, List<String> questions) {
        return new Template(user, title, questions);
    }

}
