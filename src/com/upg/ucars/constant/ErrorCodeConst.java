package com.upg.ucars.constant;

/**
 * 
 * ErrorCodeConst.java
 * 
 * @author shentuwy
 * @date 2012-7-20
 * 
 */
public class ErrorCodeConst {
	/** 通用的错误代码 */
	public static final String	COMMON_ERROR_CODE							= "COMMON_ERROR_CODE";
	// 系统空指针异常
	public static final String	SYS_NULL									= "SYS_000";
	// 系统异常，可以注入自己的异常信息
	public static final String	SYS_ERROR									= "SYS_001";
	// 通用异常配置
	public static final String	GLOBAL_ERROR								= "GLOBAL_000";
	// 资源文件解析异常
	public static final String	RESOURCE_FILE_PARS_ERR						= "RES_001";
	// 登录异常，用户名或密码为空
	public static final String	LOGON_ERR_NULL								= "LOGON_001";
	// 登录异常，用户名或密码不匹配
	public static final String	LOGON_ERR_INVALID							= "LOGON_002";
	// 登录异常，用户已关闭
	public static final String	LOGON_ERR_CLOSE								= "LOGON_003";
	// 登录异常，用户已锁定
	public static final String	LOGON_ERR_LOCK								= "LOGON_004";
	// 登录异常，ip不符
	public static final String	LOGON_ERR_IP								= "LOGON_005";
	// 登录异常，用户已在线
	public static final String	LOGON_ERR_ONLINE							= "LOGON_006";
	// 密码已过期
	public static final String	LOGON_ERR_PWD_EFFECT						= "LOGON_007";
	// 接入点未开启
	public static final String	LOGON_ERR_MEMBER_NOT_OPEN					= "LOGON_008";
	// 接入点为空
	public static final String	LOGON_ERR_MEMBER_NULL						= "LOGON_009";
	// 机构为空或不存在
	public static final String	LOGON_ERR_BRCH_NULL							= "LOGON_010";
	// 用户信息不完整
	public static final String	LOGON_ERR_INFO_FAILD						= "LOGON_011";
	// JSON数据处理异常
	public static final String	CREATE_JSON_DATA_ERROR						= "JSON_001";
	// 数据库操作异常，包含hql,和参数
	public static final String	DB_OPERATION_ERROR							= "DAO_001";
	// 数据访问层通用信息，异常信息在编码时注入
	public static final String	DB_COMMON_ERROR								= "DAO_COMMON";
	// 数据访问层通用信息，异常信息在编码时注入
	public static final String	DB_VERSION_ERROR							= "DAO_VERSION";
	/**
	 * 安全权限
	 */
	// 没有权限访问,可以注入一个参数来描述资源
	public static final String	SECURITY_NO_LOGON							= "SECURITY_000";
	public static final String	SECURITY_NO_RIGHT_ERROR						= "SECURITY_001";

	public static final String	SECURITY_SYSFUNC_SAME_FUNCID				= "SECURITY_002";
	public static final String	SECURITY_SYSFUNC_SAME_FUNCNAME				= "SECURITY_003";
	public static final String	SECURITY_SYSFUNC_SAME_FUNCNAME_EN			= "SECURITY_004";
	public static final String	SECURITY_SYSFUNC_DEL_HASSUB					= "SECURITY_005";
	public static final String	SECURITY_SYSFUNC_DISABLE_ADDSUB_URL			= "SECURITY_006";
	public static final String	SECURITY_SYSFUNC_DISABLE_ADDSUB_TYPE		= "SECURITY_007";
	public static final String	SECURITY_SYSFUNC_PARENT_NOTO_CHILD			= "SECURITY_008";

	public static final String	SECURITY_MEMBER_SAME_MINO					= "SECURITY_009";
	public static final String	SECURITY_MEMBER_SAME_MINAME					= "SECURITY_010";
	public static final String	SECURITY_SYSFUNC_MOVE_MENU_TO_FUNC			= "SECURITY_011";
	public static final String	SECURITY_SYSFUNC_MOVE_URL					= "SECURITY_012";
	public static final String	SECURITY_SYSFUNC_EDIT_TYPE_ERROR			= "SECURITY_013";
	public static final String	SECURITY_SYSFUNC_DEL_USING					= "SECURITY_014";
	public static final String	SECURITY_SYSFUNC_EDIT_SAME_NAME				= "SECURITY_015";

	/** 新增权限失败：功能类型权限节点下不能再建权限节点 */
	public static final String	SECURITY_SYSFUNC_ADD_016					= "SECURITY_016";
	/** 新增权限失败：菜单组类型权限节点下不能建功能类型权限节点 */
	public static final String	SECURITY_SYSFUNC_ADD_017					= "SECURITY_017";
	/** 新增权限失败：菜单类型权限节点下只能建功能类型权限节点 */
	public static final String	SECURITY_SYSFUNC_ADD_018					= "SECURITY_018";
	/** 新增权限失败：菜单或功能类型权限节点url不能为空 */
	public static final String	SECURITY_SYSFUNC_ADD_019					= "SECURITY_019";
	/** 修改权限失败：包含子节点，权限类型不能修改 */
	public static final String	SECURITY_SYSFUNC_EDIT_TYPE_NOT_ALLOW		= "SECURITY_020";
	/** 修改权限失败：菜单或功能类型权限节点url不能为空 */
	public static final String	SECURITY_SYSFUNC_EDIT_URL_NOT_EMPTY			= "SECURITY_020";

