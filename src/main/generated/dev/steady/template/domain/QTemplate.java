package dev.steady.template.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTemplate is a Querydsl query type for Template
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemplate extends EntityPathBase<Template> {

    private static final long serialVersionUID = 1038852499L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTemplate template = new QTemplate("template");

    public final dev.steady.global.entity.QBaseEntity _super = new dev.steady.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuestions questions;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final dev.steady.user.domain.QUser user;

    public QTemplate(String variable) {
        this(Template.class, forVariable(variable), INITS);
    }

    public QTemplate(Path<? extends Template> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTemplate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTemplate(PathMetadata metadata, PathInits inits) {
        this(Template.class, metadata, inits);
    }

    public QTemplate(Class<? extends Template> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.questions = inits.isInitialized("questions") ? new QQuestions(forProperty("questions")) : null;
        this.user = inits.isInitialized("user") ? new dev.steady.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

