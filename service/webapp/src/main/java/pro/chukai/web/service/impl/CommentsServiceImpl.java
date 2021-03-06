package pro.chukai.web.service.impl;

import pro.chukai.common.util.NetUtil;
import pro.chukai.web.constant.Constants;
import pro.chukai.web.dto.out.CommentsOut;
import pro.chukai.web.dto.out.PageOut;
import pro.chukai.web.exception.HttpStatusException;
import pro.chukai.web.mapper.CommentMapper;
import pro.chukai.web.model.Comment;
import pro.chukai.web.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pro.chukai.web.constant.Constants;
import pro.chukai.web.dto.out.CommentsOut;
import pro.chukai.web.dto.out.PageOut;
import pro.chukai.web.exception.HttpStatusException;
import pro.chukai.web.mapper.CommentMapper;
import pro.chukai.web.model.Comment;
import pro.chukai.web.service.CommentService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl extends BaseService implements CommentService {

    @Autowired
    private CommentMapper commentsMapper;

    @Resource(name = "oneMin")
    private Cache cache;

    @Override
    public boolean postComments(Comment comments) {
        checkExistBlank(comments.getArticleId(), comments.getComment(), comments.getUserName());
        String curSessionId = NetUtil.getCurSessionId() + "_cmts";
        Integer count = cache.get(curSessionId, () -> 0);
        if (count > 1) {
            throw HttpStatusException.of(HttpStatus.CONFLICT, "评论过于频繁，请稍候1分钟!");
        } else {
            cache.put(curSessionId, ++count);
        }
        comments.setCreateTime(LocalDateTime.now());
        commentsMapper.insertSelective(comments);
        commentsMapper.recalcCommentsCountForArticle(comments.getArticleId());
        return comments.getId() != null;// 插入成功后会自动注入id
    }

    @Override
    public PageOut<CommentsOut> listComments(int pageNum, Integer articleId) {
        checkExistBlank(articleId);
        PageHelper.startPage(pageNum, Constants.DEFAULT_PAGE_SIZE);
        Comment record = new Comment();
        record.setArticleId(articleId);
        Page<Comment> page = (Page<Comment>) commentsMapper.select(record);
        PageOut<CommentsOut> pageOut = new PageOut<>(page.getPageNum(), page.getPages());
        pageOut.setList(page.stream().map(a -> new CommentsOut().buildFromModel(a)).collect(Collectors.toList()));
        return pageOut;
    }

    @Override
    public List<Comment> findRecentlyComments() {
        PageHelper.startPage(1, 3);
        Page<Comment> page = (Page<Comment>) commentsMapper.selectAll();
        // 防暴露email
        return page.stream().map(p -> {
            p.setEmail(null);
            return p;
        }).collect(Collectors.toList());
    }
}
