package com.taotao.portal.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Value("${AD1_CONTENT_CID}")
    private Long AD1_CONTENT_CID;

    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;

    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;

    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;

    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @Resource
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        // 取内容分类id，需要从属性文件中取
        // 根据内容分类id查询内容列表
        List<TbContent> contentList = contentService.getContentList(AD1_CONTENT_CID);
        List<Ad1Node> ad1NodeList = new ArrayList<Ad1Node>();
        for (TbContent tbContent : contentList) {
            Ad1Node node = new Ad1Node();
            node.setAlt(tbContent.getSubTitle());
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);

            ad1NodeList.add(node);
        }
        // 将List集合转成json字符串
        String json = JsonUtils.objectToJson(ad1NodeList);
        model.addAttribute("ad1", json);
        return "index";
    }

}
