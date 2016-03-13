package qa.vo;

import java.io.Serializable;

/**
 * Created by peakone on 2015/11/7.   //暂时没用到
 */
public class QaUserVo implements Serializable {

    private Integer id;
    private String name;
    private String password;
    //荣誉分
    private Long score;

    public QaUserVo(){}

    public QaUserVo(Integer id, String name, String password, Long score) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        score = score == null ? 0 : score;
        this.score = score;
    }
}
