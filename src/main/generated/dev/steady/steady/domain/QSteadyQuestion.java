package dev.steady.steady.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSteadyQuestion is a Querydsl query type for SteadyQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSteadyQuestion extends EntityPathBase<SteadyQuestion> {

    private static final long serialVersionUID = 577741513L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSteadyQuestion steadyQuestion = new QSteadyQuestion("steadyQuestion");

    public final dev.steady.global.entity.QBaseEntity _super = new dev.steady.global.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final QSteady steady;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSteadyQuestion(String variable) {
        this(SteadyQuestion.class, forVariable(variable), INITS);
    }

    public QSteadyQuestion(Path<? extends SteadyQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSteadyQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSteadyQuestion(PathMetadata metadata, PathInits inits) {
        this(SteadyQuestion.class, metadata, inits);
    }

    public QSteadyQuestion(Class<? extends SteadyQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.steady = inits.isInitialized("steady") ? new QSteady(forProperty("steady"), inits.get("steady")) : null;
    }

}

