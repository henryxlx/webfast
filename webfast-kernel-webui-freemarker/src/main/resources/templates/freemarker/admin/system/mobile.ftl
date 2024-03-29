<#assign menu = 'client'/>
<#assign script_controller = 'setting/mobile'/>

<@block_title '移动客户端设置'/>

<#include '/admin/system/layout.ftl'/>

<#macro blockMain>
    <style>
        #mobile-logo-container img,
        #mobile-splash1-container img,
        #mobile-splash2-container img,
        #mobile-splash3-container img,
        #mobile-splash4-container img,
#mobile-splash5-container img ,
#mobile-banner1-container img , 
#mobile-banner2-container img , 
#mobile-banner3-container img , 
#mobile-banner4-container img , 
#mobile-banner5-container img 
{max-width: 80%; margin-bottom: 10px;}

.course-grids {
  margin:0 -15px 0 0;
  padding:0;
  list-style: none;
}

.course-grid {
  display: inline-block;
  vertical-align: top;
  margin: 15px 15px 15px 0;
  border: 1px solid #e1e1e1;
  border-radius: 4px;
}

.banner-course .course-grid {
  margin: 0 0 0 0;
  margin-left: 10px;
}

.course-grid .series-mode-label {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 12px;
}

.course-grid .grid-body {
  position: relative;
  width: 170px;
  display: block;
  overflow: hidden;
  border-radius: 4px;
  color: #353535;
}

.grid-body a{
  text-decoration: none;
}

.course-grid .title {
  display: block;
  padding: 10px;
  min-height: 52px;
  color: #555;
  font-weight: bold;
}

.course-grid .add-course {
  font-size: 80px;
  height: 148px;
  text-align: center;
  padding-top: 30px;
}

</style>

<div class="page-header"><h1>移动客户端设置</h1></div>

<@web_macro.flash_messages />

