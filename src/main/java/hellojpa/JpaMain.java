package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setName("jerry");
            member.setHomeAddress(new Address("city","street","zipcode"));

            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getAddressHistory().add(new Address("old1","street","zipcode"));
            member.getAddressHistory().add(new Address("old2","street","zipcode"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==============start==============");
            Member findMember = em.find(Member.class,member.getId());
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("Address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }
}
