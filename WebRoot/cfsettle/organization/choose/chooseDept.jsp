<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
    <tiles:putAttribute name="tool">
        <x:button id="chooseDeptSelect" icon="icon-yes" text="choose"/>
        <x:button id="chooseDeptCancel" icon="icon-no" text="cancel"/>
    </tiles:putAttribute>
    <tiles:putAttribute name="query">
        <form id="chooseDeptQueryForm" class="query_form">
            <input type="hidden" name="searchBean.ownedArea" value="${areaId}">
            <table>
                <tr>
                    <td class="title">营业部名:</td>
                    <td><input id="deptName" name="searchBean.deptName"/></td>
                    <td><x:button id="chooseDeptQuery" icon="icon-search" text="query"/></td>
                </tr>
            </table>
        </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="data">
        <x:datagrid id="chooseDeptDataTable" singleSelect="true" url="/orgDept/orgDeptManage_chooseList.jhtml" autoload="true"
                    form="chooseDeptQueryForm">
            <x:columns>
                <x:column field="id" checkbox="true"/>
                <x:column field="deptName" title="营业部名" width="100"/>
                <x:column field="deptMobile" title="营业部联系方式" width="200"/>
                <x:column field="deptAddr" title="营业部地址" width="200"/>
            </x:columns>
        </x:datagrid>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
            ChooseContext["choose_dept_list"].init = function () {
                $("#deptName").val('');
                chooseDeptDataTable.load();
            };
            chooseDeptDataTable.onDblClickRow = function (rowIndex, rowData) {
                if (ChooseContext["choose_dept_list"].callback && $.isFunction(ChooseContext["choose_dept_list"].callback)) {
                    ChooseContext["choose_dept_list"].callback(rowData);
                }
                ChooseContext["choose_dept_list"].close();
            };
            $("#chooseDeptQuery").unbind().click(function () {
                chooseDeptDataTable.load();
            });
            $("#chooseDeptSelect").unbind().click(function () {
                if (isSingleSelected(chooseDeptDataTable)) {
                    if (ChooseContext["choose_dept_list"].callback && $.isFunction(ChooseContext["choose_dept_list"].callback)) {
                        var row = chooseDeptDataTable.getSelectedFirstRow();
                        ChooseContext["choose_dept_list"].callback(row);
                    }
                    ChooseContext["choose_dept_list"].close();
                }
            });
            $("#chooseDeptCancel").unbind().click(function () {
                if (ChooseContext["choose_dept_list"].callback && $.isFunction(ChooseContext["choose_dept_list"].callback)) {
                    ChooseContext["choose_dept_list"].callback();
                }
                ChooseContext["choose_dept_list"].close();
            });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>