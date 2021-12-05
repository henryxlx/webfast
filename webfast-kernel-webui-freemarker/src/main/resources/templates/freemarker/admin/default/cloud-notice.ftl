<#if notices??>
<div class="col-md-12">
    <div class="main-system-alert">
        <h2>站长公告</h2>
        <ul class="alert-list">
            <#list notices! as notice>
            <li class="glyphicon glyphicon-bell" style="color:#fed032;font-size:16px;"><a href="{{notice.detailUrl}}" style="margin-left:22px;" target="_blank">{{notice.content}}<span>{{notice.createdTime|date("Y-m-d H:i:s")}}</span></a></li>
            </#list>
        </ul>
    </div>
</div>
</#if>