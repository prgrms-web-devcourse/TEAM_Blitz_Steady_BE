package dev.steady.steady.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipants is a Querydsl query type for Participants
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QParticipants extends BeanPath<Participants> {

    private static final long serialVersionUID = 223169681L;

    public static final QParticipants participants = new QParticipants("participants");

    public final ListPath<Participant, QParticipant> steadyParticipants = this.<Participant, QParticipant>createList("steadyParticipants", Participant.class, QParticipant.class, PathInits.DIRECT2);

    public QParticipants(String variable) {
        super(Participants.class, forVariable(variable));
    }

    public QParticipants(Path<? extends Participants> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipants(PathMetadata metadata) {
        super(Participants.class, metadata);
    }

}

