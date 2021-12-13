<#include '/default/layout.ftl'>

<#macro blockStylesheetsExtra>
    <link rel="stylesheet" media="screen" href="${ctx}/themes/default/css/class-default.css" />
</#macro>

<#macro blockContent>
<div class="es-row-wrap container-gap">

    <#if home_top_banner??>
    <div class="homepage-feature homepage-feature-slides mbl">
        <div class="cycle-pager"></div>
        ${home_top_banner!}
    </div>
    </#if>

    <div class="row row-9-3">

        <div class="col-md-9">

            <#if setting('classroom.enabled')??>
            {% set items = data('RecommendClassrooms',{'count':6}) %}
            <div class="es-box">
                <div class="es-box-heading">
                    <h2>{{ setting('classroom.name')|default("班级") }}</h2>
                    <a class="pull-right" href="{{ path('classroom_explore') }}">更多&gt;</a>
                </div>
                <div class="es-box-body">
                    {% for classroom in items.classrooms %}
                    {% set teachers = items.users[classroom.id]|default(null) %}
                    {% if loop.index <= 5%}
                    <ul class="home-class-list">
                        <li class="class-item">
                            <a class="class-picture-link" href="{{ path('classroom_show', {id:classroom.id}) }}">
                                <img src="{{ default_path('classroomPicture',classroom.largePicture, 'large') }}" alt="{{ classroom.title }}">
                            </a>
                            <div class="class-body">
                                <h3 class="class-title">
                                    <a href="{{ path('classroom_show', {id:classroom.id}) }}">{{ classroom.title }}</a>
                                </h3>
                                <div class="class-metas">
                                    {% if teachers %}
                                    <span class="class-teacher">
                        <i class="fa fa-user"></i>
                        {% for teacher in teachers %}
                          {% if loop.index <= 5%}
                             <a class="teacher-nickname mrl" href="{{ path('user_show', {id:teacher.id}) }}">{{ teacher.nickname }}</a>
                           {% endif %}
                        {% endfor %}
                      </span>
                                    {% endif %}
                                    <span class="class-student hidden-md"><i class="fa fa-users"></i>{{classroom.studentNum + classroom.auditorNum }}</span>
                                    <span class="class-price">

                          {% if setting('coin.coin_enabled') and setting('coin.price_type') == 'Coin' %}
                              {% if classroom.price > 0 %}
                               {{classroom.price * setting('coin.cash_rate') }} {{setting('coin.coin_name')}}
                              {% else %}
                                免费
                              {% endif %}
                          {% else %}
                              {% if classroom.price > 0 %}
                                {{ classroom.price }}元
                              {% else %}
                                免费
                              {% endif %}
                          {% endif %}

                        </span>
                                </div>
                                <div class="course-num">共{{items.allClassrooms[classroom.id].courseNum}}课程</div>
                            </div>
                        </li>
                        {% endif %}
                        {% endfor %}
                    </ul>
                </div>
            </div>
            </#if>


            <#if courses??>
            <div class="es-box">
                <div class="es-box-heading">
                    <h2>课程</h2>
                    <a class="pull-right" href="${ctx}/course/explore">更多&gt;</a>
                </div>
                <div class="es-box-body">
                    {{ _self.course_lists(data('LatestCourses',{ count:5 })) }}
                </div>
            </div>
            </#if>

            <#if setting('course.live_course_enabled')??>

            {% if recentLiveCourses %}
            <div class="es-box">
                <div class="es-box-heading">
                    <h2>最新直播</h2>
                    {% if recentLiveCourses|length >= 1 %}
                    <a class="pull-right" href="{{ path('live_course_explore') }}">更多&gt;</a>
                    {% endif %}
                </div>
                <div class="es-box-body">
                    <@renderController path='TopxiaWebBundle:LiveCourse:coursesBlock' params={courses:recentLiveCourses, view: 'list'}/>
                </div>
            </div>
            {% endif %}

            </#if>

            <#-- 最新资讯 -->
            <#assign articles = data('LatestArticles', 4)/>
            <#if articles??>
            <div class="es-box news">
                <div class="es-box-heading">
                    <h2>最新资讯</h2>
                    <a class="pull-right" href="${ctx}/article/show">更多&gt;</a>
                </div>
                <div class="es-box-body">
                    <ul class="row">
                        <#list articles as article>
                        {% if article %}
                        <li class="col-md-6">
                            <em>{{ article.updatedTime|date('m-d H:i') }} </em>
                            <a href="{{ path('article_detail', {id:article.id}) }}"> <span>[{{ article.category.name|default('未分类') }}]</span>{{ article.title }} </a>
                        </li>
                        {% endif %}
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
                <div class="es-box-heading"><h2>最热小组</h2><a href="{{path('group_search_group')}}" class="pull-right">更多&gt;</a></div>
                <div class="es-box-body">
                    <ul class="list-unstyled">
                        {% for group in groups %}
                        {% if group.status=='open' %}
                        <li class="col-md-4">
                            <div class="panel">
                                <div class="media">
                                    <a href="{{path('group_show',{id:group.id})}}" title="{{group.title}}" class="pull-left">
                                        {% if group.logo %}
                                        <img src="{{file_path(group.logo)}}" alt="{{group.title}}">
                                        {%else%}
                                        <img src="{{asset('assets/img/default/group.png')}}" alt="{{group.title}}">
                                        {%endif%}
                                    </a>
                                    <div class="media-body">
                                        <p><a  href="{{path('group_show',{id:group.id})}}" title="{{group.title}}">{{group.title|sub_text(10)}}</a></p>
                                        <div class="text-muted text-normal">
                                            {{group.memberNum}}个成员&nbsp;
                                            {{group.threadNum}}个话题
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                        {% endif %}
                        {% endfor %}
                    </ul>
                </div>
            </div>
            </#if>
            </#if>
        </div>

        <div class="col-md-3">
            <@renderController path='/default/promotedTeacherBlock'/>

            <#-- 学员动态 -->
            <#assign learns = data('PersonDynamic', 5)/>
            <#if learns??>
            <div class="es-box status-side">
                <div class="es-box-heading">
                    <h2>${setting('default.user_name', '学员')}动态</h2>
                </div>
                <div class="es-box-body">
                    <ul class="media-list">
                        <#list learns as learn>
                        {% if learn.type == "finished_testpaper" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                完成了考试 {{learn.properties.testpaper.name|plain_text(15)}}
                            </div>
                        </li>

                        {% elseif learn.type == "finished_homework" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                完成了 课程 {{learn.properties.course.title|default('')|plain_text(15)}} 课时 {{learn.properties.lesson.title|default('')|plain_text(15)}} 下的作业
                            </div>
                        </li>

                        {% elseif learn.type == "finished_exercise" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                完成了 课程 {{learn.properties.course.title|default('')|plain_text(15)}} 课时 {{learn.properties.lesson.title|default('')|plain_text(15)}} 下的练习
                            </div>
                        </li>

                        {% elseif learn.objectType == "course" and learn.type == "become_student" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                加入了课程 {{learn.properties.course.title|plain_text(15)}}
                            </div>
                        </li>

                        {% elseif learn.objectType == "classroom" and learn.type == "become_student" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                成为了{{ setting('classroom.name')|default("班级") }} {{learn.properties.classroom.title|plain_text(15)}} 的学员
                            </div>
                        </li>

                        {% elseif learn.objectType == "classroom" and learn.type == "become_auditor" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                成为了{{ setting('classroom.name')|default("班级") }} {{learn.properties.classroom.title|plain_text(15)}} 的旁听生
                            </div>
                        </li>

                        {% elseif learn.type == "learned_lesson" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                完成了课时 {{learn.properties.lesson.title|plain_text(15)}}
                            </div>
                        </li>

                        {% elseif learn.type == "favorite_course" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                收藏了课程 {{learn.properties.course.title|plain_text(15)}}
                            </div>
                        </li>

                        {% elseif learn.type == "start_learn_lesson" %}
                        <li class="media">
                            <a class="pull-left" href="{{ path('user_show', {id:learn.user.id}) }}">
                                <img class="media-object" src="{{ default_path('avatar', learn.user.mediumAvatar, '') }}">
                            </a>
                            <div class="media-body">
                                <a href="{{ path('user_show', {id:learn.user.id}) }}">{{learn.user.nickname}}</a>
                                开始学习课时 {{learn.properties.lesson.title|plain_text(15)}}
                            </div>
                        </li>
                        {% endif %}
                        </#list>
                    </ul>
                </div>
            </div>
            </#if>

            <@renderController path='/default/latestReviewsBlock' params={'number':5}/>

            <#-- 最热话题 -->
            <#if setting('group.group_show')??>
            {% set hotThreads = data('HotThreads', {'count':11}) %}
            <#if hotThreads??>
            <div class='es-box hot-threads">
                <div class="es-box-heading"><h2>最热话题</h2></div>
                <div class="es-box-body">
                    <ul class="text-list">
                        {% for thread in hotThreads %}
                        {% if thread %}
                        <li style="border-bottom:none;background:url('${ctx}/assets/img/default/triangle.png') no-repeat 0 3px;padding-left:8px;padding-top:0px;margin-bottom:8px;"><a  href="{{path('group_thread_show',{id:thread.groupId,threadId:thread.id})}}">{{thread.title|sub_text(15)}}</a></li>
                        {% endif %}
                        {% endfor %}
                    </ul>
                </div>
            </div>
            </#if>
            </#if>

        </div>

    </div>

</div>
</#macro>

<#macro course_lists courses>
{% set mode = mode|default('default') %}
<ul class="course-wide-list">
    <#list courses! as course>
    <li class="course-item clearfix">
        <a class="course-picture-link" href="${ctx}/course/show?id=${course.id}">
            <img class="course-picture" src="{{ default_path('coursePicture', course.middlePicture, 'large') }}" alt="${course.title}">
        </a>
        <div class="course-body">
            <div class="course-price-info">
                {% include "TopxiaWebBundle:Course:price-widget.html.twig" with {shows: ['price', 'discount']} %}
            </div>
            <h4 class="course-title"><a href="${ctx}/course/show?id=${course.id}">${course.title}</a>
                {% if course.serializeMode=='serialize' %}
                <span class="label label-success ">更新中</span>
                {% elseif course.serializeMode=='finished' %}
                <span class="label label-warning ">已完结</span>
                {% endif %}
                {% if course.type == 'live' %}
                {% set lesson = course['lesson']|default(null) %}
                {% if lesson and "now"|date("U") >= lesson.startTime and "now"|date("U") <= lesson.endTime %}
                <span class="label label-warning series-mode-label">正在直播中</span>
                {% else %}
                <span class="label label-success series-mode-label">直播</span>
                {% endif %}
                {% endif %}
            </h4>

            {% if course.type == 'live' %}
            {% set lesson = course.lesson|default(null) %}
            {% if lesson %}
            <div class="live-course-lesson mbm">
                <span class="text-success fsm mrm">{{ lesson.startTime|date('n月j日 H:i') }} ~ {{ lesson.endTime|date('H:i') }}</span>
                <span class="text-muted fsm mrm">第{{ lesson.number }}课时</span>
            </div>
            {% endif %}
            {% else %}
            <div class="course-about ellipsis">{{ course.subtitle }}</div>
            {% endif %}

            <div class="course-footer clearfix">

                {% set teacher = course.teachers|first|default(null) %}
                {% if teacher %}
                <div class="teacher">
                    <a href="{{ path('user_show', {id:teacher.id}) }}"><img src="{{ default_path('avatar', teacher.smallAvatar, '') }}" class="teacher-avatar"></a>
                    <a class="teacher-nickname ellipsis" href="{{ path('user_show', {id:teacher.id}) }}">{{ teacher.nickname }}</a>
                    <span class="teacher-title ellipsis">{{ teacher.title }}</span>
                </div>
                {% endif %}
                <div class="course-metas">
                    <span class="stars-{{ (course.rating)|number_format }}">&nbsp;</span>
                    {% if setting('course.show_student_num_enabled', '1') == 1  %}
                    <span class="divider"></span>
                    <span class="text-muted mrm mls"><strong>{{ course.studentNum }}</strong>{{setting('default.user_name', '学员')}}</span>
                    {% endif %}
                </div>
            </div>
        </div>
    </li>
    </#list>
</ul>
</#macro>