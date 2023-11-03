package dev.steady.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSticker is a Querydsl query type for Sticker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSticker extends EntityPathBase<Sticker> {

    private static final long serialVersionUID = -297677402L;

    public static final QSticker sticker = new QSticker("sticker");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QSticker(String variable) {
        super(Sticker.class, forVariable(variable));
    }

    public QSticker(Path<? extends Sticker> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSticker(PathMetadata metadata) {
        super(Sticker.class, metadata);
    }

}

