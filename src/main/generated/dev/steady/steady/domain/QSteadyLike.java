package dev.steady.steady.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSteadyLike is a Querydsl query type for SteadyLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSteadyLike extends EntityPathBase<SteadyLike> {

    private static final long serialVersionUID = -598409222L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSteadyLike steadyLike = new QSteadyLike("steadyLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSteady steady;

    public final dev.steady.user.domain.QUser user;

    public QSteadyLike(String variable) {
        this(SteadyLike.class, forVariable(variable), INITS);
    }

    public QSteadyLike(Path<? extends SteadyLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSteadyLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSteadyLike(PathMetadata metadata, PathInits inits) {
        this(SteadyLike.class, metadata, inits);
    }

    public QSteadyLike(Class<? extends SteadyLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.steady = inits.isInitialized("steady") ? new QSteady(forProperty("steady"), inits.get("steady")) : null;
        this.user = inits.isInitialized("user") ? new dev.steady.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

