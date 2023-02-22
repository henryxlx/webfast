<#include '/default/layout.ftl'>

<#macro blockStylesheetsExtra>
    <link rel="stylesheet" media="screen" href="${ctx}/themes/default/css/class-default.css" />
</#macro>

<#macro blockContent>
<div class="es-row-wrap container-gap">

    <#if home_top_banner??>
    <div class="homepage-feature homepage-feature-slides mbl">
        <div class="cycle-pager"></div>
        ${home_top_banner.content!}
    </div>
    </#if>

    <div class="row row-9-3">

        <div class="col-md-9">

            <#-- 最新资讯 -->
            <#assign articles = data('LatestArticles')/>
            <#if articles??>
            <div class="es-box news">
                <div class="es-box-heading">
                    <h2>最新资讯</h2>
                    <a class="pull-right" href="${ctx}/article">更多&gt;</a>
                </div>
                <div class="es-box-body">
                    <ul class="row">
                        <#list articles as article>
                        <li class="col-md-6">
                            <em>${article.updatedTime?number_to_datetime?string('yyyy-MM-dd HH:mm')} </em>
                            <a href="${ctx}/article/${article.id}"> <span>[${(article.category.name)!'未分类'}]</span>${article.title} </a>
                        </li>
                        </#list>
                    </ul>
                </div>
            </div>
            </#if>

            <#-- 小组 -->
            <#if setting('group.group_show')??>
            {% set groups = data('HotGroup', {'count':15}) %}
            <#if groups??>
                <div class="es-box hot-group">
                    <div class="es-box-heading"><h2>最热小组</h2><a href="${ctx}/group/search_group" class="pull-right">更多&gt;</a>
                    </div>
                    <div class="es-box-body">
                        <ul class="list-unstyled">
                            <#list groups as group>
                                <#if group.status=='open'>
                                    <li class="col-md-4">
                                        <div class="panel">
                                            <div class="media">
                                                <a href="${ctx}/group/${group.id}" title="${group.title}"
                                                   class="pull-left">
                                                    <#if group.logo??>
                                                        <img src="${file_path(group.logo)}" alt="${group.title}">
                                                    <#else>
                                                        <img src="${ctx}/assets/img/default/group.png"
                                                             alt="${group.title}">
                                                    </#if>
                                                </a>
                                                <div class="media-body">
                                                    <#assign groupTitle = (group.title?? && group.title?length gt 10)?string(group.title.substring(0, 10), group.title) />
                                                    <p><a href="${ctx}/group/${group.id}"
                                                          title="${group.title}">${groupTitle}</a></p>
                                                    <div class="text-muted text-normal">
                                                        ${group.memberNum}个成员&nbsp;
                                                        ${group.threadNum}个话题
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </#if>
                            </#list>
                    </ul>
                </div>
            </div>
            </#if>
            </#if>
        </div>

        <div class="col-md-3">
            <#-- 最热话题 -->
            <#if setting('group.group_show')??>
                <#assign hotThreads = data('HotThreads', {'count':11})/>
                <#if hotThreads??>
                    <div class="es-box hot-threads">
                        <div class="es-box-heading"><h2>最热话题</h2></div>
                        <div class="es-box-body">
                            <ul class="text-list">
                                <#list hotThreads! as thread>
                                    <#assign threadTitle = (thread.title?? && thread.title?length gt 15)?string(thread.title.substring(0, 15), thread.title) />
                                    <li style="border-bottom:none;background:url('${ctx}/assets/img/default/triangle.png') no-repeat 0 3px;padding-left:8px;padding-top:0px;margin-bottom:8px;">
                                        <a href="${ctx}/group/${thread.groupId}/thread/${thread.id}">${threadTitle}</a>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </#if>
            </#if>

        </div>

    </div>

</div>
</#macro>