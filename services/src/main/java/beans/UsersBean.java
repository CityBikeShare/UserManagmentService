package beans;

import core.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class UsersBean {

    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    public List<Users> getUsers() {
        TypedQuery<Users> query = entityManager.createNamedQuery("Users.getAll", Users.class);
        return query.getResultList();
    }

    public Users getUserById(int bikeId) {
        return entityManager.find(Users.class, bikeId);
    }

}
