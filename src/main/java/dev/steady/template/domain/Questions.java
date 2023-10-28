package dev.steady.template.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Questions {

    @Default
    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public Questions(Template template, List<String> questions) {
        this.questions = questions.stream()
                .map(question -> new Question(template, question))
                .toList();
    }

    public List<String> getContents() {
        return questions.stream()
                .map(Question::getContent)
                .toList();
    }

}
