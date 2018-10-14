package com.camhr.user.twitter.mapper;


import com.camhr.user.twitter.builders.TwitterCommentQueryBuiler;
import com.camhr.user.twitter.entity.TwitterComments;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TwitterCommentsMapper {

  int addTwitterComment(TwitterComments twitterComment);

  int removeTwitterComment(@Param("twitterCommentId") long twitterCommentId,
      @Param("userId") long userId);

  int updateTwitterComment(TwitterComments twitterComment);

  List<TwitterComments> queryTwitteerCommentList(
      TwitterCommentQueryBuiler twitterCommentQueryBuiler);

  long countTwitterCommentList(TwitterCommentQueryBuiler twitterCommentQueryBuiler);
}
