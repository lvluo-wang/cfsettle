<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/xcars-tags" %>
<%
    request.setAttribute("now", new java.util.Date());
%>
<tiles:insertDefinition name="WIN_FORM_BUTTON">
    <tiles:putAttribute name="form">
        <form class="busi_form" id="prj_form">
        <input type="hidden" name="prj.id" value="${prj.id}"/>
            <table>
                <tr>
                    <td>项目成立时间:</td>
                    <td>
                        <input name="prj.buildTime" class="Wdate easyui-validatebox" required="true"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                    </td>
                </tr>
            </table>
        </form>

    </tiles:putAttribute>

    <tiles:putAttribute name="button">
        <x:button id="save" iconCls="icon-save" text="save" click="doBuild" effect="round" />
        <x:button iconCls="icon-cancel" text="cancel" click="doAddCancel" effect="round" />
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
            function doBuild() {
                if ($("#prj_form").form("validate")) {
                    var url = "<s:url value='/prj/prjAudit_doBuild.jhtml'/>";
                    doPost(url, formToObject("prj_form"),
                        function(result) {
                            if (!printError(result)) {
                                info("提交成功");
                                closeWindow("project_build_win");
                                dataTable.refresh();
                            }
                        });
                }
            }

            function doAddCancel() {
                closeWindow("project_build_win");
                dataTable.refresh();
            }

        </script>
    </tiles:putAttribute>

</tiles:insertDefinition>