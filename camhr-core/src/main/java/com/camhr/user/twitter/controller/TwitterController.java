package com.camhr.user.twitter.controller;

import com.camhr.user.twitter.builders.TwitterCommentQueryBuiler;
import com.camhr.user.twitter.entity.Twitter;
import com.camhr.user.twitter.entity.TwitterComments;
import com.camhr.user.twitter.service.TwitterCommentsService;
import com.camhr.user.twitter.service.TwitterService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import we.query.QueryBuilder;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/users/twitters")
public class TwitterController {

  //old注释一下

  @Autowired
  private TwitterService twitterService;

  @Autowired
  private TwitterCommentsService twitterCommentsService;

  @PostMapping
  @ApiOperation(value = "发布动态")
  private Result addTwitter(@ApiIgnore @CurrentUser User user,
      @RequestBody @Valid Twitter twitter) {
    twitter.setUserId(user.getUserId());
    return Result.of(twitterService.addTwitter(twitter))
        .success("{twitter.add.success}")
        .fail("{twitter.add.fail}");
  }

  @DeleteMapping("/{twitterId}")
  @ApiOperation(value = "删除动态")
  private Result removeTwitter(@ApiIgnore @CurrentUser User user,
      @PathVariable("twitterId") long twitterId) {
    return Result.of(twitterService.removeTwitter(user.getUserId(), twitterId))
        .success("{twitter.remove.success}")
        .fail("{twitter.remove.fail}");
  }

  @PutMapping("/{twitterId}")
  @ApiOperation(value = "修改动态")
  private Result updateTwitter(@ApiIgnore @CurrentUser User user,
      @PathVariable("twitterId") long twitterId,
      @RequestBody Twitter twitter) {
    twitter.setId(twitterId);
    twitter.setUserId(user.getUserId());
    return Result.of(twitterService.updateTwitter(twitter))
        .success("{twitter.update.success}")
        .fail("{twitter.update.fail}");
  }

  @PostMapping({"/images"})
  @ApiOperation(value = "上传动态的图片", notes = "上传动态的图片")
  public Result uploadTwitterImages(@ApiIgnore @CurrentUser User user,
      MultipartFile image)
      throws IOException {
    return Result
        .data(twitterService.uploadTwitterImages(image.getOriginalFilename(), image.getBytes()));
  }

  @GetMapping
  @ApiOperation(value = "获取所有的动态列表", response = Twitter.class, responseContainer = "List")
  public Result queryTwitters(@ApiIgnore @CurrentUser User user,
      QueryBuilder queryBuilder) {
    return Result.data(twitterService.queryTwitters(queryBuilder));
  }

  @PostMapping("/{twitterId}/comments")
  @ApiOperation(value = "发表评论")
  private Result addComment(@ApiIgnore @CurrentUser User user,
     @PathVariable("twitterId") long twitterId,
      @RequestBody @Valid TwitterComments twitterComment) {
    twitterComment.setUserId(user.getUserId());
    twitterComment.setTwitterId(twitterId);
    return Result.of(twitterCommentsService.addTwitterComment(twitterComment))
        .success("{twitter.comment.add.success}")
        .fail("{twitter.comment.add.fail}");
  }

  @DeleteMapping("/{twitterId}/comments/{twitterCommentId}")
  @ApiOperation(value = "删除评论")
  private Result deleteTwitterComment(@ApiIgnore @CurrentUser User user,
      @PathVariable("twitterId") long twitterId,
      @PathVariable("twitterCommentId") long twitterCommentId) {
    return Result
        .of(twitterCommentsService
            .removeTwitterComment(twitterCommentId, user.getUserId(), twitterId))
        .success("{twitter.comment.remove.success}")
        .fail("{twitter.comment.remove.fail}");
  }

  @PutMapping("/{twitterId}/comments/{twitterCommentId}")
  @ApiOperation(value = "修改评论")
  private Result updateTwitterComment(@ApiIgnore @CurrentUser User user,
      @PathVariable("twitterCommentId") long twitterCommentId,
      @RequestBody @Valid TwitterComments twitterComment) {
    twitterComment.setId(twitterCommentId);
    twitterComment.setUserId(user.getUserId());
    return Result
        .of(twitterCommentsService.updateTwitterComment(twitterComment))
        .success("{twitter.comment.update.success}")
        .fail("{twitter.comment.update.fail}");
  }

  @GetMapping("/{twitterId}/comments/{twitterCommentId}")
  @ApiOperation(value = "获取动态的评论列表", response = TwitterComments.class, responseContainer = "List")
  private Result queryTwitterComments(@ApiIgnore @CurrentUser User user,
      TwitterCommentQueryBuiler twitterCommentQueryBuiler) {
    return Result.data(twitterCommentsService.queryTwitterComments(twitterCommentQueryBuiler));
  }

  @PostMapping("/{twitterId}/likes")
  @ApiOperation(value = "点赞")
  public Result likeTwitter(@ApiIgnore @CurrentUser User user,
      @PathVariable("twitterId") long twitterId) {
    return Result.of(twitterService.likeTwitter(twitterId, user.getUserId()))
        .success("{twitter.like.success}")
        .fail("{twitter.like.fail}");
  }

  @DeleteMapping("/{twitterId}/dislikes")
  @ApiOperation(value = "取消点赞")
  public Result dislikeTwitter(@ApiIgnore @CurrentUser User user,
      @PathVariable("twitterId") long twitterId) {
    return Result.of(twitterService.dislikeTwitter(twitterId, user.getUserId()))
        .success("{twitter.dislike.success}")
        .fail("{twitter.dislike.fail}");
  }

}
