package com.airbnb.repositories;

import com.airbnb.models.House;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class HouseDao {
    @PersistenceContext
    private EntityManager em;

    public void insert(House house) {
        em.persist(house);
    }

//    public House getHouseDetailById(Long houseId) {
//        String sql = "select h.id, h.houseName, h.picture, h.address, h.bedroomNumber, " +
//                "h.bathroomNumber, h.description, h.price, h.area, h.status," +
//                " u.name userName, u.id userId" +
//                " from house h" +
//                " LEFT JOIN users u" +
//                " ON h.host_id = u.id" +
//                " where h.id = :houseId";
//        Query query = em.createNativeQuery(sql);
//        query.setParameter("houseId", houseId);
//        Object[] result = (Object[]) query.getSingleResult();
//
//        int i = 0;
//    }
}
