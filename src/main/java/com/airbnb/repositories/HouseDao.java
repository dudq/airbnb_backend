package com.airbnb.repositories;

import com.airbnb.messages.response.HouseDetail;
import com.airbnb.messages.response.HouseInformation;
import com.airbnb.models.House;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

        int index = 0;
        HouseDetail houseDetail = new HouseDetail();
        houseDetail.setId(Long.parseLong("" + result[index++]));
        houseDetail.setName(result[index++].toString());
        houseDetail.setAddress(result[index++].toString());
        houseDetail.setBedroomNumber(result[index++].toString());
        houseDetail.setBathroomNumber(result[index++].toString());
        houseDetail.setDescription(result[index++].toString());
        houseDetail.setPrice(result[index++].toString());
        houseDetail.setArea(result[index++].toString());
        houseDetail.setStatus(result[index++].toString());
        houseDetail.setUserName(result[index++].toString());
        houseDetail.setUserId(result[index].toString());

        return houseDetail;
    }

    public List<HouseInformation> getListHouse(int page, int pageSize) {
        String sql = "select h.id, h.houseName, h.address, h.price, h.picture " +
                "from house h " +
                "left join users u " +
                "on h.host_id = u.id;";
        Query query = em.createNativeQuery(sql);
        List<Object[]> listResult = query.getResultList();

        List<HouseInformation> houseInformation = new ArrayList<>();
        HouseInformation house;
        int index;
        for (Object[] result : listResult) {
            index = 0;
            house = new HouseInformation(;
        }
    }
}
