package com.camhr.user.twitter.service;


import com.camhr.user.twitter.builders.TwitterCommentQueryBuiler;
import com.camhr.user.twitter.entity.TwitterComments;
import we.util.Page;

public interface TwitterCommentsService {

  int addTwitterComment(TwitterComments twitterComment);

  int removeTwitterComment(long twitterCommentId, long userId, long twitterId);

  int updateTwitterComment(TwitterComments twitterComment);

  Page<TwitterComments> queryTwitterComments(TwitterCommentQueryBuiler twitterCommentQueryBuiler);
}
