package dev.steady.template.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Questions {

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> values = new ArrayList<>();

    public Questions(Template template, List<String> questions) {
        List<Question> values = createQuestions(template, questions);
        this.values = values;
    }

    public void update(List<String> questions, Template template) {
        this.values.clear();
        List<Question> values = createQuestions(template, questions);
        this.values.addAll(values);
    }

    private List<Question> createQuestions(Template template, List<String> questions) {
        return questions.stream()
                .map(question -> new Question(template, question))
                .toList();
    }

    public List<String> getContents() {
        return values.stream()
                .map(Question::getContent)
                .toList();
    }

}
