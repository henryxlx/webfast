<#assign panel = 'dashboard'/>
<#assign nav = 'system'/>

<#include '/admin/layout.ftl'/>

<#macro blockContent>
<link href="${ctx}/assets/libs/gallery/morris/0.5.0/morris.css" rel="stylesheet" />
<div class="row">

    <#if blockSideBar??><@blockSideBar/></#if>

    <div class="col-md-12">
        <#if blockAnalysisHead??><@blockAnalysisHead />
        <#else>
        <div class="col-md-12">
            <@web_macro.flash_messages />
            <div class="page-header">
                <h1>数据统计</h1>
            </div>
            <form  class="well well-sm form-inline" action="${ctx}/admin/operation/analysis/rountByanalysisDateType/${tab!'trend'}" method="get" id="operation-form"  role="form" >
            <div class="form-group">
                <select class="form-control" name="analysisDateType">
                    <@select_options dict['analysisDateType']!{} 'dataInfo.analysisDateType' '--选择数据类型--' />
                </select>
            </div>
            <div class="form-group">
                <a  type="button" class="btn btn-default"  id="btn-month" currentMonthStart="${(dataInfo.currentMonthStart)!}" currentMonthEnd="${(dataInfo.currentMonthEnd)!}">本月</a>
            </div>
            <div class="form-group">
                <a type="button"  class="btn btn-default "  id="btn-lastMonth" lastMonthStart="${(dataInfo.lastMonthStart)!}" lastMonthEnd="${(dataInfo.lastMonthEnd)!}">上月</a>
            </div>

            <div class="form-group">
                <a type="button"  class="btn btn-default " id="btn-lastThreeMonths" lastThreeMonthsStart="${(dataInfo.lastThreeMonthsStart)!}" lastThreeMonthsEnd="${(dataInfo.lastThreeMonthsEnd)!}">近三月</a>
            </div>

            <div class="form-group">
                <label class="col-md-4" style="padding-top:9px;">起始日期</label>
                <div class="col-md-8 ">
                    <input type="text" class="form-control" name="startTime" value="${(dataInfo.startTime)!}">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4" style="padding-top:9px;">截止日期</label>
                <div class="col-md-8 ">
                    <input type="text" class="form-control"  name="endTime" value="${(dataInfo.endTime)!}">
                </div>
            </div>

            <button class="btn btn-primary pull-right" id="btn-search">查询</button>
            </form>

            <#if showHelpMessage!0 == 1>
            <div class="help-block">视频观看数，将从数据统计功能更新启用之日起开始记录</div>
            </#if>
        </div>
        <div class="col-md-12"><br>
        </div>
        <div class="col-md-12">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" <#if tab! =="trend"> class="active"</#if> ><a href="${ctx}/${href!}/trend?startTime=${(dataInfo.startTime)!}&endTime=${(dataInfo.endTime)!}&analysisDateType=${(dataInfo.analysisDateType)!}">趋势</a></li>
                <li role="presentation" <#if tab! =="detail"> class="active"</#if>><a href="${ctx}/${href!}/detail?startTime=${(dataInfo.startTime)!}&endTime=${(dataInfo.endTime)!}&analysisDateType=${(dataInfo.analysisDateType)!}">明细</a></li>
            </ul>
        </div>
        </#if>
        <#if blockAnalysisBody??><@blockAnalysisBody /></#if>
    </div>
</div>

</#macro>