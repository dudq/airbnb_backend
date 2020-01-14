package com.airbnb.services;

import com.airbnb.messages.response.CommentsOfHouse;
import com.airbnb.models.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<CommentsOfHouse> findAllByHouseId(Long houseId);

    void createComment(Comment comment);
}
