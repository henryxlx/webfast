<@block_title '服务协议'/>

<#include '/layout.ftl'>

<#macro blockContent>

  <div class="es-row-wrap container-gap">
    <div class="row">
      <div class="col-md-12">
        <div class="page-header block-center"><h1>服务协议</h1></div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        ${userTerms!}
      </div>
    </div>

  </div>
</#macro>