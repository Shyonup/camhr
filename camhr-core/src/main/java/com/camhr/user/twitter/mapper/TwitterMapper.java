package com.camhr.user.twitter.mapper;

import com.camhr.user.twitter.entity.Twitter;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import we.query.QueryBuilder;

@Mapper
public interface TwitterMapper {

  int addTwitter(Twitter twitter);

  int removeTwitter(@Param("userId") long userId, @Param("twitterId") long twitterId);

  int updateTwitter(Twitter twitter);

  List<Twitter> queryTwitterList(QueryBuilder queryBuilder);

  long countTwitterList(QueryBuilder queryBuilder);

  int likeTwitter(@Param("twitterId") long twitterId, @Param("userId") long userId);

  int dislikeTwitter(@Param("twitterId") long twitterId, @Param("userId") long userId);

  int addTwitterLikes(@Param("twitterId") long twitterId);

  int minusTwitterLikes(@Param("twitterId") long twitterId);

  int addTwitterComments(@Param("twitterId") long twitterId);

  int minusTwitterComments(@Param("twitterId") long twitterId);
}
