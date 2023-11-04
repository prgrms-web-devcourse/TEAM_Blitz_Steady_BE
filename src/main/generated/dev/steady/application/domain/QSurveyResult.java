package dev.steady.application.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSurveyResult is a Querydsl query type for SurveyResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSurveyResult extends EntityPathBase<SurveyResult> {

    private static final long serialVersionUID = 2084663712L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSurveyResult surveyResult = new QSurveyResult("surveyResult");

    public final StringPath answer = createString("answer");

    public final QApplication application;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath question = createString("question");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public QSurveyResult(String variable) {
        this(SurveyResult.class, forVariable(variable), INITS);
    }

    public QSurveyResult(Path<? extends SurveyResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSurveyResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSurveyResult(PathMetadata metadata, PathInits inits) {
        this(SurveyResult.class, metadata, inits);
    }

    public QSurveyResult(Class<? extends SurveyResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.application = inits.isInitialized("application") ? new QApplication(forProperty("application"), inits.get("application")) : null;
    }

}

