package com.airbnb.services.impl;

import com.airbnb.messages.response.CommentsOfHouse;
import com.airbnb.models.Comment;
import com.airbnb.repositories.CommentDao;
import com.airbnb.repositories.CommentRepository;
import com.airbnb.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<CommentsOfHouse> findAllByHouseId(Long houseId) {
        return commentDao.getCommentsOfHouse(houseId);
    }

    @Override
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }
}
