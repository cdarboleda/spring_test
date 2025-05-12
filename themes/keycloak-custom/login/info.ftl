<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section> 
    <#if section = "header">
    <div class="">
        <#if requiredActions?? && requiredActions?size == 1 && requiredActions[0] == "UPDATE_PASSWORD">
            <h1 class="">${kcSanitize(msg("requiredAction.UPDATE_PASSWORD"))?no_esc}</h1>
        <#else>
            <#if messageHeader??>
                ${kcSanitize(msg("${messageHeader}"))?no_esc}
                ${message.summary}
            </#if>
        </#if> 
    </div>

            
    <#elseif section = "form">
    <div id="kc-info-message">
        <p class="instruction">${message.summary}<#if requiredActions??><#list requiredActions>: <b><#items as reqActionItem>${kcSanitize(msg("requiredAction.${reqActionItem}"))?no_esc}<#sep>, </#items></b></#list><#else></#if></p>
        <#if skipLink??>
        <#else>
            <#if pageRedirectUri?has_content>
                <p><a href="${pageRedirectUri}">${kcSanitize(msg("backToApplication"))?no_esc}</a></p>
            <#elseif actionUri?has_content>
                <p><a href="${actionUri}">${kcSanitize(msg("proceedWithAction"))?no_esc}</a></p>
            <#elseif (client.baseUrl)?has_content>
                <p><a href="${client.baseUrl}">${kcSanitize(msg("backToApplication"))?no_esc}</a></p>
            </#if>
        </#if>
    </div>
    <p><a href="https://posgrado-app.jollybush-eb2fdd97.westus.azurecontainerapps.io">${kcSanitize(msg("backToApplication"))?no_esc}</a></p>
    </#if>
       

</@layout.registrationLayout>