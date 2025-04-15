<#outputformat "plainText">
<#assign requiredActionsText><#if requiredActions??><#list requiredActions><#items as reqActionItem>${msg("requiredAction.${reqActionItem}")}<#sep>, </#sep></#items></#list></#if></#assign>
</#outputformat>

<#import "template.ftl" as layout>
<#import "footerEmail.ftl" as footerEmail>
<#import "executeActionsUpdatePasswordPagoDocenteBody.ftl" as updatePasswordPagoDocenteBody>
<@layout.emailLayout>
<#-- Verificamos si la única acción requerida es UPDATE_PASSWORD -->
    <#if requiredActions?? && requiredActions?size == 1 && requiredActions[0] == "UPDATE_PASSWORD">
        <@updatePasswordPagoDocenteBody.updatePasswordPagoDocenteBody />
    <#else>
        ${kcSanitize(msg("executeActionsBodyHtml", link, linkExpiration, realmName, requiredActionsText, linkExpirationFormatter(linkExpiration)))?no_esc}
    </#if>
    <@footerEmail.footerEmail />
</@layout.emailLayout>
