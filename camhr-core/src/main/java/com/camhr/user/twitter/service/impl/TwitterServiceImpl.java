package com.camhr.user.twitter.service.impl;


import com.camhr.user.twitter.constants.AnonymousStatus;
import com.camhr.user.twitter.entity.Twitter;
import com.camhr.user.twitter.error.TwitterErrorCodes;
import com.camhr.user.twitter.mapper.TwitterMapper;
import com.camhr.user.twitter.service.TwitterService;
import com.google.common.net.MediaType;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Exceptions;
import we.oss.ObjectKey;
import we.oss.PutObjectRequest;
import we.oss.PutObjectResult;
import we.oss.StorageService;
import we.query.QueryBuilder;
import we.util.Page;

@Service
public class TwitterServiceImpl implements TwitterService {

  @Autowired
  private TwitterMapper twitterMapper;

  @Autowired
  private StorageService storageService;

  @Override
  public int addTwitter(Twitter twitter) {
    return twitterMapper.addTwitter(twitter);
  }

  @Override
  public int removeTwitter(long seekerId, long twitterId) {
    return twitterMapper.removeTwitter(seekerId, twitterId);
  }

  @Override
  public int updateTwitter(Twitter twitter) {
    twitter.setUpdateAt(new Date());
    return twitterMapper.updateTwitter(twitter);
  }

  @Override
  public Page<Twitter> queryTwitters(QueryBuilder queryBuilder) {
    Page<Twitter> page = Page.of(queryBuilder);
    List<Twitter> list = twitterMapper.queryTwitterList(queryBuilder);
    for (Twitter twitter : list) {
      if (AnonymousStatus.ANONYMOUS.value() == twitter.getAnonymous()) {
        twitter.setUserName("");
        twitter.setUserId(0);
        twitter.setAvatar("");
      }
    }
    page.setResult(list);
    page.setTotalCount(twitterMapper.countTwitterList(queryBuilder));
    return page;
  }

  @Override
  public PutObjectResult uploadTwitterImages(String originalFilename, byte[] data)
      throws IOException {
    return storageService
        .upload(
            PutObjectRequest
                .of("twitters", ObjectKey.of("images", originalFilename), data)
                .accept(MediaType.ANY_IMAGE_TYPE));
  }

  @Override
  @Transactional
  public int likeTwitter(long twitterId, long userId) {
    try{
      addTwitterLikes(twitterId);
      twitterMapper.likeTwitter(twitterId,userId);
    }catch (Exception e){
      Exceptions.of(TwitterService.class).error(TwitterErrorCodes.DUPLICAT_LIKE_OPRATION);
    }
    return 1;
  }

  @Override
  @Transactional
  public int dislikeTwitter(long twitterId, long userId) {
  if(twitterMapper.dislikeTwitter(twitterId,userId) > 0){
    minusTwitterLikes(twitterId);
  }
    return 1;
  }

  @Override
  public int addTwitterLikes(long twitterId) {
    return twitterMapper.addTwitterLikes(twitterId);
  }

  @Override
  public int minusTwitterLikes(long twitterId) {
    return twitterMapper.minusTwitterLikes(twitterId);
  }

  @Override
  public int addTwitterComments(long twitterId) {
    return twitterMapper.addTwitterComments(twitterId);
  }

  @Override
  public int minusTwitterComments(long twitterId) {
    return twitterMapper.minusTwitterComments(twitterId);
  }
}
