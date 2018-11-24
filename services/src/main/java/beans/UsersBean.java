package beans;

import core.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UsersBean {

    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    public List<Users> getUsers() {
        TypedQuery<Users> query = entityManager.createNamedQuery("Users.getAll", Users.class);
        return query.getResultList();
    }

    public Users getUserById(int userId) {
        return entityManager.find(Users.class, userId);
    }

    public Users getUserByUsername(String username) {
        TypedQuery<Users> query = entityManager.createNamedQuery("Users.getByUsername", Users.class);
        query.setParameter("username", username);

        try {
            return query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    @Transactional
    public Users insertUser(Users user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }
}
