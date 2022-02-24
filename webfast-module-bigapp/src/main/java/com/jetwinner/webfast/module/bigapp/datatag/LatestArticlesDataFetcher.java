package com.jetwinner.webfast.module.bigapp.datatag;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.datatag.BaseDataFetcher;
import com.jetwinner.webfast.kernel.datatag.annotations.FastDataTag;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.module.bigapp.service.AppArticleCategoryService;
import com.jetwinner.webfast.module.bigapp.service.AppArticleService;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@FastDataTag
public class LatestArticlesDataFetcher extends BaseDataFetcher {

    private final AppArticleService articleService;
    private final AppArticleCategoryService categoryService;

    public LatestArticlesDataFetcher(ApplicationContext applicationContext) {
        super(applicationContext);
        this.articleService = applicationContext.getBean(AppArticleService.class);
        this.categoryService = applicationContext.getBean(AppArticleCategoryService.class);
    }

    /**
     * 获取最新资讯列表
     *
     * 可传入的参数：
     *   count    必需 课程数量，取值不能超过100
     *
     *   type:  featured  可选  是否头条
     *          promoted  可选  是否推荐
     *          sticky    可选  是否置顶
     *
     * @param  arguments 参数
     * @return array 资讯列表
     */
    @Override
    public List<Map<String, Object>> getData(Map<String, Object> arguments) {
        if (EasyStringUtil.isBlank(arguments.get("count"))) {
            throw new RuntimeGoingException("count参数缺失");
        }
        int count = ValueParser.parseInt(arguments.get("count"));
        if (count > 100) {
            throw new RuntimeGoingException("count参数超出最大取值范围");
        }

        Map<String, Object> conditions = new HashMap<>(arguments.size());
        if ("featured".equals(arguments.get("type"))) {
            conditions.put("featured", 1);
        }

        if ("promoted".equals(arguments.get("type"))) {
            conditions.put("promoted", 1);
        }

        if ("sticky".equals(arguments.get("type"))) {
            conditions.put("sticky", 1);
        }

        List<Map<String, Object>> articles =
                articleService.searchArticles(conditions,"created", 0, count);

        Map<String, Map<String, Object>> categories =
                categoryService.findCategoriesByIds(ArrayToolkit.column(articles, "categoryId"));

        articles.forEach(e -> {
            if (EasyStringUtil.isBlank(e.get("categoryId"))) {
                return;
            }
            final String categoryId = String.valueOf(e.get("categoryId"));
            if (categoryId.equals(categories.get(categoryId).get("id"))) {
                e.put("category", categories.get(categoryId));
            }
        });
        return articles;
    }
}
