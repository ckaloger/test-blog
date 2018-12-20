package EJB;

import data.Article;
import data.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class UserDAO {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void persist(User user){
        manager.persist(user);
    }

    @Transactional
    public void persist(Article article){
        manager.persist(article);
    }

    @Transactional
    public void remove(Article article){
        System.out.println("userdao remove article id = " + article.getId());
        System.out.println("article title = " + article.getTitle());
        manager.remove(article);
    }

    @Transactional
    public void remove(int id){
//        System.out.println("userdao remove article id = " + article.getId());
//        System.out.println("article title = " + article.getTitle());
        manager.remove(find(id));
    }


    @Transactional
    public List<Article> listAll() {
        return manager.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }


    @Transactional
    public User findWithUsername(String username){
        try {
            return manager
                    .createQuery("select u from User u where u.username = :username ", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public User findWithUsernameAndPassword(String username,String password){
        try {
            return manager
                    .createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public int find(int ownerId,int id){
        try {

                  if  (manager
                    .createQuery("select a from Article a where a.id = :id and a.ownerId = :ownerId", Article.class)
                    .setParameter("id", id)
                    .setParameter("ownerId", ownerId)
                    .getSingleResult() != null)
                return 1;
        }
        catch (NoResultException e) {
            return -1;
        }
        return -1;
    }

    public Article find(int id){
        try {

            return  manager
                    .createQuery("select a from Article a where a.id = :id", Article.class)
                    .setParameter("id", id)
                    .getSingleResult();

        }
        catch (NoResultException e) {
            return null;
        }
    }
}
