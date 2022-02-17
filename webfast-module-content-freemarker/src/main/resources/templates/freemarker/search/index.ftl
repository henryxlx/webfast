<#include '/layout.ftl'>

<#macro blockTitle>搜索：${keywords!} - ${blockTitleParent}</#macro>

<#macro blockContent>

<div class="es-row-wrap container-gap">
    <div class="row">
        <div class="col-md-12">
            <div class="page-header"><h1>搜索</h1></div>
        </div>
    </div>

    <div class="row row-3-9">
        <div class="col-md-9" style="min-height:300px;">
            <form class="well well-sm" action="${ctx}/search" id="search_box">

                <div style="float:left;">
                    <select  class="form-control searchSelect " name="categoryIds">
                        <@select_options categoryIds!, RequestParameters['categoryIds']!, '课程分类'/>
                    </select>
                </div>
                <div class="input-group input-group-lg">
                    <input type="text" class="form-control" name="q" value="${keywords!}" id="searchText"  placeholder="请输入感兴趣的课程名">
                    <span class="input-group-btn">
              <button class="btn btn-primary" type="submit">搜索</button>
              </span>
                </div>

                <div class="checkbox-group coursesTypeChoices" RepeatDirection="Horizontal" id="coursesTypeChoices" name="coursesTypeChoices">
                    <div class="sortedCourses">
                        <label >筛选课程:</label>
                        <#if isShowVipSearch??>
                        <#if currentUserVipLevel??>
                        <input type="checkbox" name="coursesTypeChoices" id="vipCourses"
                               value="vipCourses" data-url="${ctx}/search" <#if coursesTypeChoices! == 'vipCourses'> checked</#if> />
                        <label class="course-checkbox-label" for="vipCourses">仅显示会员课程</label>
                        </#if>
                        </#if>

                        <input type="checkbox" name="coursesTypeChoices" id="liveCourses"
                               value="liveCourses" data-url="${ctx}/search" <#if coursesTypeChoices! == 'liveCourses'> checked</#if> />
                        <label class="course-checkbox-label" for="liveCourses">仅显示直播课程</label>

                        <input type="checkbox" name="coursesTypeChoices" id="freeCourses"
                               value="freeCourses" data-url="${ctx}/search" <#if coursesTypeChoices! == 'freeCourses'> checked</#if> />
                        <label class="course-checkbox-label" for="freeCourses">仅显示免费课程</label>
                    </div>
                </div>
            </form>

            <#if keywords??>
            <#if courses??>
                <@renderController path='/course/courseBlock' params={'view': 'list'}/>
                <@web_macro.paginator paginator!/>
            <#else>
            <div class="empty">没有搜到相关课程，请换个关键词试试！</div>
            </#if>
            <#else>
            <div class="empty"></div>
            </#if> <#-- end of if keywords -->
        </div>
        <div class="col-md-3">

        </div>
    </div>

</div>
</#macro>