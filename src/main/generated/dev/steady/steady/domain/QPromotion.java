package dev.steady.steady.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromotion is a Querydsl query type for Promotion
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPromotion extends BeanPath<Promotion> {

    private static final long serialVersionUID = -76270254L;

    public static final QPromotion promotion = new QPromotion("promotion");

    public final DateTimePath<java.time.LocalDateTime> promotedAt = createDateTime("promotedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> promotionCount = createNumber("promotionCount", Integer.class);

    public QPromotion(String variable) {
        super(Promotion.class, forVariable(variable));
    }

    public QPromotion(Path<? extends Promotion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotion(PathMetadata metadata) {
        super(Promotion.class, metadata);
    }

}

