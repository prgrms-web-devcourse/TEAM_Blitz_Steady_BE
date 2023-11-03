package dev.steady.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserStack is a Querydsl query type for UserStack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserStack extends EntityPathBase<UserStack> {

    private static final long serialVersionUID = 4202195L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserStack userStack = new QUserStack("userStack");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStack stack;

    public final QUser user;

    public QUserStack(String variable) {
        this(UserStack.class, forVariable(variable), INITS);
    }

    public QUserStack(Path<? extends UserStack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserStack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserStack(PathMetadata metadata, PathInits inits) {
        this(UserStack.class, metadata, inits);
    }

    public QUserStack(Class<? extends UserStack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stack = inits.isInitialized("stack") ? new QStack(forProperty("stack")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