	/** 功能节点只能在菜单节点下面 */
	public static final String	SECURITY_FUNC_SYSFUNC_MOVE_ERROR			= "SECURITY_022";
	/** 菜单节点只能在菜单组节点下面 */
	public static final String	SECURITY_MENU_SYSFUNC_MOVE_ERROR			= "SECURITY_023";
	/** 菜单组节点只能在菜单组节点下面 */
	public static final String	SECURITY_GROUP_SYSFUNC_MOVE_ERROR			= "SECURITY_024";
	/** 菜单或者菜单组节点不能移置到菜单节点下面 */
	public static final String	SECURITY_MENU_GROUP_SYSFUNC_MOVE_ERROR		= "SECURITY_025";
	/** 权限树只能有一个根节点 */
	public static final String	SECURITY_SYSFUNC_MOVE_TOP_ERROR				= "SECURITY_026";

	public static final String	SECURITY_PASSWORD_ERROR_PREPASSWORD			= "SECURITY_101";
	public static final String	SECURITY_PASSWORD_ERROR_DIFFERENT_PASSWORD	= "SECURITY_102";
	public static final String	SECURITY_NO_ROLE_ERROR						= "SECURITY_103";
	public static final String	SECURITY_USERNO_EXIST						= "SECURITY_111";
	/** 修改用户类型失败：用户已分配了角色，不能修改用户类型 */
	public static final String	SECURITY_USER_EDIT_USERTYPE					= "SECURITY_113";
	public static final String	SECURITY_ROLENAME_EXIST						= "SECURITY_201";
	public static final String	SECURITY_ROLEUPDATE_SETEDFUNCTION			= "SECURITY_202";

	/** 子系统错误code */
	/**
	 * 子系统删除还引用权限
	 */
	public static final String	SUBSYS_DEL_HAS_FUNC							= "SUBSYS_001";
	/**
	 * 处在开放状态，子系统不能删除
	 */
	public static final String	SUBSYS_DEL_STATUS_OPEN						= "SUBSYS_002";

	/*
	 * 生成机构数型编码错误
	 */
	public static String		GEN_BRCH_TREE_CODE_ERROR					= "GEN_BRCH_TREE_CODE";

	/**
	 * ESB 异常码
	 */
	/*
	 * ESB上下文配置异常
	 */
	public static final String	ESB_CONF_ERROR								= "ESB_CONF_001";
	/*
	 * ESB初试化异常
	 */
	public static final String	ESB_SERVER_INIT_ERROR						= "ESB_SERVER_001";
	/*
	 * ESB服务启动异常
	 */
	public static final String	ESB_SERVER_START_ERROR						= "ESB_SERVER_002";
	/*
	 * ESB客户端创建异常
	 */
	public static final String	ESB_CLIENT_CREATE_ERROR						= "ESB_CLIENT_001";
	/*
	 * ESB客户端消息发送异常
	 */
	public static final String	ESB_CLIENT_SEND_ERROR						= "ESB_CLIENT_002";

	/*
	 * ESB报文转换配置异常
	 */
	public static final String	ESB_MSG_TF_CONF_ERROR						= "ESB_MSG_TF_CONF_001";

	/*
	 * ESB报文转换异常
	 */
	public static final String	ESB_MSG_TF_ERROR							= "ESB_MSG_TF_001";

	/**
	 * 工作流
	 */
	// 工作流通用异常，异常信息由编码时注入
	public static final String	WF_COMMON_ERROR								= COMMON_ERROR_CODE;
	// 流程已存在
	public static final String	WF_EXIST_UNEND_PROCESS						= "WF_EXIST_UNEND_PROCESS";
	public static final String	WF_TASK_ACTOR_NULL							= "WF_TASK_ACTOR_NULL";
	public static final String	WF_AUTO_NODE_NO_SERVICE						= "WF_AUTO_NODE_NO_SERVICE";
	public static final String	WF_AUTO_NODE_INVOKE_FAILURE					= "WF_AUTO_NODE_INVOKE_FAILURE";
	public static final String	WF_ACTION_NAME_INVALID						= "WF_ACTION_NAME_INVALID";
	public static final String	WF_ACTION_INVOKE_FAILURE					= "WF_ACTION_INVOKE_FAILURE";
	// 工作流任务处理异常 包含 节点id(taskId)参数
	public static final String	WF_TASK_PROCESS_ERROR						= "WF_TASK_PROCESS_ERROR";
	// 工作流令牌流转异常，包含令牌id（tokenId）
	public static final String	WF_TOKEN_PROCESS_ERROR						= "WF_TOKEN_PROCESS_ERROR";
	// 创建流程定义实例异常，包含流程定义名称（processDefinitionName）
	public static final String	WF_CREATE_PROCESS_ERROR						= "WF_CREATE_PROCESS_ERROR";
	public static final String	WF_TASK_HOLD								= "WF_TASK_HOLD";
	public static final String	WF_TASK_CANCEL_HOLD							= "WF_TASK_CANCEL_HOLD";
	/** 任务处理人被固定不能撤销 */
	public static final String  WF_TASK_CANCEL_HOLD_FIX						= "WF_TASK_CANCEL_HOLD_FIX";
	public static final String	WF_ONE_IN_MULTI_TOKEN						= "WF_ONE_IN_MULTI_TOKEN";
	// 流程已分配到接入点，不允许删除.
	public static final String	WF_DEF_DEL_001								= "WF_DEF_DEL_001";

