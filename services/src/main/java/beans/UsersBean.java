package beans;

import core.Users;
import org.eclipse.microprofile.metrics.annotation.Metered;

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

    @Metered(name = "getUsers")
    public List<Users> getUsers() {
        TypedQuery<Users> query = entityManager.createNamedQuery("Users.getAll", Users.class);
        return query.getResultList();
    }

    @Metered(name = "getUserById")
    public Users getUserById(int userId) {
        return entityManager.find(Users.class, userId);
    }

    @Metered(name = "getUserByUsername")
    public Users getUserByUsername(String username) {
        TypedQuery<Users> query = entityManager.createNamedQuery("Users.getByUsername", Users.class);
        query.setParameter("username", username);

        try {
            return query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    @Metered(name = "getUsersByRegion")
    public List<Users> getUsersByRegion(String region){
        TypedQuery<Users> query = entityManager.createNamedQuery("Users.getByRegion", Users.class);
        query.setParameter("region", region);

        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    @Metered(name = "insertUser")
    @Transactional
    public Users insertUser(Users user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    @Metered(name = "updateUser")
    @Transactional
    public Users updateUser(int userId, Users user) {
        try {
            Users tempUser = entityManager.find(Users.class, userId);
            user.setUser_id(tempUser.getUser_id());
            user = entityManager.merge(user);
        } catch(Exception e) {
            return null;
        }
        return user;
    }

    @Metered(name = "deleteUser")
    @Transactional
    public boolean deleteUser(int userId) {
        try {
            Users user = entityManager.find(Users.class, userId);
            entityManager.remove(user);
        } catch(Exception e) {
            return false;
        }
        return true;
    }


}
