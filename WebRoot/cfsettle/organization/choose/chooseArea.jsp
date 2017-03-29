<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="/WEB-INF/xcars-tags.tld" %>
<tiles:insertDefinition name="WIN_TOOL_QUERY_DATA">
    <tiles:putAttribute name="tool">
        <x:button id="chooseMetaSelect" icon="icon-yes" text="choose"/>
        <x:button id="chooseMetaCancel" icon="icon-no" text="cancel"/>
    </tiles:putAttribute>
    <tiles:putAttribute name="query">
        <form id="chooseAreaQueryForm" class="query_form">
            <table>
                <tr>
                    <td class="title">区域:</td>
                    <td><input id="areaName" name="searchBean.areaName"/></td>
                    <input type="hidden" name="searchBean.status" value="1"/>
                    <td><x:button id="chooseMetaQuery" icon="icon-search" text="query"/></td>
                </tr>
            </table>
        </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="data">
        <x:datagrid id="chooseAreaDataTable" singleSelect="true" url="/orgArea/orgAreaManage_list.jhtml" autoload="true"
                    form="chooseAreaQueryForm">
            <x:columns>
                <x:column field="id" checkbox="true"/>
                <x:column field="areaName" title="区域名" width="100"/>
                <x:column field="overRange" title="管辖范围" width="200"/>
            </x:columns>
        </x:datagrid>
    </tiles:putAttribute>
    <tiles:putAttribute name="end">
        <script type="text/javascript">
            ChooseContext["choose_area_list"].init = function () {
                $("#areaName").val('');
                chooseAreaDataTable.load();
            };
            chooseAreaDataTable.onDblClickRow = function (rowIndex, rowData) {
                if (ChooseContext["choose_area_list"].callback && $.isFunction(ChooseContext["choose_area_list"].callback)) {
                    ChooseContext["choose_area_list"].callback(rowData);
                }
                ChooseContext["choose_area_list"].close();
            };
            $("#chooseMetaQuery").unbind().click(function () {
                chooseAreaDataTable.load();
            });
            $("#chooseMetaSelect").unbind().click(function () {
                if (isSingleSelected(chooseAreaDataTable)) {
                    if (ChooseContext["choose_area_list"].callback && $.isFunction(ChooseContext["choose_area_list"].callback)) {
                        var row = chooseAreaDataTable.getSelectedFirstRow();
                        ChooseContext["choose_area_list"].callback(row);
                    }
                    ChooseContext["choose_area_list"].close();
                }
            });
            $("#chooseMetaCancel").unbind().click(function () {
                if (ChooseContext["choose_area_list"].callback && $.isFunction(ChooseContext["choose_area_list"].callback)) {
                    ChooseContext["choose_area_list"].callback();
                }
                ChooseContext["choose_area_list"].close();
            });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>