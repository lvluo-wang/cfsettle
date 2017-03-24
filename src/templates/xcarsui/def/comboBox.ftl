<span<#rt/>
<#if parameters.id??>
 id="${parameters.id}"<#rt/>
</#if>
 class="combo xcarsui-combox"<#rt/>
><#rt/>
<div style="display:none;" class="config-info"><#rt/>
<#if (parameters.propConfig?? && parameters.propConfig?size > 0)>
<#assign aKeys = parameters.propConfig.keySet() />
<#list aKeys as k>
"${k}":'${parameters.propConfig[k]?html}'<#if k_has_next>,</#if><#rt/>
</#list>
</#if>
</div><#rt/>
<input autocomplete="off" type="text" class="combo-text<#rt/>
<#if (parameters.isValidateBox?? && "true"=parameters.isValidateBox)>
 xcarsui-validatebox<#rt/>
</#if>
"<#rt/>
<#if parameters.initComboxText??>
 value="${parameters.initComboxText?html}"<#rt/>
</#if>
<#if parameters.required??>
 required="${parameters.required}"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle}"<#rt/>
</#if>
<#if (parameters.readonly?? && parameters.readonly='false') >
<#rt/>
<#else>
 readonly="${parameters.readonly?default("readonly")}"<#rt/>
</#if>
<#if (parameters.disabled?? && (parameters.disabled='disabled' || parameters.disabled='true') )>
 disabled="${parameters.disabled}"<#rt/>
</#if>
/><#rt/>
<span><span class="combo-arrow"></span></span><#rt/>
<#if (parameters.multiple?? && parameters.multiple='true')>
<#else>
<input  class="combo-value<#rt/>
<#if parameters.cssClass??>
 ${parameters.cssClass}<#rt/>
</#if>
"<#rt/>
 type="hidden"<#rt/>
<#if parameters.name??>
 name="${parameters.name}"<#rt/>
</#if>
<#if parameters.value??>
 value="${parameters.value}"<#rt/>
</#if>
<#if parameters.disabled??>
 disabled="${parameters.disabled}"<#rt/>
</#if>
/>
</#if>
</span>