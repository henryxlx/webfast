<#assign bodyClass = 'teacherpage'/>

<#assign metaKeywords>用户介绍 ${setting('site.name')!}</#assign>
<#assign metaDescription>${setting('site.name')!}的用户介绍。</#assign>

<#include '/layout.ftl'/>

<#macro blockTitle>用户介绍 - ${blockTitleParent}</#macro>

<#macro blockContent>

    <div class="es-row-wrap container-gap">
        <div class="row">
            <div class="col-md-12">
                <div class="page-header"><h1>用户介绍</h1></div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">

                <ul class="media-list">
                    <#list users! as user>
                        <#assign profile = profiles[''+user.id]! />
                        <li class="media teacher">
                            <a class="pull-left" href="${ctx}/user/${user.id}">
                                <img class="media-object" src="${default_path('avatar',user.mediumAvatar, '')}">
                            </a>
                            <div class="media-body">
                                <button class="btn btn-default pull-right" data-toggle="modal" data-target="#modal"
                                        data-url="${ctx}/message/create/${user.id}">私信
                                </button>

                                <h4 class="media-heading">
                                    <a href="${ctx}/user/${user.id}">${user.username}</a>
                                </h4>
                                <div class="teacher-title">
                                    <#if user.title??>
                                        ${user.title}
                                    <#else>
                                        <span class="text-muted">请设置头衔</span>
                                    </#if>
                                </div>
                                <div class="teacher-about">${(profile.about)!}</div>
                            </div>
                        </li>
                    </#list>
                </ul>
                <@web_macro.paginator paginator! />

            </div>
        </div>

    </div>
</#macro>