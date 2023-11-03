package dev.steady.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewSticker is a Querydsl query type for ReviewSticker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewSticker extends EntityPathBase<ReviewSticker> {

    private static final long serialVersionUID = 1139427438L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewSticker reviewSticker = new QReviewSticker("reviewSticker");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public final QSticker sticker;

    public QReviewSticker(String variable) {
        this(ReviewSticker.class, forVariable(variable), INITS);
    }

    public QReviewSticker(Path<? extends ReviewSticker> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewSticker(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewSticker(PathMetadata metadata, PathInits inits) {
        this(ReviewSticker.class, metadata, inits);
    }

    public QReviewSticker(Class<? extends ReviewSticker> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
        this.sticker = inits.isInitialized("sticker") ? new QSticker(forProperty("sticker")) : null;
    }

}