<form class="form-horizontal" id="mobile-form" method="post">

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label >是否开启客户端</label>
    </div>
    <div class="controls col-md-8 radios">
      <#local mobileEnabled = (mobile.enabled)! />
      <@radios 'enabled' {'1':'开启', '0':'关闭'}  mobileEnabled! />
      <div class="help-block">开启后，网站首页顶部导航会出现客户端入口</div>
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="mobile_about">系统简介</label>
    </div>
    <div class="controls col-md-8">
      <textarea class="form-control" id="mobile_about" rows="10" name="about" data-image-upload-url="{{ path('editor_upload', {token:upload_token('default')}) }}">${(mobile.about)!}</textarea>
      <div class="help-block">此简介将显示在移动客户端的"关于系统"</div>
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="logo">系统LOGO</label>
    </div>
    <div class="col-md-8 controls">
      <div id="mobile-logo-container"><#if (mobile.logo)!false><img src="{{ asset(mobile.logo) }}"></#if></div>
      <button class="btn btn-default btn-sm" id="mobile-logo-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'logo'}) }}">上传</button>
      <button class="btn btn-default btn-sm" id="mobile-logo-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'logo'}) }}" <#if !(mobile.logo)!false>style="display:none;"</#if>>删除</button>
      <p class="help-block">请上传png, gif, jpg格式的图片文件。LOGO图片的高度建议不要超过50px。</p>
      <input type="hidden" name="logo" value="${(mobile.logo)!}">
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="logo">公告</label>
    </div>
    <div class="col-md-8 controls">
      <input type="text" class="form-control" name="notice" value="${(mobile.notice)!}">
      <p class="help-block">将会在手机端banner下面显示系统的公告，建议用简练概括的语言描述，字数在20字以下。</p>
    </div>
  </div>

  <fieldset>
    <legend>banner设置</legend>
    
    
    <div class="help-block">客户端显示的轮播图。最多5张，建议图片大小为640*330，格式支持jpg、png。</div>
    <div class="form-group">
      <div class="col-md-2 control-label">
        <label for="banner1">轮播图1</label>
      </div>
      <div class="col-md-8 controls">
        <div id="mobile-banner1-container"><#if (mobile.banner1)!false><img src="{{ asset(mobile.banner1) }}"></#if></div>

        <button class="btn btn-default btn-sm" id="mobile-banner1-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'banner1'}) }}">上传</button>
        <button class="btn btn-default btn-sm" id="mobile-banner1-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'banner1'}) }}" <#if !(mobile.banner1)!false>style="display:none;"</#if>>删除</button>

        <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>







        <div class="banner-setting" role="banner1-setting" <#if !(mobile.banner1)!false>style="display:none;"</#if>>
          <input type="radio" role="bannerClick1" name="bannerClick1" <#if (mobile.bannerClick1)??>checked="checked"</#if> value="0"/>默认（无触发动作）
          <input type="radio" role="bannerClick1" name="bannerClick1" value="1" <#if (mobile.bannerClick1)!'' == "1">checked="checked"</#if>/>跳转到连接地址

          <input type="radio" role="bannerClick1" name="bannerClick1" value="2" <#if (mobile.bannerClick1)!'' == "2">checked="checked"</#if>/>跳转到内部课程

          <div class="row">
            <div class="col-xs-11">
              <input type="text" id="bannerUrl1" name="bannerUrl1" class="form-control" value="${(mobile.bannerUrl1)!}" placeholder="请填写连接地址"  <#if (mobile.bannerClick1)!'' != "1">style="display:none"</#if>/>
            </div>  
          </div>

          <div class="row" id="selectBannerCourse1" data-role="selectBannerCourse" <#if (mobile.bannerClick1)!'' != "2">style="display:none"</#if>>
            <a data-role="selectCourse" class="btn btn-sm btn-primary pull-left" data-toggle="modal" data-target="#modal" data-url="{{path('admin_course_search_to_fill_banner')}}" >
             选课
            </a>
            <div name="bannerCourseShow1">
              <ul class="banner-course" role="bannerCourse">
                {% include 'TopxiaAdminBundle:Course:course-item.html.twig' with {course:bannerCourse1} %}
              </ul>
            </div>
            <input type="text" name="bannerJumpToCourseId1" class="form-control" value="{{mobile.bannerJumpToCourseId1}}" placeholder="请填写内部课程Id" style="display:none;"/>
          </div>
        </div>

        
        <input type="hidden" name="banner1" value="${(mobile.banner1)!}">
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-2 control-label">
        <label for="banner2">轮播图2</label>
      </div>
      <div class="col-md-8 controls">
        <div id="mobile-banner2-container"><#if (mobile.banner2)??><img src="{{ asset(mobile.banner2) }}"></#if></div>

        <button class="btn btn-default btn-sm" id="mobile-banner2-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'banner2'}) }}">上传</button>
        <button class="btn btn-default btn-sm" id="mobile-banner2-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'banner2'}) }}" <#if !(mobile.banner2)??>style="display:none;"</#if>>删除</button>

        <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>







        <div class="banner-setting" role="banner2-setting" <#if !(mobile.banner2)??>style="display:none;"</#if>>
          <input type="radio" role="bannerClick2" name="bannerClick2" <#if !(mobile.bannerClick2)??>checked="checked"</#if> value="0"/>默认（无触发动作）
          <input type="radio" role="bannerClick2" name="bannerClick2" value="1" <#if (mobile.bannerClick2)!'' == "1">checked="checked"</#if>/>跳转到连接地址

          <input type="radio" role="bannerClick2" name="bannerClick2" value="2" <#if (mobile.bannerClick2)!'' == "2">checked="checked"</#if>/>跳转到内部课程

          <div class="row">
            <div class="col-xs-11">
              <input type="text" id="bannerUrl2" name="bannerUrl2" class="form-control" value="{{mobile.bannerUrl2}}" placeholder="请填写连接地址" <#if (mobile.bannerClick2)!'' != "1">style="display:none"</#if>/>
            </div>  
          </div>

          <div class="row" id="selectBannerCourse2" data-role="selectBannerCourse" <#if (mobile.bannerClick2)!'' != "2">style="display:none"</#if>>
            <a data-role="selectCourse" class="btn btn-sm btn-primary pull-left" data-toggle="modal" data-target="#modal" data-url="{{path('admin_course_search_to_fill_banner')}}" >
             选课
            </a>
            <div name="bannerCourseShow2">
              <ul class="banner-course" role="bannerCourse">
                {% include 'TopxiaAdminBundle:Course:course-item.html.twig' with {course:bannerCourse2} %}
              </ul>
            </div>

            <input type="text" name="bannerJumpToCourseId2" class="form-control" value="{{mobile.bannerJumpToCourseId2}}" placeholder="请填写内部课程Id" style="display:none;"/>
          </div>
        </div>

        
        <input type="hidden" name="banner2" value="{{ mobile.banner2 }}">
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-2 control-label">
        <label for="banner3">轮播图3</label>
      </div>
      <div class="col-md-8 controls">
        <div id="mobile-banner3-container"><#if (mobile.banner3)??><img src="{{ asset(mobile.banner3) }}"></#if></div>

        <button class="btn btn-default btn-sm" id="mobile-banner3-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'banner3'}) }}">上传</button>
        <button class="btn btn-default btn-sm" id="mobile-banner3-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'banner3'}) }}" <#if !(mobile.banner3)??>style="display:none;"</#if>>删除</button>

        <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>







        <div class="banner-setting" role="banner3-setting" <#if !(mobile.banner3)??>style="display:none;"</#if>>
          <input type="radio" role="bannerClick3" name="bannerClick3" <#if !(mobile.bannerClick3)??>checked="checked"</#if> value="0"/>默认（无触发动作）
          <input type="radio" role="bannerClick3" name="bannerClick3" value="1" <#if (mobile.bannerClick3)!'' == "1">checked="checked"</#if>/>跳转到连接地址

          <input type="radio" role="bannerClick3" name="bannerClick3" value="2" <#if (mobile.bannerClick3)!'' == "2">checked="checked"</#if>/>跳转到内部课程

          <div class="row">
            <div class="col-xs-11">
              <input type="text" id="bannerUrl3" name="bannerUrl3" class="form-control" value="{{mobile.bannerUrl3}}" placeholder="请填写连接地址" <#if (mobile.bannerClick3)!'' != "1">style="display:none"</#if>/>
            </div>  
          </div>

          <div class="row" id="selectBannerCourse3" data-role="selectBannerCourse" <#if (mobile.bannerClick3)!'' != "2">style="display:none"</#if>>
            <a data-role="selectCourse" class="btn btn-sm btn-primary pull-left" data-toggle="modal" data-target="#modal" data-url="{{path('admin_course_search_to_fill_banner')}}" >
             选课
            </a>
            <div name="bannerCourseShow3">
              <ul class="banner-course" role="bannerCourse">
                {% include 'TopxiaAdminBundle:Course:course-item.html.twig' with {course:bannerCourse3} %}
              </ul>
            </div>
            <input type="text" name="bannerJumpToCourseId3" class="form-control" value="{{mobile.bannerJumpToCourseId3}}" placeholder="请填写内部课程Id" style="display:none;"/>
          </div>
        </div>

        
        <input type="hidden" name="banner3" value="{{ mobile.banner3 }}">
      </div>
    </div>


    <div class="form-group">
      <div class="col-md-2 control-label">
        <label for="banner4">轮播图4</label>
      </div>
      <div class="col-md-8 controls">
        <div id="mobile-banner4-container"><#if (mobile.banner4)??><img src="{{ asset(mobile.banner4) }}"></#if></div>

        <button class="btn btn-default btn-sm" id="mobile-banner4-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'banner4'}) }}">上传</button>
        <button class="btn btn-default btn-sm" id="mobile-banner4-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'banner4'}) }}" <#if !(mobile.banner4)??>style="display:none;"</#if>>删除</button>

        <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>







        <div class="banner-setting" role="banner4-setting" <#if !(mobile.banner4)??>style="display:none;"</#if>>
          <input type="radio" role="bannerClick4" name="bannerClick4" <#if !(mobile.bannerClick4)??>checked="checked"</#if> value="0"/>默认（无触发动作）
          <input type="radio" role="bannerClick4" name="bannerClick4" value="1" <#if (mobile.bannerClick4)!'' == "1">checked="checked"</#if>/>跳转到连接地址

          <input type="radio" role="bannerClick4" name="bannerClick4" value="2" <#if (mobile.bannerClick4)!'' == "2">checked="checked"</#if>/>跳转到内部课程

          <div class="row">
            <div class="col-xs-11">
              <input type="text" id="bannerUrl4" name="bannerUrl4" class="form-control" value="{{mobile.bannerUrl4}}" placeholder="请填写连接地址" <#if (mobile.bannerClick4)!'' != "1">style="display:none"</#if>/>
            </div>  
          </div>

          <div class="row" id="selectBannerCourse4" data-role="selectBannerCourse" <#if (mobile.bannerClick4)!'' != "2">style="display:none"</#if>>
            <a data-role="selectCourse" class="btn btn-sm btn-primary pull-left" data-toggle="modal" data-target="#modal" data-url="{{path('admin_course_search_to_fill_banner')}}" >
             选课
            </a>
            <div name="bannerCourseShow4">
              <ul class="banner-course" role="bannerCourse">
                {% include 'TopxiaAdminBundle:Course:course-item.html.twig' with {course:bannerCourse4} %}
              </ul>
            </div>
            <input type="text" name="bannerJumpToCourseId4" class="form-control" value="{{mobile.bannerJumpToCourseId4}}" placeholder="请填写内部课程Id" style="display:none;"/>
          </div>
        </div>

        
        <input type="hidden" name="banner4" value="{{ mobile.banner4 }}">
      </div>
    </div>


    <div class="form-group">
      <div class="col-md-2 control-label">
        <label for="banner5">轮播图5</label>
      </div>
      <div class="col-md-8 controls">
        <div id="mobile-banner5-container"><#if (mobile.banner5)??><img src="{{ asset(mobile.banner5) }}"></#if></div>

        <button class="btn btn-default btn-sm" id="mobile-banner5-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'banner5'}) }}">上传</button>
        <button class="btn btn-default btn-sm" id="mobile-banner5-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'banner5'}) }}" <#if !(mobile.banner5)??>style="display:none;"</#if>>删除</button>

        <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>







        <div class="banner-setting" role="banner5-setting" <#if !(mobile.banner5)??>style="display:none;"</#if>>
          <input type="radio" role="bannerClick5" name="bannerClick5" <#if !(mobile.bannerClick5)??>checked="checked"</#if> value="0"/>默认（无触发动作）
          <input type="radio" role="bannerClick5" name="bannerClick5" value="1" <#if (mobile.bannerClick5)!'' == "1">checked="checked"</#if>/>跳转到连接地址

          <input type="radio" role="bannerClick5" name="bannerClick5" value="2" <#if (mobile.bannerClick5)!'' == "2">checked="checked"</#if>/>跳转到内部课程

          <div class="row">
            <div class="col-xs-11">
              <input type="text" id="bannerUrl5" name="bannerUrl5" class="form-control" value="{{mobile.bannerUrl5}}" placeholder="请填写连接地址" <#if (mobile.bannerClick5)!'' != "1">style="display:none"</#if>/>
            </div>  
          </div>

          <div class="row" id="selectBannerCourse5" data-role="selectBannerCourse" <#if (mobile.bannerClick5)!'' != "2">style="display:none"</#if>>
            <a data-role="selectCourse" class="btn btn-sm btn-primary pull-left" data-toggle="modal" data-target="#modal" data-url="{{path('admin_course_search_to_fill_banner')}}" >
             选课
            </a>
            <div name="bannerCourseShow5">
              <ul class="banner-course" role="bannerCourse">
                {% include 'TopxiaAdminBundle:Course:course-item.html.twig' with {course:bannerCourse5} %}
              </ul>
            </div>
            <input type="text" name="bannerJumpToCourseId5" class="form-control" value="{{mobile.bannerJumpToCourseId5}}" placeholder="请填写内部课程Id" style="display:none;"/>
          </div>
        </div>

        
        <input type="hidden" name="banner5" value="{{ mobile.banner5 }}">
      </div>
    </div>

    
  </fieldset>


  <fieldset>
  <legend>设置启动图</legend>
  <div class="help-block">最多允许设置5张启动图(尺寸为640*960)，用户首次登录系统时会显示启动图。</div>
  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="splash1">启动图1</label>
    </div>
    <div class="col-md-8 controls">
      <div id="mobile-splash1-container"><#if (mobile.splash1)??><img src="{{ asset(mobile.splash1) }}"></#if></div>
      <button class="btn btn-default btn-sm" id="mobile-splash1-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'splash1'}) }}">上传</button>
      <button class="btn btn-default btn-sm" id="mobile-splash1-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'splash1'}) }}" <#if !(mobile.splash1)??>style="display:none;"</#if>>删除</button>
      <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>
      <input type="hidden" name="splash1" value="{{ mobile.splash1 }}">
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="splash2">启动图2</label>
    </div>
    <div class="col-md-8 controls">
      <div id="mobile-splash2-container"><#if (mobile.splash2)??><img src="{{ asset(mobile.splash2) }}"></#if></div>
      <button class="btn btn-default btn-sm" id="mobile-splash2-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'splash2'}) }}">上传</button>
      <button class="btn btn-default btn-sm" id="mobile-splash2-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'splash2'}) }}" <#if !(mobile.splash2)??>style="display:none;"</#if>>删除</button>
      <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>
      <input type="hidden" name="splash2" value="{{ mobile.splash2 }}">
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="splash3">启动图3</label>
    </div>
    <div class="col-md-8 controls">
      <div id="mobile-splash3-container"><#if (mobile.splash3)??><img src="{{ asset(mobile.splash3) }}"></#if></div>
      <button class="btn btn-default btn-sm" id="mobile-splash3-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'splash3'}) }}">上传</button>
      <button class="btn btn-default btn-sm" id="mobile-splash3-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'splash3'}) }}" <#if !(mobile.splash3)??>style="display:none;"</#if>>删除</button>
      <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>
      <input type="hidden" name="splash3" value="{{ mobile.splash3 }}">
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="splash4">启动图4</label>
    </div>
    <div class="col-md-8 controls">
      <div id="mobile-splash4-container"><#if (mobile.splash4)??><img src="{{ asset(mobile.splash4) }}"></#if></div>
      <button class="btn btn-default btn-sm" id="mobile-splash4-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'splash4'}) }}">上传</button>
      <button class="btn btn-default btn-sm" id="mobile-splash4-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'splash4'}) }}" <#if !(mobile.splash4)??>style="display:none;"</#if>>删除</button>
      <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>
      <input type="hidden" name="splash4" value="{{ mobile.splash4 }}">
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-2 control-label">
      <label for="splash5">启动图5</label>
    </div>
    <div class="col-md-8 controls">
      <div id="mobile-splash5-container"><#if (mobile.splash5)??><img src="{{ asset(mobile.splash5) }}"></#if></div>
      <button class="btn btn-default btn-sm" id="mobile-splash5-upload" type="button" data-url="{{ path('admin_setting_mobile_picture_upload', {type:'splash5'}) }}">上传</button>
      <button class="btn btn-default btn-sm" id="mobile-splash5-remove" type="button" data-url="{{ path('admin_setting_mobile_picture_remove', {type:'splash5'}) }}" <#if !(mobile.splash5)??>style="display:none;"</#if>>删除</button>
      <p class="help-block">请上传png, gif, jpg格式的图片文件。</p>
      <input type="hidden" name="splash5" value="{{ mobile.splash5 }}">
    </div>
  </div>

  </fieldset>


  <fieldset>
    <legend>每周精选课程</legend>
    <div class="help-block">所选择的课程将会在客户端【每周精选】栏目中显示，最多选择三门。<br>
    客户端【每周精选】栏目中默认显示的是网站中<#if setting('default.user_name')??>{{setting('default.user_name')|default('学员')}}<#else>学员</#if>数最多的课程，最多显示三门。
    </div>
    <div class="form-group">
      <input type='hidden' name="courseIds" value="{{mobile.courseIds}}"/>
      <div class="col-md-12" role="course-item-container">
        <ul class="course-grids">
          <#list courses! as course>
          {% include 'TopxiaAdminBundle:Course:course-item.html.twig' with {course:course, showDelBtn:true} %}
          </#list>
          <li class="course-grid related-course-grid" role="add-course" 
          style="cursor:pointer
          <#if courses?? && courses?size >= 3>
          ;display:none
          </#if>
          "
          data-backdrop="static" data-toggle="modal" data-target="#modal" data-url="{{path('admin_course_search')}}">
            <div class="add-course grid-body glyphicon glyphicon-plus">
              
            </div>
          </li>
        </ul>
      </div>
    </div>
  </fieldset>

  <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
  
  <div class="row form-group">
    <div class="controls col-md-offset-2 col-md-8">
      <button type="submit" class="btn btn-primary">提交</button>
    </div>
  </div>

</form>

</#macro>