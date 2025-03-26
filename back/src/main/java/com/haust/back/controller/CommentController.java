package com.haust.back.controller;

import java.sql.Timestamp;
import java.util.Date;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haust.back.entity.Comment;
import com.haust.back.mapper.CommentMapper;

@RestController
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Operation(summary = "添加一条评论，需传入评论内容")
    @PostMapping("/comment/insert")
    public Comment insertComment(Comment comment)
    {
        Date date = new Date();//获得当前时间
        Timestamp t = new Timestamp(date.getTime());//将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
        comment.setCDate(t);
        commentMapper.insert(comment);
        return comment;
    }
    @Operation(summary = "分页查询指定nid新闻的所有评论，需传入新闻ID，默认每次查询5条数据")
    @GetMapping("/comment/{nid}/{id}/{size}")
    public IPage getCommentByid(@PathVariable(value = "nid") Integer nid,@PathVariable(value = "id") int id,@PathVariable(value = "size") int size)
    {
        Page<Comment> page = new Page<>(id, size);
        IPage iPage = commentMapper.selectPage(page, new QueryWrapper<Comment>().eq("new_id", nid));
        return iPage;
    }
    @Operation(summary = "查询指定nid新闻的点赞数量，需传入新闻ID")
    @GetMapping("/comment/like/{nid}")
    public Long getlike(@PathVariable(value = "nid") Integer nid)
    {
        Long like = commentMapper.selectCount(new QueryWrapper<Comment>().eq("new_id", nid));
        return like;
    }
    @Operation(summary = "查询指定nid新闻的点踩数量，需传入新闻ID")
    @GetMapping("/comment/gdislike/{nid}")
    public Long getdislike(@PathVariable(value = "nid") Integer nid)
    {
        Long like = commentMapper.selectCount(new QueryWrapper<Comment>().eq("new_id", nid));
        return like;
    }
    @Operation(summary = "增加指定nid新闻的点赞数量，需传入新闻ID")
    @GetMapping("/comment/plike/{nid}")
    public Long postlike(@PathVariable(value = "nid") Integer nid)
    {
        Long like = commentMapper.selectCount(new QueryWrapper<Comment>().eq("new_id", nid));
        return like;
    }
    @Operation(summary = "增加指定nid新闻的点踩数量，需传入新闻ID")
    @GetMapping("/comment/pdislike/{nid}")
    public Long postdislike(@PathVariable(value = "nid") Integer nid)
    {
        Long like = commentMapper.selectCount(new QueryWrapper<Comment>().eq("new_id", nid));
        return like;
    }
}
