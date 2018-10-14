package com.camhr.user.twitter.service.impl;


import com.camhr.user.twitter.builders.TwitterCommentQueryBuiler;
import com.camhr.user.twitter.entity.TwitterComments;
import com.camhr.user.twitter.mapper.TwitterCommentsMapper;
import com.camhr.user.twitter.service.TwitterCommentsService;
import com.camhr.user.twitter.service.TwitterService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.util.Page;

@Service
public class TwitterCommentsServiceImpl implements TwitterCommentsService {

  @Autowired
  private TwitterCommentsMapper twitterCommentsMapper;

  @Autowired
  private TwitterService twitterService;

  @Override
  @Transactional
  public int addTwitterComment(TwitterComments twitterComment) {
    twitterService.addTwitterComments(twitterComment.getTwitterId());
    return twitterCommentsMapper.addTwitterComment(twitterComment);
  }

  @Override
  @Transactional
  public int removeTwitterComment(long twitterCommentId, long userId, long twitterId) {
    twitterService.minusTwitterComments(twitterId);
    return twitterCommentsMapper.removeTwitterComment(twitterCommentId, userId);
  }

  @Override
  public int updateTwitterComment(TwitterComments twitterComment) {
    twitterComment.setUpdateAt(new Date());
    return twitterCommentsMapper.updateTwitterComment(twitterComment);
  }

  @Override
  public Page<TwitterComments> queryTwitterComments(
      TwitterCommentQueryBuiler twitterCommentQueryBuiler) {
    Page<TwitterComments> page = Page.of(twitterCommentQueryBuiler);
    page.setResult(twitterCommentsMapper.queryTwitteerCommentList(twitterCommentQueryBuiler));
    page.setTotalCount(twitterCommentsMapper.countTwitterCommentList(twitterCommentQueryBuiler));
    return page;
  }

}
