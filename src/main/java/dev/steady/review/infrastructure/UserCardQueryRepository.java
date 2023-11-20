package dev.steady.review.infrastructure;

import dev.steady.review.dto.response.UserCardResponse;
import dev.steady.user.domain.User;

import java.util.List;

public interface UserCardQueryRepository {

    List<UserCardResponse> getCardCountByUser(User user);

}
