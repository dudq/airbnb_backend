package com.airbnb.repositories;

import com.airbnb.messages.response.CommentsOfHouse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class CommentDao {
    @PersistenceContext
    private EntityManager em;

    public List<CommentsOfHouse> getCommentsOfHouse(Long houseId) {
        String sql = "select c.id, c.comment, c.house, c.user, h.houseName houseName, u.name userName " +
                "from comment c join house h on h.id = c.house " +
                "join users u on u.id = c.user " +
                "where h.id = :houseId";
        Query query = em.createNativeQuery(sql);
        query.setParameter("houseId", houseId);

        List<Object[]> listResult = query.getResultList();

        List<CommentsOfHouse> commentsOfHouses = new ArrayList<>();

        CommentsOfHouse comment;
        int index;

        for (Object[] result : listResult) {
            index = 0;

            comment = new CommentsOfHouse();
            comment.setId(Long.parseLong(result[index++].toString()));
            comment.setComment(result[index++].toString());
            comment.setHouseId(Long.parseLong(result[index++].toString()));
            comment.setUserId(Long.parseLong(result[index++].toString()));
            comment.setHouseName(result[index++].toString());
            comment.setUserName(result[index].toString());
            commentsOfHouses.add(comment);
        }

        return commentsOfHouses;
    }
}
