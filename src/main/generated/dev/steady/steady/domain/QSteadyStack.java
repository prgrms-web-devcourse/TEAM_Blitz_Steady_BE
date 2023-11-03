package dev.steady.steady.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSteadyStack is a Querydsl query type for SteadyStack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSteadyStack extends EntityPathBase<SteadyStack> {

    private static final long serialVersionUID = -1364033915L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSteadyStack steadyStack = new QSteadyStack("steadyStack");

    public final dev.steady.global.entity.QBaseEntity _super = new dev.steady.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final dev.steady.user.domain.QStack stack;

    public final QSteady steady;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSteadyStack(String variable) {
        this(SteadyStack.class, forVariable(variable), INITS);
    }

    public QSteadyStack(Path<? extends SteadyStack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSteadyStack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSteadyStack(PathMetadata metadata, PathInits inits) {
        this(SteadyStack.class, metadata, inits);
    }

    public QSteadyStack(Class<? extends SteadyStack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stack = inits.isInitialized("stack") ? new dev.steady.user.domain.QStack(forProperty("stack")) : null;
        this.steady = inits.isInitialized("steady") ? new QSteady(forProperty("steady"), inits.get("steady")) : null;
    }

}