	public static final String	DEMO_LOAN_INFO_SAVE							= "DEMO_LOAN_INFO_SAVE";
	public static final String	DEMO_LOAN_INFO_UPDATE						= "DEMO_LOAN_INFO_UPDATE";
	public static final String	DEMO_LOAN_INFO_DEL							= "DEMO_LOAN_INFO_DEL";
	public static final String	DEMO_LOAN_INFO_GET							= "DEMO_LOAN_INFO_GET";
	public static final String	DEMO_LOAN_INFO_QUERY						= "DEMO_LOAN_INFO_QUERY";
	public static final String	DEMO_LOAN_PAYMENT_ERROR						= "DEMO_LOAN_001";
	public static final String	DEMO_LOAN_TASK_ERROR						= "DEMO_LOAN_002";
	public static final String	DEMO_LOAN_START_ERROR						= "DEMO_LOAN_003";

	public static final String	PROCMAP_GETPROC_001							= "PROCMAP_GETPROC_001";
	public static final String	PROCMAP_GETPROC_002							= "PROCMAP_GETPROC_002";
	public static final String	PROCMAP_GETPROC_003							= "PROCMAP_GETPROC_003";

	public static final String	AUTO_TASK_001								= "AUTO_TASK_001";
	/** 定时任务运行时通用异常 */
	public static final String	AUTO_TASK_RUN_COMMON						= "AUTO_TASK_RUN_001";

	public static final String	BASESYSTEM_CODE_META_REPEAT					= "BASESYSTEM_CODE_META_REPEAT";

	/** 参数表 违反 (paramKey和miNo)唯一性 */
	public static final String	SYS_PARAM_KEY_MI_EXIST_ERROR				= "SYS_PARAM_KEY_MI_EXIST";

	/** 如果有子机构不能更改当前结构的树编码 */
	public static final String	BRANCH_CHANGE_TREECODE_ERROR				= "BRANCH_CHANGE_TREECODE_ERROR";
	/**
	 * 机构码已经存在
	 */
	public static String		BRCH_MI_NO_REPEAT							= "BRANCH_001";
	/** 机构正在使用权限，不能删除 */
	public static final String	BRANCH_DEL_ERROR_HAS_RIGHT					= "BRANCH_002";
	/** 机构权限正在审核中，不能设置权限 */
	public static final String	BRANCH_SET_RIGHT_CHECKING					= "BRANCH_003";
	/** 机构权限状态为分配待审核，才能提交审批 */
	public static final String	BRANCH_AUDIT_ERROR_UN_ASSIGNNING			= "BRANCH_004";
	/**
	 * TODO 移植代码时临时使用，待删除。
	 */
	public static final String	TEMP										= "TEMP";

	/** 非空输入较验 */
	public static final String	ESI_CANTNOT_BE_NULL							= "ESI_001";

	/** 产品编号已存在 */
	public static final String	PRODUCT_SAVE_EXIST_NO						= "PRODUCT_001";
	/** 同级别中产品名称不能重复 */
	public static final String	PRODUCT_SUBPORD_NAMEREPEAT					= "PRODUCT_003";
	/** 同级别中产品名称国际化不能重复 */
	public static final String	PRODUCT_SUBPORD_KEYREPEAT					= "PRODUCT_004";
	/** 存在子项，不许删除 */
	public static final String	PRODUCT_DEL_HASSUB							= "PRODUCT_005";
	/** 分配了权限，不许修改类型 */
	public static final String	PRODUCT_UPDATETYPE_BE_FUNC					= "PRODUCT_006";
	/** 产品已授权给接入点，不许删除 */
	public static final String	PRODUCT_DEL_ASSIGN_MEMBER					= "PRODUCT_007";
	/** 产品分配失败：部分产品已经删除 */
	public static final String	PRODUCT_DELETED								= "PRODUCT_008";

	/** 网银实体校验信息 */
	public static final String	NET_BEAN_VALIDATE							= "NBV_001";
}
