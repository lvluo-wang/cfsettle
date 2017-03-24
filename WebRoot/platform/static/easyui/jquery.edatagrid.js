/**
 * edatagrid - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2011 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   datagrid
 *   messager
 * 
 */
(function($){
	function buildGrid(target){
		var opts = $.data(target, 'edatagrid').options;
		$(target).datagrid($.extend({}, opts, {
			onDblClickCell:function(index,field){
				if (opts.editing){
					$(this).edatagrid('editRow', index);
					var row = $(this).datagrid('getSelected');
					opts.onEdit.call(target, index, row);
					focusEditor(field);
				}
				//$(this).edatagrid("selectRow",index);
			},
			onClickCell:function(index,field){
				if (opts.editing && opts.editIndex >= 0){
					$(this).edatagrid('editRow', index);
					var row = $(this).datagrid('getSelected');
					opts.onEdit.call(target, index, row);
					focusEditor(field);
				}
				//$(this).edatagrid("selectRow",index);
			},
			onAfterEdit: function(index, row){
				opts.editIndex = undefined;
				var url = row.isNewRecord ? opts.saveUrl : opts.updateUrl;
				var param = row.isNewRecord ? opts.saveParams.call(target,index,row) : opts.updateParams.call(target,index,row);
				if (url){
					//alert(param);
					doPost(url, str2obj(param), function(data){
						//alert(data);
						//data.isNewRecord = null;
						$.extend(data,{"isNewRecord":null});
						$(target).datagrid('updateRow', {
							index: index,
							row: data
						});
						if (opts.tree){
							var t = $(opts.tree);
							var node = t.tree('find', row.id);
							if (node){
								node.text = row[opts.treeTextField];
								t.tree('update', node);
							} else {
								var pnode = t.tree('find', row[opts.treeParentField]);
								t.tree('append', {
									parent: (pnode ? pnode.target : null),
									data: [{id:row.id,text:row[opts.treeTextField]}]
								});
							}
						}
						opts.onSave.call(target, index, row);
					});
				}
				if (opts.onAfterEdit) opts.onAfterEdit.call(target, index, row);
			},
			onCancelEdit: function(index, row){
				opts.editIndex = undefined;
				if (row.isNewRecord) {
					$(this).datagrid('deleteRow', index);
				}
				if (opts.onCancelEdit) opts.onCancelEdit.call(target, index, row);
			},
			onBeforeLoad: function(param){
				$(this).datagrid('rejectChanges');
				if (opts.tree){
					var node = $(opts.tree).tree('getSelected');
					param[opts.treeParentField] = node ? node.id : undefined;
				}
				if (opts.onBeforeLoad) opts.onBeforeLoad.call(target, param);
			}
		}));
		
		function focusEditor(field){
			var editor = $(target).datagrid('getEditor', {index:opts.editIndex,field:field});
			if (editor){
				editor.target.focus();
			} else {
				var editors = $(target).datagrid('getEditors', opts.editIndex);
				if (editors.length){
					editors[0].target.focus();
				}
			}
		}
		
		if (opts.tree){
			$(opts.tree).tree({
				url: opts.treeUrl,
				onClick: function(node){
					$(target).datagrid('load');
				},
				onDrop: function(dest,source,point){
					var targetId = $(this).tree('getNode', dest).id;
					$.ajax({
						url: opts.treeDndUrl,
						type:'post',
						data:{
							id:source.id,
							targetId:targetId,
							point:point
						},
						dataType:'json',
						success:function(){
							$(target).datagrid('load');
						}
					});
				}
			});
		}
	}
	
	$.fn.edatagrid = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.edatagrid.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.datagrid(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'edatagrid');
			if (state){
				$.extend(state.options, options);
			} else {
				$.data(this, 'edatagrid', {
					options: $.extend({}, $.fn.edatagrid.defaults, $.fn.edatagrid.parseOptions(this), options)
				});
			}
			buildGrid(this);
		});
	};
	
	$.fn.edatagrid.parseOptions = function(target){
		return $.extend({}, $.fn.datagrid.parseOptions(target), {
		});
	};
	
	$.fn.edatagrid.methods = {
		options: function(jq){
			var opts = $.data(jq[0], 'edatagrid').options;
			return opts;
		},
		enableEditing: function(jq){
			return jq.each(function(){
				var opts = $.data(this, 'edatagrid').options;
				opts.editing = true;
			});
		},
		disableEditing: function(jq){
			return jq.each(function(){
				var opts = $.data(this, 'edatagrid').options;
				opts.editing = false;
			});
		},
		editRow: function(jq, index){
			return jq.each(function(){
				var dg = $(this);
				var opts = $.data(this, 'edatagrid').options;
				var editIndex = opts.editIndex;
				if (editIndex != index){
					if (dg.datagrid('validateRow', editIndex)){
						dg.datagrid('endEdit', editIndex);
						dg.datagrid('beginEdit', index);
						opts.editIndex = index;
					} else {
						setTimeout(function(){
							dg.datagrid('selectRow', editIndex);
						}, 0);
					}
				}
			});
		},
		addRow: function(jq){
			return jq.each(function(){
				var dg = $(this);
				var opts = $.data(this, 'edatagrid').options;
				if (opts.editIndex >= 0){
					if (!dg.datagrid('validateRow', opts.editIndex)){
						dg.datagrid('selectRow', opts.editIndex);
						return;
					}
					dg.datagrid('endEdit', opts.editIndex);
				}
				dg.datagrid('appendRow', {isNewRecord:true});
				var rows = dg.datagrid('getRows');
				opts.editIndex = rows.length - 1;
				dg.datagrid('beginEdit', opts.editIndex);
				dg.datagrid('selectRow', opts.editIndex);
				
				if (opts.tree){
					var node = $(opts.tree).tree('getSelected');
					rows[opts.editIndex][opts.treeParentField] = (node ? node.id : 0);
				}
				opts.onAdd.call(this, opts.editIndex, rows[opts.editIndex]);
			});
		},
		saveRow: function(jq){
			return jq.each(function(){
				var index = $(this).edatagrid('options').editIndex;
				$(this).datagrid('endEdit', index);
			});
		},
		cancelRow: function(jq){
			return jq.each(function(){
				var index = $(this).edatagrid('options').editIndex;
				$(this).datagrid('cancelEdit', index);
			});
		},
		destroyRow: function(jq){
			return jq.each(function(){
				var dg = $(this);
				var opts = $.data(this, 'edatagrid').options;
				//var row = dg.datagrid('getSelected');
				var rows = dg.datagrid('getSelected');
				//if (!row){
				if (!rows){
					$.messager.show({
						title: opts.destroyMsg.norecord.title,
						msg: opts.destroyMsg.norecord.msg
					});
					return;
				}
				$.messager.confirm(opts.destroyMsg.confirm.title,opts.destroyMsg.confirm.msg,function(r){
					if (r){
						var rowsToDel = new Array();//需要服务端删除的行数组
						for(var i = 0; i< rows.legnth; i++){
							var row = rows[i];
							var index = dg.datagrid('getRowIndex', row);
							if (row.isNewRecord){
								dg.datagrid('cancelEdit', index);
							}else{
								rowsToDel.push(row);
							}
						}
						if(rowsToDel.length > 0 ){
							if (opts.destroyUrl){
								var dels = new Array();//服务端需要删除的实体ID数组
								for(var i = 0 ; i < rowsToDel.length; i++){
									if(opts.idField){
										dels.push(rowsToDel[i].goodsId);
										//dels.push(rowsToDel[i].[opts.idField]);
									}else{
										dels.push(rowsToDel[i].id);
									}
								}
								$.post(opts.destroyUrl, {ids:dels}, function(){
									if (opts.tree){
										dg.datagrid('reload');
										var t = $(opts.tree);
										row = rowsToDel[0];///
										var node = t.tree('find', row.id);
										if (node){
											t.tree('remove', node.target);
										}
									} else {
										dg.datagrid('cancelEdit', index);
										dg.datagrid('deleteRow', index);
									}
									opts.onDestroy.call(dg[0], index, row);
								});
							} else {
							    for(var k = 0 ; k < rowsToDel.legnth; k++ ){
								    dg.datagrid('cancelEdit', index);
									dg.datagrid('deleteRow', index);
							    }
							}
						}
					}
				});
			});
		}
	};
	
	$.fn.edatagrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
		editing: true,
		editIndex: -1,
		destroyMsg:{
			norecord:{
				title:'Warning',
				msg:'No record is selected.'
			},
			confirm:{
				title:'Confirm',
				msg:'Are you sure you want to delete?'
			}
		},
		destroyConfirmTitle: 'Confirm',
		destroyConfirmMsg: 'Are you sure you want to delete?',
		
		url: null,	// return the datagrid data
		saveUrl: null,	// return the added row
		updateUrl: null,	// return the updated row
		destroyUrl: null,	// return {success:true}
		
		tree: null,		// the tree selector
		treeUrl: null,	// return tree data
		treeDndUrl: null,	// to process the drag and drop operation, return {success:true}
		treeTextField: 'name',
		treeParentField: 'parentId',
		
		onAdd: function(index, row){},
		onSave: function(index, row){},
		onDestroy: function(index, row){},
		onEdit: function(index,row){},		//刘明庄增加
		saveParams: function(index,row){},  //刘明庄增加
		updateParams: function(index,row){}	//刘明庄增加
	});
})(jQuery);