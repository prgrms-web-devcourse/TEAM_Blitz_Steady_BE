package dev.steady.steady.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSteadyPosition is a Querydsl query type for SteadyPosition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSteadyPosition extends EntityPathBase<SteadyPosition> {

    private static final long serialVersionUID = -1803550708L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSteadyPosition steadyPosition = new QSteadyPosition("steadyPosition");

    public final dev.steady.global.entity.QBaseEntity _super = new dev.steady.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final dev.steady.user.domain.QPosition position;

    public final QSteady steady;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSteadyPosition(String variable) {
        this(SteadyPosition.class, forVariable(variable), INITS);
    }

    public QSteadyPosition(Path<? extends SteadyPosition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSteadyPosition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSteadyPosition(PathMetadata metadata, PathInits inits) {
        this(SteadyPosition.class, metadata, inits);
    }

    public QSteadyPosition(Class<? extends SteadyPosition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.position = inits.isInitialized("position") ? new dev.steady.user.domain.QPosition(forProperty("position")) : null;
        this.steady = inits.isInitialized("steady") ? new QSteady(forProperty("steady"), inits.get("steady")) : null;
    }

}

