package dev.steady.application.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSurveyResults is a Querydsl query type for SurveyResults
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSurveyResults extends BeanPath<SurveyResults> {

    private static final long serialVersionUID = 200065747L;

    public static final QSurveyResults surveyResults = new QSurveyResults("surveyResults");

    public final ListPath<SurveyResult, QSurveyResult> values = this.<SurveyResult, QSurveyResult>createList("values", SurveyResult.class, QSurveyResult.class, PathInits.DIRECT2);

    public QSurveyResults(String variable) {
        super(SurveyResults.class, forVariable(variable));
    }

    public QSurveyResults(Path<? extends SurveyResults> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSurveyResults(PathMetadata metadata) {
        super(SurveyResults.class, metadata);
    }

}

