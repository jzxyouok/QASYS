package qa.service;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import qa.dao.UserDao;
import qa.dao.WordsDao;
import qa.domain.QaUser;
import qa.domain.Question;
import qa.domain.Vote;
import qa.domain.Words;

@Aspect
public class PointsService {
    private WordsDao wordsDao;
    private UserDao userDao;

    public PointsService(WordsDao wordsDao,UserDao userDao) {
        this.wordsDao = wordsDao;
        this.userDao = userDao;
    }

    @Pointcut("bean(voteService) && execution(* addOneVote(..))")
    private void voteUp() {
        //Empty.
    }

    @AfterReturning(value = "voteUp() && target(service)", returning = "vote")
    public void afterVoteUp(VoteService service, Vote vote) {
        Words words = vote.getWords();
        //得到问题或回答的user
        QaUser user = words.getWhoCreated();

        if (vote.isUpVoted()) {
            words.addPoint(10); //TODO hard code
            user.addScore(10);
        } else {
            words.minusPoint(10);
            user.minusScore(10);
        }
        wordsDao.update(words);
        userDao.update(user);

    }
}
