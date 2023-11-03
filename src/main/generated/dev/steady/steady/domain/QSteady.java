package dev.steady.steady.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSteady is a Querydsl query type for Steady
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSteady extends EntityPathBase<Steady> {

    private static final long serialVersionUID = 1412920771L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSteady steady = new QSteady("steady");

    public final dev.steady.global.entity.QBaseEntity _super = new dev.steady.global.entity.QBaseEntity(this);

    public final StringPath bio = createString("bio");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> deadline = createDate("deadline", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> numberOfParticipants = createNumber("numberOfParticipants", Integer.class);

    public final NumberPath<Integer> participantLimit = createNumber("participantLimit", Integer.class);

    public final QParticipants participants;

    public final QPromotion promotion;

    public final EnumPath<ScheduledPeriod> scheduledPeriod = createEnum("scheduledPeriod", ScheduledPeriod.class);

    public final EnumPath<SteadyStatus> status = createEnum("status", SteadyStatus.class);

    public final EnumPath<SteadyMode> steadyMode = createEnum("steadyMode", SteadyMode.class);

    public final ListPath<SteadyStack, QSteadyStack> steadyStacks = this.<SteadyStack, QSteadyStack>createList("steadyStacks", SteadyStack.class, QSteadyStack.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final EnumPath<SteadyType> type = createEnum("type", SteadyType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSteady(String variable) {
        this(Steady.class, forVariable(variable), INITS);
    }

    public QSteady(Path<? extends Steady> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSteady(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSteady(PathMetadata metadata, PathInits inits) {
        this(Steady.class, metadata, inits);
    }

    public QSteady(Class<? extends Steady> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.participants = inits.isInitialized("participants") ? new QParticipants(forProperty("participants")) : null;
        this.promotion = inits.isInitialized("promotion") ? new QPromotion(forProperty("promotion")) : null;
    }

}

