<#if articles??>
  <div class="panel panel-default sub-articles">
    <div class="panel-heading">
      <h3 class="panel-title">推荐资讯</h3>
    </div>
    <div class="panel-body">
      <ul class="media-list">
        <#list articles as article>
          <li class="media">
            <span class="rank_num">${article?counter}</span>
            <div class="media-body"><a href="${ctx}/article/${article.id}" title="${article.title}">${article.title[0..*19]}</a></div>
          </li>
        </#list>
      </ul>
    </div>
  </div>
</#if>