package com.airbnb.repositories;

import com.airbnb.messages.response.HouseDetail;
import com.airbnb.messages.response.HouseList;
import com.airbnb.models.House;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class HouseDao {
    @PersistenceContext
    private EntityManager em;

    public void insert(House house) {
        em.persist(house);
    }

    public HouseDetail getHouseDetailById(Long houseId) {
        String sql = "select h.id, h.houseNam, h.address, h.bedroomNumber, " +
                "h.bathroomNumber, h.description, h.price, h.area, h.status," +
                " u.name userName, u.id userId" +
                " from house h" +
                " LEFT JOIN users u" +
                " ON h.host_id = u.id" +
                " where h.id = :houseId";
        Query query = em.createNativeQuery(sql);
        query.setParameter("houseId", houseId);
        Object[] result = (Object[]) query.getSingleResult();

        int i = 0;
        HouseDetail houseDetail = new HouseDetail();
        houseDetail.setId(Long.parseLong("" + result[i++]));
        houseDetail.setName(result[i++].toString());
        houseDetail.setAddress(result[i++].toString());
        houseDetail.setBedroomNumber(result[i++].toString());
        houseDetail.setBathroomNumber(result[i++].toString());
        houseDetail.setDescription(result[i++].toString());
        houseDetail.setPrice(result[i++].toString());
        houseDetail.setArea(result[i++].toString());
        houseDetail.setStatus(result[i++].toString());
        houseDetail.setUserName(result[i++].toString());
        houseDetail.setUserId(result[i++].toString());

        return houseDetail;
    }

    public List<HouseList> getListHouse(int page, int pageSize) {

    }
}
