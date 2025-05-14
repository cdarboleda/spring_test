<#--  <h1>Login personalizado</h1>
<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=false displayMessage=false; section>
</@layout.registrationLayout>  -->
<#import "template.ftl" as layout>
<#import "field.ftl" as field>
<#import "buttons.ftl" as buttons>
<#import "social-providers.ftl" as identityProviders>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
    <#if section = "header">
        ${msg("loginAccountTitle")}
    <#elseif section = "form">
        <div id="kc-form">
          <div id="kc-form-wrapper">


            <div style="position: relative; display: block; margin-bottom: 1rem;">
                <button id="errorInfoBtn" type="button" style="
                    display: block;
                    margin-left: auto;
                    background-color: #facc15;
                    color: #000;
                    border: none;
                    border-radius: 50%;
                    width: 2rem;
                    height: 2rem;
                    font-weight: bold;
                    cursor: pointer; 
                ">!</button>

                <div id="errorTooltip" style="
                    display: block;
                    margin-left: auto;
                    visibility: hidden;
                    width: 300px;
                    background-color: #333;
                    color: #fff;
                    text-align: left;
                    border-radius: 6px;
                    padding: 10px;
                    position: absolute;
                    z-index: 1;
                    bottom: 125%;
                    left: 50%;
                    opacity: 0;
                    transition: opacity 0.3s;
                ">
                Si se ingresan incorrectamente las credenciales en tres intentos, la cuenta será bloqueada. Se recomienda tener precaución.
                </div>
            </div>

            <script>
                const btn = document.getElementById("errorInfoBtn");
                const tooltip = document.getElementById("errorTooltip");

                btn.addEventListener("mouseenter", function() {
                    const isVisible = tooltip.style.visibility === "visible";
                    tooltip.style.visibility = isVisible ? "hidden" : "visible";
                    tooltip.style.opacity = isVisible ? "0" : "1";
                });

                // Oculta el tooltip si se hace clic fuera
                document.addEventListener("mouseover", function(e) {
                    if (!btn.contains(e.target)) {
                        tooltip.style.visibility = "hidden";
                        tooltip.style.opacity = "0";
                    }
                });
            </script>


          


            <#if realm.password>
                <form id="kc-form-login" class="${properties.kcFormClass!}" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post" novalidate="novalidate">
                    <#if !usernameHidden??>
                        <#assign label>
                            <#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if>
                        </#assign>
                        <@field.input name="username" label=label error=kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc autofocus=true autocomplete="username" value=login.username!'' />
                        <@field.password name="password" label=msg("password") error="" forgotPassword=realm.resetPasswordAllowed autofocus=usernameHidden?? autocomplete="current-password">
                            <#if realm.rememberMe && !usernameHidden??>
                                <@field.checkbox name="rememberMe" label=msg("rememberMe") value=login.rememberMe?? />
                            </#if>
                        </@field.password>
                    <#else>
                        <@field.password name="password" label=msg("password") forgotPassword=realm.resetPasswordAllowed autofocus=usernameHidden?? autocomplete="current-password">
                            <#if realm.rememberMe && !usernameHidden??>
                                <@field.checkbox name="rememberMe" label=msg("rememberMe") value=login.rememberMe?? />
                            </#if>
                        </@field.password>
                    </#if>

                    <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                    <@buttons.loginButton />
                </form>
            </#if>
            </div>
        </div>
    <#elseif section = "socialProviders" >
        <#if realm.password && social.providers?? && social.providers?has_content>
            <@identityProviders.show social=social/>
        </#if>
    <#elseif section = "info" >
        <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
            <div id="kc-registration-container">
                <div id="kc-registration">
                    <span>${msg("noAccount")} <a href="${url.registrationUrl}">${msg("doRegister")}</a></span>
                </div>
            </div>
        </#if>
    </#if>

</@layout.registrationLayout>
