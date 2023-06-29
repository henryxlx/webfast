<@block_title '标签'/>

<#include '/default/layout.ftl'/>

<#macro blockContent>

    <div class="es-row-wrap container-gap">
        <div class="row">
            <div class="col-md-12">
                <div class="page-header"><h1>标签</h1></div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12" style="min-height:300px;">

                <#list tags! as tag>
                    <a href="${ctx}/tag/${tag.id}" class="btn btn-default"><span
                                class="text-muted glyphicon glyphicon-tag"></span> ${tag.name}</a>
                <#else>
                    <div class="empty">当前还没有标签!</div>
                </#list>

            </div>
        </div>

    </div>
</#macro>