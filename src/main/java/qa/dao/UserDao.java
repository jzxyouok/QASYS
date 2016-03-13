package qa.dao;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import qa.domain.QaUser;
import qa.vo.QaUserVo;

import java.util.List;

@Transactional
public class UserDao {
    private HibernateTemplate template;

    public UserDao(HibernateTemplate template) {
        this.template = template;
    }

    @Transactional(readOnly = true)
    public QaUser findByName(String username) {
        List<QaUser> users = (List < QaUser >)template.find("FROM QaUser where name = ?", username);
        return users.isEmpty() ? null : users.get(0);
    }
    @Transactional(readOnly = true)
    public QaUser findById(int id) {
        return template.get(QaUser.class,id);
    }

    /**
     * 模糊查询
     * @param username
     */
    @Transactional(readOnly = true)
    public List<QaUser> findName(String username) {
        List<QaUser> users = (List < QaUser >)template.find("FROM QaUser where name like '%"+username+"%' order by score desc");
        return users;
    }

    public QaUser persist(QaUser user) {
        template.persist(user);
        return user;
    }

    public void update(QaUser user) {
        template.merge(user);
    }

    @Transactional(readOnly = true)
    public List<QaUser> findAll(){
        String hql = "FROM QaUser order by score desc";
        return (List<QaUser>) template.find(hql);
    }
    /**
     * 此处考虑到当user的word不是急加载或部分加载时显示积分就不正确，
     * 顺便试一下将查询结果映射为非(pojo) VO中
     * 当然可以在users表中新增字段存储
     */
    /*@Transactional(readOnly = true)
    public List<QaUserVo> findAll(){

        String sql = "SELECT u.ID ,u.NAME  , u.PASSWORD ,sum(w.POINTS) score" +
                " FROM USERS u " +
                " LEFT JOIN WORDS w ON u.ID = w.USER_ID" +
                " GROUP BY u.ID" +
                " ORDER BY score DESC";

        Session session = template.getSessionFactory().getCurrentSession();
        Query query = session.createSQLQuery(sql)
                .addScalar("id").addScalar("name").addScalar("password").addScalar("score", StandardBasicTypes.LONG)
                .setResultTransformer(Transformers.aliasToBean(QaUserVo.class));
        List<QaUserVo> list = query.list();
        return list;
    }*/

}
