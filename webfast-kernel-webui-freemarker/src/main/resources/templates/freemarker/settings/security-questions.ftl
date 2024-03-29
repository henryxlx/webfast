<#assign side_nav = 'security'/>
<#assign script_controller = 'settings/security-questions'/>

<@block_title '安全问题'/>

<#include '/settings/layout.ftl'/>

<#macro blockMain>
  <#local class='panel-col'/>
  <#include '/bootstrap/panel.ftl'/>
</#macro>

<#macro blockHeading>安全设置</#macro>
<#macro blockPanelBody>
  <#local questionOptions = dict['secureQuestion']!>

    <@web_macro.flash_messages />

  <#if !hasSecurityQuestions>

    <ul class="breadcrumb">
      <li><a href="${ctx}/settings/security">安全设置</a></li>
      <li class="active">设置安全问题</li>
    </ul>

    <div class="alert alert-warning"> <span class="glyphicon glyphicon-info-sign"></span> 安全问题一经设置，不可再次修改。</div>

    <form id="settings-security-questions-form" class="form-horizontal" autocomplete="off" method="post" {# {% if not app.user.password %} style="display:none;"{% endif %} #}> 
      
      <input style="display:none">{# Prevent autocompleting #}
      <input type="password" style="display:none">

      <div class="form-group row"> 
        <div  class="col-md-3 control-label"><label for="question-1">安全问题1</label></div> 
        <div class="col-md-4">
          <select class="form-control" id="question-1" name="question-1"> 
            <@select_options questionOptions question1!'parents' />
          </select> 
        </div> 
      </div> 
      <div class="form-group row"> 
        <div  class="col-md-3 control-label"><label for="answer-1">答案</label></div> 
        <div class="col-md-4 controls"> 
          <input type="text" id="answer-1" name="answer-1" class="form-control" autocomplete="off" value="" placeholder="安全问题1答案"> 
        </div> 
      </div> 



      <div class="form-group row"> 
        <div class="col-md-3 control-label"><label for="question-2">安全问题2</label></div> 
        <div class="col-md-4"> 
          <select class="form-control " id="question-2" name="question-2"> 
            <@select_options questionOptions question2!'teacher' />
          </select> 
        </div> 
      </div> 

      <div class="form-group row"> 
        <div  class="col-md-3 control-label"><label for="answer-2">答案</label></div> 
        <div class="col-md-4 controls"> 
          <input type="text" id="answer-2" name="answer-2" class="form-control" autocomplete="off"  value="" placeholder="安全问题2答案"> 
        </div> 
      </div> 

      <div class="form-group row"> 
        <div  class="col-md-3 control-label"><label for="question-3">安全问题3</label></div> 
        <div class="col-md-4"> 
          <select class="form-control" id="question-3" name="question-3"> 
            <@select_options questionOptions question3!'lover' />
          </select> 
        </div> 
      </div> 
    
      <div class="form-group row"> 
        <div  class="col-md-3 control-label"><label for="answer-3">答案</label></div> 
        <div class="col-md-4 controls"> 
          <input type="text" id="answer-3" name="answer-3" class="form-control" autocomplete="off" value="" placeholder="安全问题3答案"> 
        </div> 
      </div> 

      <div class="form-group row"> 
        <div  class="col-md-3 control-label"><label for="userLoginPassword">您的登陆密码</label></div> 
        <div class="col-md-4 controls"> 
          <input type="password" id="userLoginPassword" name="userLoginPassword" class="form-control" autocomplete="off" value=""> 
        </div> 
      </div> 

      <br>
      <div class="form-group row"> 
          <div class="col-md-4 col-md-offset-3  controls"> 
            <button id="password-save-btn" data-submiting-text="正在保存" type="submit" class="btn btn-primary {# pull-right #}">提交</button> 
          </div> 
        </div> 

        <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
 
    </form> 

  <#else>

    <ul class="breadcrumb">
      <li><a href="${ctx}/settings/security">安全设置</a></li>
      <li class="active">查看安全问题</li>
    </ul>


    <form class="form-horizontal" role="form">
      <div class="form-group row">
        <label  class="col-md-3 control-label">安全问题1</label>
        <p class="form-control-static">${questionOptions[question1]}</p>
      </div>
      <div class="form-group row"> 
        <label  class="col-md-3 control-label">安全问题2</label> 
        <p class="form-control-static">${questionOptions[question2]}</p>
      </div>  
      <div class="form-group row"> 
        <label  class="col-md-3 control-label">安全问题3</label> 
        <p class="form-control-static">${questionOptions[question3]}</p>
      </div>
    </form>
  </#if>
</#macro>