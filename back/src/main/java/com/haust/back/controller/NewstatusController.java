package com.haust.back.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haust.back.entity.Newstatus;
import com.haust.back.mapper.NewstatusMapper;

@RestController
public class NewstatusController {
    @Autowired
    private NewstatusMapper newstatusMapper;

    @Operation(summary = "查询指定新闻内容的行为数据，需传入新闻ID")
    @GetMapping("/newstatus/status/{nid}")
    public Newstatus getstatusbynid(@PathVariable Integer nid) {
        return newstatusMapper.selectOne(new QueryWrapper<Newstatus>().eq("new_id", nid));
    }

    @Operation(summary = "查询指定nid新闻的点赞数量，需传入新闻ID")
    @GetMapping("/newstatus/like/{nid}")
    public Long getlike(@PathVariable Integer nid) {
        Long like = newstatusMapper.selectCount(new QueryWrapper<Newstatus>().eq("new_id", nid));
        return like;
    }

    @Operation(summary = "查询指定nid新闻的点踩数量，需传入新闻ID")
    @GetMapping("/newstatus/gdislike/{nid}")
    public Long getdislike(@PathVariable Integer nid) {
        Long like = newstatusMapper.selectCount(new QueryWrapper<Newstatus>().eq("new_id", nid));
        return like;
    }

    @Operation(summary = "增加指定nid新闻的点赞数量，需传入新闻ID")
    @PostMapping("/newstatus/like")
    public Newstatus postlike(Newstatus newstatus)
    {
        newstatus.setNewLike(newstatus.getNewLike() + 1);
        newstatusMapper.update(newstatus, new QueryWrapper<Newstatus>().eq("new_id", newstatus.getNewId()));
        return newstatus;
    }

    @Operation(summary = "增加指定nid新闻的点踩数量，需传入新闻ID")
    @PostMapping("/newstatus/dislike")
    public Newstatus postdislike(Newstatus newstatus) {
        newstatus.setNewDislike(newstatus.getNewDislike() + 1);
        newstatusMapper.update(newstatus, new QueryWrapper<Newstatus>().eq("new_id", newstatus.getNewId()));
        return newstatus;
    }

    @Operation(summary = "增加指定nid新闻的转发数量，需传入新闻ID")
    @PostMapping("/newstatus/forward")
    public Newstatus postforward(Newstatus newstatus) {
        newstatus.setNewForward(newstatus.getNewForward() + 1);
        newstatusMapper.update(newstatus, new QueryWrapper<Newstatus>().eq("new_id", newstatus.getNewId()));
        return newstatus;
    }
}
