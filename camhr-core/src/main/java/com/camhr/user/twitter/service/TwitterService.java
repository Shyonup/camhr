package com.camhr.user.twitter.service;

import com.camhr.user.twitter.entity.Twitter;
import java.io.IOException;
import org.apache.ibatis.annotations.Param;
import we.oss.PutObjectResult;
import we.query.QueryBuilder;
import we.util.Page;

public interface TwitterService {

  int addTwitter(Twitter twitter);

  int removeTwitter(long seekerId, long twitterId);

  int updateTwitter(Twitter twitter);

  Page<Twitter> queryTwitters(QueryBuilder queryBuilder);

  PutObjectResult uploadTwitterImages(String originalFilename, byte[] data)
      throws IOException;

  int likeTwitter(long twitterId, long userId);

  int dislikeTwitter(long twitterId, long userId);

  int addTwitterLikes(long twitterId);

  int minusTwitterLikes(long twitterId);

  int addTwitterComments(long twitterId);

  int minusTwitterComments(long twitterId);
}
